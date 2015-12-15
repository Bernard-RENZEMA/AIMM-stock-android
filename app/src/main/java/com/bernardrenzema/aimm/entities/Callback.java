package com.bernardrenzema.aimm.entities;


public class Callback {

    public void onProgress() {
    }
    public void onProgress(Progress progress) {
    }
    public void onProgress(Object result) {
    }
    public void onProgress(Progress progress, Object result) {
    }

    public void onSuccess() {
    }
    public void onSuccess(Progress progress) {
    }
    public void onSuccess(Object result) {

    }
    public void onSuccess(Progress progress, Object result) {
    }

    public void onFailure(int code, String error) {
    }
    public void onFailure(int code, Object error) {
    }
    public void onFailure(Progress progress, int code, String error) {
    }
}
