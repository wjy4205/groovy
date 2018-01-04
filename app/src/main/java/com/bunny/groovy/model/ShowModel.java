package com.bunny.groovy.model;

import android.os.Parcel;
import android.os.Parcelable;

/****************************************
 * 功能说明:  演出者下一个表演
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public class ShowModel implements Parcelable {

    /**
     * createDate : 2017-12-25 10:38
     * venueID : 8
     * venueName : XU LANG FEN
     * headImg : null
     * venueAddress : XU LANG FEN
     * phoneNumber : 18321320583
     * webSiteAddress : www.baidu.com
     * longitude : 121.65
     * latitude : 31.23
     * venueScore : 0
     * venueTypeName : Exclue 21+,Serves Food,Serves Alcohol
     * twitterAccount : 22222222
     * facebookAccount : 11111111
     * isHaveCharges : 0
     * venueEmail : null
     * performID : 46
     * performStartDate : 2017-12-29 12:00
     * performEndDate : 2017-12-29 18:30
     * performType : POP,BLUES
     * performDesc : 这是描述信息
     * performerID : 26
     * performerName : 巴银
     * pheadImg : http://47.100.104.82:8083/upload/headImgFile/headImg_1514168839014.3.839-_4345c67e90434b3ab048c6fb0a241c8f.jpg
     * plongitude : null
     * platitude : null
     * performerType : null
     * performState : 0
     * isOpportunity : 0
     * publishType : 0
     * ptwitterAccount : null
     * pfacebookAccount : null
     * distance : null
     * pvenueScore : 0
     * signature : 个性签名
     * soundcloudAccount : null
     * musicFile : music_1514168839013.mp3
     * stageName : null
     * zipCode : null
     * performDate : Dec 29
     * performTime : 0:00PM-6:30PM
     * week : null
     * venueLongitude : 121.65
     * venueLatitude : 31.23
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
    private String performID;
    private String performStartDate;
    private String performEndDate;
    private String performType;
    private String performDesc;
    private String performerID;
    private String performerName;
    private String pheadImg;
    private String plongitude;
    private String platitude;
    private String performerType;
    private String performState;
    private String isOpportunity;
    private String publishType;
    private String ptwitterAccount;
    private String pfacebookAccount;
    private String distance;
    private String pvenueScore;
    private String signature;
    private String soundcloudAccount;
    private String musicFile;
    private String stageName;
    private String zipCode;
    private String performDate;
    private String performTime;
    private String week;
    private String venueLongitude;
    private String venueLatitude;
    private String scheduleList;
    private String handlerDate;
    private String applyState;
    private String opportunityState;
    private String operationDate;
    private String inviteID;//邀请id
    private String invitationDesc;// 邀请描述
    private String invitationState;//状态（0-未处理，1-同意，2-拒绝）

    protected ShowModel(Parcel in) {
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
        performID = in.readString();
        performStartDate = in.readString();
        performEndDate = in.readString();
        performType = in.readString();
        performDesc = in.readString();
        performerID = in.readString();
        performerName = in.readString();
        pheadImg = in.readString();
        plongitude = in.readString();
        platitude = in.readString();
        performerType = in.readString();
        performState = in.readString();
        isOpportunity = in.readString();
        publishType = in.readString();
        ptwitterAccount = in.readString();
        pfacebookAccount = in.readString();
        distance = in.readString();
        pvenueScore = in.readString();
        signature = in.readString();
        soundcloudAccount = in.readString();
        musicFile = in.readString();
        stageName = in.readString();
        zipCode = in.readString();
        performDate = in.readString();
        performTime = in.readString();
        week = in.readString();
        venueLongitude = in.readString();
        venueLatitude = in.readString();
        scheduleList = in.readString();
        handlerDate = in.readString();
        applyState = in.readString();
        opportunityState = in.readString();
        operationDate = in.readString();
        inviteID = in.readString();
        invitationDesc = in.readString();
        invitationState = in.readString();
    }

    public static final Creator<ShowModel> CREATOR = new Creator<ShowModel>() {
        @Override
        public ShowModel createFromParcel(Parcel in) {
            return new ShowModel(in);
        }

        @Override
        public ShowModel[] newArray(int size) {
            return new ShowModel[size];
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

    public String getPerformID() {
        return performID;
    }

    public void setPerformID(String performID) {
        this.performID = performID;
    }

    public String getPerformStartDate() {
        return performStartDate;
    }

    public void setPerformStartDate(String performStartDate) {
        this.performStartDate = performStartDate;
    }

    public String getPerformEndDate() {
        return performEndDate;
    }

    public void setPerformEndDate(String performEndDate) {
        this.performEndDate = performEndDate;
    }

    public String getPerformType() {
        return performType;
    }

    public void setPerformType(String performType) {
        this.performType = performType;
    }

    public String getPerformDesc() {
        return performDesc;
    }

    public void setPerformDesc(String performDesc) {
        this.performDesc = performDesc;
    }

    public String getPerformerID() {
        return performerID;
    }

    public void setPerformerID(String performerID) {
        this.performerID = performerID;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    public String getPheadImg() {
        return pheadImg;
    }

    public void setPheadImg(String pheadImg) {
        this.pheadImg = pheadImg;
    }

    public String getPlongitude() {
        return plongitude;
    }

    public void setPlongitude(String plongitude) {
        this.plongitude = plongitude;
    }

    public String getPlatitude() {
        return platitude;
    }

    public void setPlatitude(String platitude) {
        this.platitude = platitude;
    }

    public String getPerformerType() {
        return performerType;
    }

    public void setPerformerType(String performerType) {
        this.performerType = performerType;
    }

    public String getPerformState() {
        return performState;
    }

    public void setPerformState(String performState) {
        this.performState = performState;
    }

    public String getIsOpportunity() {
        return isOpportunity;
    }

    public void setIsOpportunity(String isOpportunity) {
        this.isOpportunity = isOpportunity;
    }

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public String getPtwitterAccount() {
        return ptwitterAccount;
    }

    public void setPtwitterAccount(String ptwitterAccount) {
        this.ptwitterAccount = ptwitterAccount;
    }

    public String getPfacebookAccount() {
        return pfacebookAccount;
    }

    public void setPfacebookAccount(String pfacebookAccount) {
        this.pfacebookAccount = pfacebookAccount;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPvenueScore() {
        return pvenueScore;
    }

    public void setPvenueScore(String pvenueScore) {
        this.pvenueScore = pvenueScore;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSoundcloudAccount() {
        return soundcloudAccount;
    }

    public void setSoundcloudAccount(String soundcloudAccount) {
        this.soundcloudAccount = soundcloudAccount;
    }

    public String getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(String musicFile) {
        this.musicFile = musicFile;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getVenueLongitude() {
        return venueLongitude;
    }

    public void setVenueLongitude(String venueLongitude) {
        this.venueLongitude = venueLongitude;
    }

    public String getVenueLatitude() {
        return venueLatitude;
    }

    public void setVenueLatitude(String venueLatitude) {
        this.venueLatitude = venueLatitude;
    }

    public String getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(String scheduleList) {
        this.scheduleList = scheduleList;
    }

    public String getHandlerDate() {
        return handlerDate;
    }

    public void setHandlerDate(String handlerDate) {
        this.handlerDate = handlerDate;
    }

    public String getApplyState() {
        return applyState;
    }

    public void setApplyState(String applyState) {
        this.applyState = applyState;
    }

    public String getOpportunityState() {
        return opportunityState;
    }

    public void setOpportunityState(String opportunityState) {
        this.opportunityState = opportunityState;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getInviteID() {
        return inviteID;
    }

    public void setInviteID(String inviteID) {
        this.inviteID = inviteID;
    }

    public String getInvitationDesc() {
        return invitationDesc;
    }

    public void setInvitationDesc(String invitationDesc) {
        this.invitationDesc = invitationDesc;
    }

    public String getInvitationState() {
        return invitationState;
    }

    public void setInvitationState(String invitationState) {
        this.invitationState = invitationState;
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
        dest.writeString(performID);
        dest.writeString(performStartDate);
        dest.writeString(performEndDate);
        dest.writeString(performType);
        dest.writeString(performDesc);
        dest.writeString(performerID);
        dest.writeString(performerName);
        dest.writeString(pheadImg);
        dest.writeString(plongitude);
        dest.writeString(platitude);
        dest.writeString(performerType);
        dest.writeString(performState);
        dest.writeString(isOpportunity);
        dest.writeString(publishType);
        dest.writeString(ptwitterAccount);
        dest.writeString(pfacebookAccount);
        dest.writeString(distance);
        dest.writeString(pvenueScore);
        dest.writeString(signature);
        dest.writeString(soundcloudAccount);
        dest.writeString(musicFile);
        dest.writeString(stageName);
        dest.writeString(zipCode);
        dest.writeString(performDate);
        dest.writeString(performTime);
        dest.writeString(week);
        dest.writeString(venueLongitude);
        dest.writeString(venueLatitude);
        dest.writeString(scheduleList);
        dest.writeString(handlerDate);
        dest.writeString(applyState);
        dest.writeString(opportunityState);
        dest.writeString(operationDate);
        dest.writeString(invitationDesc);
        dest.writeString(invitationState);
        dest.writeString(inviteID);
    }
}
