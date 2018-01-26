package com.example.kanthimp.testfacelibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.facescanlibrary.config.GlobalFaceScan;
import com.example.facescanlibrary.module.ui.ServiceFaceScan;
import com.example.facescanlibrary.util.Error;
import com.example.facescanlibrary.util.ImageFace;
import com.example.facescanlibrary.util.ImageFaceObj;
import com.example.facescanlibrary.util.Success;

public class MainActivity extends AppCompatActivity implements ServiceFaceScan.CallBack {
    ImageView imageView_1;
    ImageView ImageView_2;
    ProgressDialog progressDialog;
    TextView info;
    private ServiceFaceScan serviceFaceScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);
        imageView_1 = (ImageView) findViewById(R.id.image_0);
        ImageView_2 = (ImageView) findViewById(R.id.image_1);
        GlobalFaceScan.getInstance().setSubscription_key("45e9d47642f44f5c86290fe0754363ac");
        GlobalFaceScan.getInstance().setEndpoint("https://westcentralus.api.cognitive.microsoft.com/face/v1.0");
        GlobalFaceScan.getInstance().setRequest_carmera1(2627);//default value 2627
        GlobalFaceScan.getInstance().setRequest_carmera2(2628);//default value 2628
        GlobalFaceScan.getInstance().setRequest_carmera_none(2629); //default value 2629
        setUpServiceFaceScan();
        
    }

    private void setUpServiceFaceScan() {
        serviceFaceScan = new ServiceFaceScan(this, this);
    }

    public void selectImage1(View view) {
        serviceFaceScan.openCamera(GlobalFaceScan.getInstance().getRequest_carmera2());

    }

    public void selectImage0(View view) {
        serviceFaceScan.openCamera(GlobalFaceScan.getInstance().getRequest_carmera1());
    }

    public void verify(View view) {
        showLoading();
        serviceFaceScan.Verify();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        serviceFaceScan.openCamera(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        serviceFaceScan.onActivityResultCamera(requestCode, resultCode, data);
    }

    @Override
    public void oServiceFaceScanImageSuccess(ImageFaceObj imageFaceObj) {
        for (ImageFace imageFace : imageFaceObj.getArrImage()) {
            if (imageFace.getRequestTake() == GlobalFaceScan.getInstance().getRequest_carmera1()) {
                imageView_1.setImageBitmap(imageFace.getBitmap());
            } else if (imageFace.getRequestTake() == GlobalFaceScan.getInstance().getRequest_carmera2()) {
                ImageView_2.setImageBitmap(imageFace.getBitmap());
            }
        }
    }

    @Override
    public void oServiceFaceScanImageFailed(int requestCode, int resultCode, Intent data, ImageFaceObj imageFaceObj) {

    }

    @Override
    public void oServiceFaceScanCallApiSuccess(Success success) {
        hideLoading();
        info.setText(success.getMsg());
    }


    @Override
    public void oServiceFaceScanCallApiFailed(Error error) {
        hideLoading();
        Log.d("", "Error:" + error.getMsg());
        if (error.getRequestTake() == GlobalFaceScan.getInstance().getRequest_carmera1()) {
            info.setText(error.getMsg() + "Image 1");
        } else if (error.getRequestTake() == GlobalFaceScan.getInstance().getRequest_carmera2()) {
            info.setText(error.getMsg() + "Image 2");
        }
    }


    private void showLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ....");
        progressDialog.show();
    }

    private void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
