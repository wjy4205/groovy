package com.bunny.groovy.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/19.
 ****************************************/

public class VenueModel implements Parcelable {


    /**
     * createDate : 2017-10-30 17:35
     * venueID : 9
     * venueName : 夏先生
     * headImg : null
     * venueAddress : 中国广东省深圳市龙岗区
     * phoneNumber : 13510762742
     * webSiteAddress : www.baidu.com
     * longitude : 114.25
     * latitude : 22.72
     * venueScore : 4.4
     * venueTypeName : Exclue 21+,Serves Food,Serves Alcohol
     * twitterAccount : 654321
     * facebookAccount : 12345678
     * isHaveCharges : null
     * venueEmail : null
     * isVenueCollection : 0
     * scheduleList : [{"createDate":"2017-12-15 18:17","scheduleID":"59","venueID":"9","weekNum":"1","startDate":null,"endDate":null,"isHaveCharges":"0"},{"createDate":"2017-12-15 18:17","scheduleID":"60","venueID":"9","weekNum":"2","startDate":null,"endDate":null,"isHaveCharges":"0"},{"createDate":"2017-12-15 18:17","scheduleID":"61","venueID":"9","weekNum":"3","startDate":null,"endDate":null,"isHaveCharges":"0"},{"createDate":"2017-12-15 18:17","scheduleID":"62","venueID":"9","weekNum":"4","startDate":null,"endDate":null,"isHaveCharges":"0"},{"createDate":"2017-12-15 18:17","scheduleID":"63","venueID":"9","weekNum":"5","startDate":null,"endDate":null,"isHaveCharges":"0"},{"createDate":"2017-12-15 18:17","scheduleID":"64","venueID":"9","weekNum":"6","startDate":null,"endDate":null,"isHaveCharges":"0"},{"createDate":"2017-12-15 18:17","scheduleID":"65","venueID":"9","weekNum":"7","startDate":null,"endDate":null,"isHaveCharges":"0"}]
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
    private String isVenueCollection;
    private List<ScheduleListBean> scheduleList;

    protected VenueModel(Parcel in) {
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
        isVenueCollection = in.readString();
    }

    public static final Creator<VenueModel> CREATOR = new Creator<VenueModel>() {
        @Override
        public VenueModel createFromParcel(Parcel in) {
            return new VenueModel(in);
        }

        @Override
        public VenueModel[] newArray(int size) {
            return new VenueModel[size];
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

    public String getIsVenueCollection() {
        return isVenueCollection;
    }

    public void setIsVenueCollection(String isVenueCollection) {
        this.isVenueCollection = isVenueCollection;
    }

    public List<ScheduleListBean> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<ScheduleListBean> scheduleList) {
        this.scheduleList = scheduleList;
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
        dest.writeString(isVenueCollection);
    }

    public static class ScheduleListBean {
        /**
         * createDate : 2017-12-15 18:17
         * scheduleID : 59
         * venueID : 9
         * weekNum : 1
         * startDate : null
         * endDate : null
         * isHaveCharges : 0
         */

        private String createDate;
        private String scheduleID;
        private String venueID;
        private String weekNum;
        private String startDate;
        private String endDate;
        private String isHaveCharges;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getScheduleID() {
            return scheduleID;
        }

        public void setScheduleID(String scheduleID) {
            this.scheduleID = scheduleID;
        }

        public String getVenueID() {
            return venueID;
        }

        public void setVenueID(String venueID) {
            this.venueID = venueID;
        }

        public String getWeekNum() {
            return weekNum;
        }

        public void setWeekNum(String weekNum) {
            this.weekNum = weekNum;
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

        public String getIsHaveCharges() {
            return isHaveCharges;
        }

        public void setIsHaveCharges(String isHaveCharges) {
            this.isHaveCharges = isHaveCharges;
        }
    }
}
