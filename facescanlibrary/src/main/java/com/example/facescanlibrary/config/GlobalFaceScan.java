package com.example.facescanlibrary.config;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;

/**
 * Created by kanthimp on 08/01/2561.
 */

public class GlobalFaceScan {

    private static GlobalFaceScan instance;
    private String subscription_key = "";
    private String endpoint = "";

    private int request_carmera_none = 2629;
    private int request_carmera1 = 2627;
    private int request_carmera2 = 2628;

    private FaceServiceClient sFaceServiceClient;

    public FaceServiceClient getsFaceServiceClient() {
        if(sFaceServiceClient == null){
            sFaceServiceClient = new FaceServiceRestClient(GlobalFaceScan.getInstance().endpoint, GlobalFaceScan.getInstance().subscription_key);
        }
        return sFaceServiceClient;
    }

    public static GlobalFaceScan getInstance() {
        if (instance == null)
            instance = new GlobalFaceScan();
        return instance;
    }

    public void setSubscription_key(String subscription_key) {
        this.subscription_key = subscription_key;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public int getRequest_carmera1() {
        return request_carmera1;
    }

    public void setRequest_carmera1(int request_carmera1) {
        this.request_carmera1 = request_carmera1;
    }

    public int getRequest_carmera2() {
        return request_carmera2;
    }

    public void setRequest_carmera2(int request_carmera2) {
        this.request_carmera2 = request_carmera2;
    }

    public int getRequest_carmera_none() {
        return request_carmera_none;
    }

    public void setRequest_carmera_none(int request_carmera_none) {
        this.request_carmera_none = request_carmera_none;
    }
}
