package com.example.facescanlibrary.util;

import android.graphics.Bitmap;

import com.example.facescanlibrary.config.GlobalFaceScan;
import com.microsoft.projectoxford.face.contract.Face;

import java.util.List;
import java.util.UUID;

/**
 * Created by kanthimp on 09/01/2561.
 */

public class ImageFace {
    private int requestTake = GlobalFaceScan.getInstance().getRequest_carmera_none();
    private int index;
    private Bitmap bitmap = null;
    private Face[] face;
    private List<Face> listFaces;
    private UUID uuid;

    public int getRequestTake() {
        return requestTake;
    }

    public void setRequestTake(int requestTake) {
        this.requestTake = requestTake;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Face[] getFace() {
        return face;
    }

    public void setFace(Face[] face) {
        this.face = face;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Face> getListFaces() {
        return listFaces;
    }

    public void setListFaces(List<Face> listFaces) {
        this.listFaces = listFaces;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
