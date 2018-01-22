package com.example.facescanlibrary.module.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.example.facescanlibrary.config.GlobalFaceScan;
import com.example.facescanlibrary.module.callservice.CallServiceUploadImage;
import com.example.facescanlibrary.module.callservice.CallServiceVerify;
import com.example.facescanlibrary.util.Error;
import com.example.facescanlibrary.util.ImageFace;
import com.example.facescanlibrary.util.ImageFaceObj;
import com.example.facescanlibrary.util.Success;
import com.microsoft.projectoxford.face.contract.Face;

/**
 * Created by kanthimp on 08/01/2561.
 */

public class ServiceFaceScan {
    private Context context;
    private CallBack callBack;
    private ImageFaceObj imageFaceObj;


    public ServiceFaceScan(Context context, CallBack callback) {
        this.context = context;
        this.callBack = callback;
        imageFaceObj = new ImageFaceObj();
    }

    public ServiceFaceScan openCamera(int requestTake) {
        if (checkPermissionCameraOpen(requestTake)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ((Activity) context).startActivityForResult(intent, requestTake);
        } else {

        }
        return this;
    }

    public void openCamera(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode != GlobalFaceScan.getInstance().getRequest_carmera_none()){
            openCamera(grantResults, requestCode);
        }
    }

    public void openCamera(int[] grantResults, int requestTake) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera(requestTake);
        }
    }

    private boolean checkPermissionCameraOpen(int requestTake) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ((Activity) context).requestPermissions(new String[]{Manifest.permission.CAMERA},
                        requestTake);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    public void onActivityResultCamera(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        if (requestCode != GlobalFaceScan.getInstance().getRequest_carmera_none() && resultCode == ((Activity) context).RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            // setcallCloudVision(bitmap);
            imageFaceObj.clearData(requestCode);
            imageFaceObj.setArrImageBitmap(bitmap, requestCode);
            callBack.oServiceFaceScanImageSuccess(imageFaceObj);
        } else {
            callBack.oServiceFaceScanImageFailed(requestCode, resultCode, data, imageFaceObj);
        }

    }

    public void Verify() {
        //uploadimage0
        if (imageFaceObj.getArrImage().get(0).getFace() == null) {
            callServiceUploadImage(imageFaceObj.getArrImage().get(0));
        } else if (imageFaceObj.getArrImage().get(1).getFace() == null) {
            callServiceUploadImage(imageFaceObj.getArrImage().get(1));
        } else {
            callServiceVerify();
        }
    }

    private void callServiceUploadImage(ImageFace imageFace) {
        final CallServiceUploadImage callServiceUploadImage = new CallServiceUploadImage(imageFace);
        callServiceUploadImage.setCallBackService(new CallServiceUploadImage.CallBackService() {
            @Override
            public void success(Face[] detectionResult, int index, boolean succeed) {
                Log.d("", "");
                Verify();
            }

            @Override
            public void failed(Error error) {
                callBack.oServiceFaceScanCallApiFailed(error);
            }

            @Override
            public void loading() {

            }
        }).call();
    }

    private void callServiceVerify() {
        CallServiceVerify callServiceVerify = new CallServiceVerify(imageFaceObj);
        callServiceVerify.setCallBackService(new CallServiceVerify.CallBackService() {
            @Override
            public void success(String verificationResult) {
                callBack.oServiceFaceScanCallApiSuccess(new Success(verificationResult, imageFaceObj));
            }

            @Override
            public void failed(String msg) {

            }

            @Override
            public void loading() {

            }
        }).call();

    }


    public interface CallBack {
        public void oServiceFaceScanImageSuccess(ImageFaceObj imageFaceObj);

        public void oServiceFaceScanImageFailed(int requestCode, int resultCode, Intent data, ImageFaceObj imageFaceObj);

        public void oServiceFaceScanCallApiSuccess(Success success);

        public void oServiceFaceScanCallApiFailed(Error error);

    }
}
