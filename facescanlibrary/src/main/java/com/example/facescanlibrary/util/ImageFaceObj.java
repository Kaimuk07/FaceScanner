package com.example.facescanlibrary.util;

import android.graphics.Bitmap;

import com.example.facescanlibrary.config.GlobalFaceScan;

import java.util.ArrayList;

/**
 * Created by kanthimp on 09/01/2561.
 */

public class ImageFaceObj {
    private ArrayList<ImageFace> arrImage;

    public ImageFaceObj() {
        arrImage = new ArrayList<ImageFace>();
        //
        ImageFace imageFace_1 = new ImageFace();
        imageFace_1.setRequestTake(GlobalFaceScan.getInstance().getRequest_carmera1());
        imageFace_1.setIndex(0);
        //
        ImageFace imageFace_2 = new ImageFace();
        imageFace_2.setRequestTake(GlobalFaceScan.getInstance().getRequest_carmera2());
        imageFace_2.setIndex(1);
        //
        arrImage.add(imageFace_1);
        arrImage.add(imageFace_2);
    }

    public ArrayList<ImageFace> getArrImage() {
        return arrImage;
    }

    public void setArrImage(ArrayList<ImageFace> arrImage) {
        this.arrImage = arrImage;
    }

    public void setArrImageBitmap(Bitmap bitmap, int requestTake){
        for (ImageFace imageface : arrImage) {
            if(imageface.getRequestTake() == requestTake){
                imageface.setBitmap(bitmap);
            }
        }
    }

    public void clearData(int requestTake){
        for (ImageFace imageface : arrImage) {
            if(imageface.getRequestTake() == requestTake){
                imageface.setBitmap(null);
                imageface.setFace(null);
            }
        }
    }

}
