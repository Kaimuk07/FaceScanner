package com.example.facescanlibrary.module.callservice;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.facescanlibrary.config.GlobalFaceScan;
import com.example.facescanlibrary.util.ImageFace;
import com.example.facescanlibrary.util.ImageFaceObj;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.VerifyResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.UUID;

/**
 * Created by kanthimp on 09/01/2561.
 */

public class CallServiceVerify {
    private FaceServiceClient faceServiceClient;
    private CallBackService callBackService;
    private ImageFaceObj imageFaceObj;

    public CallServiceVerify(ImageFaceObj imageFaceObj) {
        this.faceServiceClient = GlobalFaceScan.getInstance().getsFaceServiceClient();
        this.imageFaceObj = imageFaceObj;
    }

    public CallServiceVerify setCallBackService(CallBackService callBackService) {
        this.callBackService = callBackService;
        return this;
    }

    public FaceServiceClient getFaceServiceClient() {
        return faceServiceClient;
    }

    public void setFaceServiceClient(FaceServiceClient faceServiceClient) {
        this.faceServiceClient = faceServiceClient;
    }

    public interface CallBackService {
        void success(String verificationResult);

        void failed(String msg);

        void loading();

    }

    public CallServiceVerify call(){
        new VerificationTask(imageFaceObj.getArrImage().get(0).getUuid(), imageFaceObj.getArrImage().get(1).getUuid()).execute();
        return this;
    }

    private class VerificationTask extends AsyncTask<Void, String, VerifyResult> {
        // The IDs of two face to verify.
        private UUID mFaceId0;
        private UUID mFaceId1;

        VerificationTask(UUID faceId0, UUID faceId1) {
            mFaceId0 = faceId0;
            mFaceId1 = faceId1;
        }

        @Override
        protected VerifyResult doInBackground(Void... params) {
            // Get an instance of face service client to detect faces in image.
            try {
                publishProgress("Verifying...");

                // Start verification.
                return faceServiceClient.verify(
                        mFaceId0,      /* The first face ID to verify */
                        mFaceId1);     /* The second face ID to verify */
            } catch (Exception e) {
                publishProgress(e.getMessage());
                callBackService.failed(e.getMessage());
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
        protected void onPostExecute(VerifyResult result) {
//            if (result != null) {
////                addLog("Response: Success. Face " + mFaceId0 + " and face "
////                        + mFaceId1 + (result.isIdentical ? " " : " don't ")
////                        + "belong to the same person");
//
//            }

            // Show the result on screen when verification is done.
            setUiAfterVerification(result);
        }
    }

    private void setUiAfterVerification(VerifyResult result) {
        // Show verification result.
        if (result != null) {
            DecimalFormat formatter = new DecimalFormat("#0.00");
            String verificationResult = (result.isIdentical ? "The same person" : "Different persons")
                    + ". The confidence is " + formatter.format(result.confidence);
            callBackService.success(verificationResult);
        }else{
            callBackService.failed("Error");
        }
    }


}
