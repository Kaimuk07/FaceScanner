package com.example.facescanlibrary.module.callservice;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.facescanlibrary.config.GlobalFaceScan;
import com.example.facescanlibrary.util.Error;
import com.example.facescanlibrary.util.ImageFace;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.Face;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by kanthimp on 09/01/2561.
 */

public class CallServiceUploadImage {
    private FaceServiceClient faceServiceClient;
    private CallBackService callBackService;
    private ImageFace imageFace;
    private ByteArrayInputStream inputStream;

    public CallServiceUploadImage(ImageFace imageFace) {
        this.faceServiceClient = GlobalFaceScan.getInstance().getsFaceServiceClient();
        this.imageFace = imageFace;
        this.inputStream = setInputStram();
    }

    private ByteArrayInputStream setInputStram() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        imageFace.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());
        return new ByteArrayInputStream(output.toByteArray());
    }

    public CallServiceUploadImage setCallBackService(CallBackService callBackService) {
        this.callBackService = callBackService;
        return this;
    }

    public FaceServiceClient getFaceServiceClient() {
        return faceServiceClient;
    }

    public void setFaceServiceClient(FaceServiceClient faceServiceClient) {
        this.faceServiceClient = faceServiceClient;
    }

    public CallServiceUploadImage call() {
        new DetectionTask(imageFace.getIndex()).execute(inputStream);
        return this;
    }

    private void setUiAfterDetection(Face[] detectionResult, int index, boolean succeed) {
        if (succeed) {
            imageFace.setFace(detectionResult);
            if (checkDetectFace(imageFace,detectionResult) == false) {
                callBackService.failed(new Error("No face detected!", imageFace.getRequestTake()));
            } else if(checkDetectSingleFace(imageFace) == false){
                callBackService.failed(new Error("No single face detected!", imageFace.getRequestTake()));
            }else {
                findUUID(imageFace);
                callBackService.success(detectionResult, index, succeed);
            }
        } else if (detectionResult != null && detectionResult.length == 0) {
            callBackService.failed(new Error("No face detected!", imageFace.getRequestTake()));
        }
    }

    private boolean checkDetectFace(ImageFace mTmpimageFace, Face[] detectionResult) {
        boolean status = false;
        if (detectionResult != null) {
            List<Face> listFaces;
            listFaces = Arrays.asList(detectionResult);
            if (listFaces != null && listFaces.size() > 0) {
                mTmpimageFace.setListFaces(listFaces);
                status = true;
            } else {
                status = false;
            }
        }
        return status;
    }

    private boolean checkDetectSingleFace (ImageFace mTmpimageFace){
        if(mTmpimageFace.getListFaces() != null && mTmpimageFace.getListFaces().size()==1){
            return true;
        }
        return false;
    }

    private void findUUID(ImageFace mTmpimageFace){
        for (Face item : mTmpimageFace.getListFaces()) {
            mTmpimageFace.setUuid(item.faceId);
        }
    }

    public interface CallBackService {
        void success(Face[] detectionResult, int index, boolean succeed);

        void failed(Error error);

        void loading();

    }

    private class DetectionTask extends AsyncTask<InputStream, String, Face[]> {
        // Index indicates detecting in which of the two images.
        private int mIndex;
        private boolean mSucceed = true;

        DetectionTask(int index) {
            mIndex = index;
        }

        @Override
        protected Face[] doInBackground(InputStream... params) {
            // Get an instance of face service client to detect faces in image.
            try {
                publishProgress("Detecting...");

                // Start detection.
                return faceServiceClient.detect(
                        params[0],  /* Input stream of image to detect */
                        true,       /* Whether to return face ID */
                        false,       /* Whether to return face landmarks */
                        /* Which face attributes to analyze, currently we support:
                           age,gender,headPose,smile,facialHair */
                        null);
            } catch (Exception e) {
                mSucceed = false;
                publishProgress(e.getMessage());
                callBackService.failed(new Error(e.getMessage(),imageFace.getRequestTake()));
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            callBackService.loading();
        }

        @Override
        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(Face[] result) {
            setUiAfterDetection(result, mIndex, mSucceed);
        }
    }

}
