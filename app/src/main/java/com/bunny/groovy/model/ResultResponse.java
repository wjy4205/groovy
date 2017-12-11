package com.bunny.groovy.model;

/**
 * @description: 访问返回的response
 */
public class ResultResponse<T> {

    public String errorCode;
    public String errorMsg;
    public String success; 
    public T resultData;

    public ResultResponse(String errorCode, String errorMsg, String success, T resultData) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.success = success;
        this.resultData = resultData;
    }
}
