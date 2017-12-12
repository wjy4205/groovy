package com.bunny.groovy.model;

/**
 * Created by Administrator on 2017/12/11.
 */

public class BooleanBean {

    /**
     * success : true
     * errorMsg :
     * resultData : null
     * errorCode :
     */

    private boolean success;
    private String errorMsg;
    private Object resultData;
    private String errorCode;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
