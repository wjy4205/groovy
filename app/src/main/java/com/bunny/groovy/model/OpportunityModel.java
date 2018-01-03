package com.bunny.groovy.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/25.
 ****************************************/

public class OpportunityModel implements Parcelable {

    /**
     * createDate : 2017-12-06 09:54
     * venueID : 9
     * venueName : 夏先生
     * headImg : null
     * venueAddress : 中国广东省深圳市龙岗区
     * phoneNumber : 13510762742
     * webSiteAddress : null
     * longitude : 114.25
     * latitude : 22.72
     * venueScore : 4.4
     * venueTypeName : Exclue 21+,Serves Food,Serves Alcohol
     * twitterAccount : 654321
     * facebookAccount : 12345678
     * isHaveCharges : null
     * venueEmail : 747616936@qq.com
     * opportunityID : 5
     * startDate : 2017-12-29 18:55:14
     * endDate : 2017-12-29 19:55:14
     * performDesc : 222
     * opportunityState : 0
     * applyID : 1
     * operationDate : 2017-12-06 09:54:25.0
     * performDate : Dec 29
     * performTime : 6:55PM-7:55PM
     * performerOpportunity : [{"createDate":"2017-12-07 14:36","venueID":9,"venueName":"夏先生","headImg":null,"venueAddress":"中国广东省深圳市龙岗区","phoneNumber":"13510762742","webSiteAddress":null,"longitude":114.25,"latitude":22.72,"venueScore":4.4,"venueTypeName":"Exclue 21+,Serves Food,Serves Alcohol","twitterAccount":"654321","facebookAccount":"12345678","isHaveCharges":null,"venueEmail":"747616936@qq.com","opportunityID":"6","startDate":"2018-12-07 21:30:00","endDate":"2018-12-07 23:30:00","performDesc":"明年的今天有一场表演，需要一名吉他手，键盘手，以及数名歌唱演员","opportunityState":"1","applyID":"1","operationDate":"2017-12-08 14:12:20.0","performDate":"Dec 07","performTime":"9:30PM-11:30PM","performerOpportunity":null,"distance":null,"scheduleList":null},{"createDate":"2017-12-06 09:54","venueID":9,"venueName":"夏先生","headImg":null,"venueAddress":"中国广东省深圳市龙岗区","phoneNumber":"13510762742","webSiteAddress":null,"longitude":114.25,"latitude":22.72,"venueScore":4.4,"venueTypeName":"Exclue 21+,Serves Food,Serves Alcohol","twitterAccount":"654321","facebookAccount":"12345678","isHaveCharges":null,"venueEmail":"747616936@qq.com","opportunityID":"5","startDate":"2017-12-29 18:55:14","endDate":"2017-12-29 19:55:14","performDesc":"222","opportunityState":"0","applyID":"1","operationDate":"2017-12-06 09:54:25.0","performDate":"Dec 29","performTime":"6:55PM-7:55PM","performerOpportunity":null,"distance":null,"scheduleList":null},{"createDate":"2017-12-04 11:38","venueID":9,"venueName":"夏先生","headImg":null,"venueAddress":"中国广东省深圳市龙岗区","phoneNumber":"13510762742","webSiteAddress":null,"longitude":114.25,"latitude":22.72,"venueScore":4.4,"venueTypeName":"Exclue 21+,Serves Food,Serves Alcohol","twitterAccount":"654321","facebookAccount":"12345678","isHaveCharges":null,"venueEmail":"747616936@qq.com","opportunityID":"3","startDate":"2017-12-30 02:00:00","endDate":"2017-12-14 03:30:00","performDesc":"兔兔JJ啦","opportunityState":"0","applyID":null,"operationDate":"2017-12-07 14:34:24.0","performDate":"Dec 30","performTime":"2:00AM-3:30AM","performerOpportunity":null,"distance":null,"scheduleList":null}]
     * distance : 5188.15
     * scheduleList : null
     */

    private String createDate;
    private String venueID;
    private String venueName;
    private String headImg;
    private String venueAddress;
    private String phoneNumber;
    private String webSiteAddress;
    private String longitude;
    private String latitude;
    private String venueScore;
    private String venueTypeName;
    private String twitterAccount;
    private String facebookAccount;
    private String isHaveCharges;
    private String venueEmail;
    private String opportunityID;
    private String startDate;
    private String endDate;
    private String performDesc;
    private String opportunityState;
    private String applyID;
    private String operationDate;
    private String performDate;
    private String performTime;
    private String distance;
    private String scheduleList;
    private List<PerformerOpportunityBean> performerOpportunity;

    public OpportunityModel(Parcel in) {
        createDate = in.readString();
        venueID = in.readString();
        venueName = in.readString();
        headImg = in.readString();
        venueAddress = in.readString();
        phoneNumber = in.readString();
        webSiteAddress = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        venueScore = in.readString();
        venueTypeName = in.readString();
        twitterAccount = in.readString();
        facebookAccount = in.readString();
        isHaveCharges = in.readString();
        venueEmail = in.readString();
        opportunityID = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        performDesc = in.readString();
        opportunityState = in.readString();
        applyID = in.readString();
        operationDate = in.readString();
        performDate = in.readString();
        performTime = in.readString();
        distance = in.readString();
        scheduleList = in.readString();
    }

    public static final Creator<OpportunityModel> CREATOR = new Creator<OpportunityModel>() {
        @Override
        public OpportunityModel createFromParcel(Parcel in) {
            return new OpportunityModel(in);
        }

        @Override
        public OpportunityModel[] newArray(int size) {
            return new OpportunityModel[size];
        }
    };

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebSiteAddress() {
        return webSiteAddress;
    }

    public void setWebSiteAddress(String webSiteAddress) {
        this.webSiteAddress = webSiteAddress;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getVenueScore() {
        return venueScore;
    }

    public void setVenueScore(String venueScore) {
        this.venueScore = venueScore;
    }

    public String getVenueTypeName() {
        return venueTypeName;
    }

    public void setVenueTypeName(String venueTypeName) {
        this.venueTypeName = venueTypeName;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public void setTwitterAccount(String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }

    public String getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(String facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    public String getIsHaveCharges() {
        return isHaveCharges;
    }

    public void setIsHaveCharges(String isHaveCharges) {
        this.isHaveCharges = isHaveCharges;
    }

    public String getVenueEmail() {
        return venueEmail;
    }

    public void setVenueEmail(String venueEmail) {
        this.venueEmail = venueEmail;
    }

    public String getOpportunityID() {
        return opportunityID;
    }

    public void setOpportunityID(String opportunityID) {
        this.opportunityID = opportunityID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPerformDesc() {
        return performDesc;
    }

    public void setPerformDesc(String performDesc) {
        this.performDesc = performDesc;
    }

    public String getOpportunityState() {
        return opportunityState;
    }

    public void setOpportunityState(String opportunityState) {
        this.opportunityState = opportunityState;
    }

    public String getApplyID() {
        return applyID;
    }

    public void setApplyID(String applyID) {
        this.applyID = applyID;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getPerformDate() {
        return performDate;
    }

    public void setPerformDate(String performDate) {
        this.performDate = performDate;
    }

    public String getPerformTime() {
        return performTime;
    }

    public void setPerformTime(String performTime) {
        this.performTime = performTime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(String scheduleList) {
        this.scheduleList = scheduleList;
    }

    public List<PerformerOpportunityBean> getPerformerOpportunity() {
        return performerOpportunity;
    }

    public void setPerformerOpportunity(List<PerformerOpportunityBean> performerOpportunity) {
        this.performerOpportunity = performerOpportunity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createDate);
        dest.writeString(venueID);
        dest.writeString(venueName);
        dest.writeString(headImg);
        dest.writeString(venueAddress);
        dest.writeString(phoneNumber);
        dest.writeString(webSiteAddress);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(venueScore);
        dest.writeString(venueTypeName);
        dest.writeString(twitterAccount);
        dest.writeString(facebookAccount);
        dest.writeString(isHaveCharges);
        dest.writeString(venueEmail);
        dest.writeString(opportunityID);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(performDesc);
        dest.writeString(opportunityState);
        dest.writeString(applyID);
        dest.writeString(operationDate);
        dest.writeString(performDate);
        dest.writeString(performTime);
        dest.writeString(distance);
        dest.writeString(scheduleList);
    }

    public static class PerformerOpportunityBean {
        /**
         * createDate : 2017-12-07 14:36
         * venueID : 9
         * venueName : 夏先生
         * headImg : null
         * venueAddress : 中国广东省深圳市龙岗区
         * phoneNumber : 13510762742
         * webSiteAddress : null
         * longitude : 114.25
         * latitude : 22.72
         * venueScore : 4.4
         * venueTypeName : Exclue 21+,Serves Food,Serves Alcohol
         * twitterAccount : 654321
         * facebookAccount : 12345678
         * isHaveCharges : null
         * venueEmail : 747616936@qq.com
         * opportunityID : 6
         * startDate : 2018-12-07 21:30:00
         * endDate : 2018-12-07 23:30:00
         * performDesc : 明年的今天有一场表演，需要一名吉他手，键盘手，以及数名歌唱演员
         * opportunityState : 1
         * applyID : 1
         * operationDate : 2017-12-08 14:12:20.0
         * performDate : Dec 07
         * performTime : 9:30PM-11:30PM
         * performerOpportunity : null
         * distance : null
         * scheduleList : null
         */

        private String createDate;
        private String venueID;
        private String venueName;
        private String headImg;
        private String venueAddress;
        private String phoneNumber;
        private String webSiteAddress;
        private String longitude;
        private String latitude;
        private String venueScore;
        private String venueTypeName;
        private String twitterAccount;
        private String facebookAccount;
        private String isHaveCharges;
        private String venueEmail;
        private String opportunityID;
        private String startDate;
        private String endDate;
        private String performDesc;
        private String opportunityState;
        private String applyID;
        private String operationDate;
        private String performDate;
        private String performTime;
        private String performerOpportunity;
        private String distance;
        private String scheduleList;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getVenueID() {
            return venueID;
        }

        public void setVenueID(String venueID) {
            this.venueID = venueID;
        }

        public String getVenueName() {
            return venueName;
        }

        public void setVenueName(String venueName) {
            this.venueName = venueName;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getVenueAddress() {
            return venueAddress;
        }

        public void setVenueAddress(String venueAddress) {
            this.venueAddress = venueAddress;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getWebSiteAddress() {
            return webSiteAddress;
        }

        public void setWebSiteAddress(String webSiteAddress) {
            this.webSiteAddress = webSiteAddress;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getVenueScore() {
            return venueScore;
        }

        public void setVenueScore(String venueScore) {
            this.venueScore = venueScore;
        }

        public String getVenueTypeName() {
            return venueTypeName;
        }

        public void setVenueTypeName(String venueTypeName) {
            this.venueTypeName = venueTypeName;
        }

        public String getTwitterAccount() {
            return twitterAccount;
        }

        public void setTwitterAccount(String twitterAccount) {
            this.twitterAccount = twitterAccount;
        }

        public String getFacebookAccount() {
            return facebookAccount;
        }

        public void setFacebookAccount(String facebookAccount) {
            this.facebookAccount = facebookAccount;
        }

        public String getIsHaveCharges() {
            return isHaveCharges;
        }

        public void setIsHaveCharges(String isHaveCharges) {
            this.isHaveCharges = isHaveCharges;
        }

        public String getVenueEmail() {
            return venueEmail;
        }

        public void setVenueEmail(String venueEmail) {
            this.venueEmail = venueEmail;
        }

        public String getOpportunityID() {
            return opportunityID;
        }

        public void setOpportunityID(String opportunityID) {
            this.opportunityID = opportunityID;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getPerformDesc() {
            return performDesc;
        }

        public void setPerformDesc(String performDesc) {
            this.performDesc = performDesc;
        }

        public String getOpportunityState() {
            return opportunityState;
        }

        public void setOpportunityState(String opportunityState) {
            this.opportunityState = opportunityState;
        }

        public String getApplyID() {
            return applyID;
        }

        public void setApplyID(String applyID) {
            this.applyID = applyID;
        }

        public String getOperationDate() {
            return operationDate;
        }

        public void setOperationDate(String operationDate) {
            this.operationDate = operationDate;
        }

        public String getPerformDate() {
            return performDate;
        }

        public void setPerformDate(String performDate) {
            this.performDate = performDate;
        }

        public String getPerformTime() {
            return performTime;
        }

        public void setPerformTime(String performTime) {
            this.performTime = performTime;
        }

        public String getPerformerOpportunity() {
            return performerOpportunity;
        }

        public void setPerformerOpportunity(String performerOpportunity) {
            this.performerOpportunity = performerOpportunity;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getScheduleList() {
            return scheduleList;
        }

        public void setScheduleList(String scheduleList) {
            this.scheduleList = scheduleList;
        }
    }
}
