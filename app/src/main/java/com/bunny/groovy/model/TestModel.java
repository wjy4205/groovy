package com.bunny.groovy.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class TestModel {

    /**
     * success : true
     * errorMsg :
     * resultData : [{"createDate":"2017-11-02 16:40","typeID":"1","typeName":"ROCK","typeImg":"http://47.100.104.82:8083/upload/performTypeImgFile/10_ROCK.png"},{"createDate":"2017-11-02 16:40","typeID":"2","typeName":"INDIE","typeImg":"http://47.100.104.82:8083/upload/performTypeImgFile/6_INDIE.png"},{"createDate":"2017-11-02 16:40","typeID":"3","typeName":"POP","typeImg":"http://47.100.104.82:8083/upload/performTypeImgFile/9_POP.png"},{"createDate":"2017-11-02 16:40","typeID":"4","typeName":"R&B","typeImg":"http://47.100.104.82:8083/upload/performTypeImgFile/11_R&B.png"},{"createDate":"2017-11-02 16:40","typeID":"5","typeName":"BLUES","typeImg":"http://47.100.104.82:8083/upload/performTypeImgFile/1_BLUES.png"},{"createDate":"2017-11-02 16:40","typeID":"6","typeName":"JAZZ","typeImg":"http://47.100.104.82:8083/upload/performTypeImgFile/7_JAZZ.png"},{"createDate":"2017-11-02 16:40","typeID":"7","typeName":"FOLK","typeImg":"http://47.100.104.82:8083/upload/performTypeImgFile/5_FOLK.png"},{"createDate":"2017-11-02 16:40","typeID":"8","typeName":"COUNTRY","typeImg":"http://47.100.104.82:8083/upload/performTypeImgFile/2_COUNTRY.png"},{"createDate":"2017-11-02 16:41","typeID":"9","typeName":"RAP/HIP-HOP","typeImg":"http://47.100.104.82:8083/upload/performTypeImgFile/12_RAPHIP-HOP.png"},{"createDate":"2017-11-02 16:41","typeID":"10","typeName":"ELECTRONIC/DJ","typeImg":"http://47.100.104.82:8083/upload/performTypeImgFile/4_ELECTRONICDJ.png"},{"createDate":"2017-11-02 16:41","typeID":"11","typeName":"METAL","typeImg":"http://47.100.104.82:8083/upload/performTypeImgFile/8_METAL.png"},{"createDate":"2017-11-02 16:41","typeID":"12","typeName":"CLASSICAL","typeImg":"http://47.100.104.82:8083/upload/performTypeImgFile/3_CLASSICAL.png"}]
     * errorCode :
     */

    private boolean success;
    private String errorMsg;
    private String errorCode;
    private List<ResultDataBean> resultData;

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

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<ResultDataBean> getResultData() {
        return resultData;
    }

    public void setResultData(List<ResultDataBean> resultData) {
        this.resultData = resultData;
    }

    public static class ResultDataBean {
        /**
         * createDate : 2017-11-02 16:40
         * typeID : 1
         * typeName : ROCK
         * typeImg : http://47.100.104.82:8083/upload/performTypeImgFile/10_ROCK.png
         */

        private String createDate;
        private String typeID;
        private String typeName;
        private String typeImg;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getTypeID() {
            return typeID;
        }

        public void setTypeID(String typeID) {
            this.typeID = typeID;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeImg() {
            return typeImg;
        }

        public void setTypeImg(String typeImg) {
            this.typeImg = typeImg;
        }
    }
}
