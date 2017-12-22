package com.bunny.groovy.model;

/**
 * 我的收藏
 *
 * Created by Administrator on 2017/12/21.
 */

public class FavoriteModel {
    private String venueID;//演出厅编号
    private String venueName;//演出厅名称
    private String headImg;//演出厅头像
    private String venueAddress;//演出厅地址
    private String phoneNumber;//演出厅联系方式
    private String webSiteAddress;//演出厅网址
    private String longitude;//经度（°）
    private String latitude;//纬度（°）
    private String venueScore;//评分
    private String venueTypeName;//【演出厅】类型（多选）
    private String twitterAccount;//twitter用户名【表演者/演出厅】
    private String facebookAccount;//facebook用户名【表演者/演出厅】
    private String isHaveCharges;//是否需要付费（0-否 1-是）
    private String venueEmail;//【演出厅】邮箱账户

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
}
