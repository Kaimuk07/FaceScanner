package com.example.facescanlibrary.util;

import com.example.facescanlibrary.config.GlobalFaceScan;

/**
 * Created by kanthimp on 10/01/2561.
 */

public class Error {
    private String msg;
    private int requestTake = GlobalFaceScan.getInstance().getRequest_carmera_none();

    public Error(String msg,int requestTake) {
        this.msg = msg;
        this.requestTake = requestTake;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRequestTake() {
        return requestTake;
    }

    public void setRequestTake(int requestTake) {
        this.requestTake = requestTake;
    }
}
