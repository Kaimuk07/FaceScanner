package com.example.facescanlibrary.util;

/**
 * Created by kanthimp on 10/01/2561.
 */

public class Success {
    private String msg;
    private ImageFaceObj imageFaceObj;

    public Success(String msg, ImageFaceObj imageFaceObj) {
        this.msg = msg;
        this.imageFaceObj = imageFaceObj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
