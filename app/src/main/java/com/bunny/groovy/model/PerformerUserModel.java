package com.bunny.groovy.model;

/****************************************
 * 功能说明:  表演者用户信息
 *
 * Author: Created by bayin on 2017/12/13.
 ****************************************/

public class PerformerUserModel {

    private String userID; // 用户编号
    private String userName; // 姓名
    private String userEmail; // 邮箱（账户）
    private String telephone; // 手机号（账户）
    private String userPwd; // 登陆密码
    private String headImg;// 头像【头像/演出厅图片/表演者图片】
    private String balance;// 账户余额
    private String userType; // 类型（0-普通用户 1-表演者 2-演出厅）
    private String performTypeName;// 【表演者】演出类别（多选）
    private String venueTypeName; // 【演出厅】类型（多选）
    private String venueAddress; // 【演出厅】地址
    private String phoneNumber; // 【演出厅/表演者】联系方式
    private String webSiteAddress; // 【演出厅/表演者】网站地址
    private String longitude;// 经度（°）
    private String latitude;// 纬度（°）
    private String venueScore; // 评分【演出厅】
    private String packageCount; // 推广包数量
    private String signature; // 【表演者】个人描述
    private String zipCode; // 【表演者】邮编号
    private String musicFile; // 【表演者】音乐文件名称
    private String paypalAccount;// paypal 账户名称
    private String twitterAccount;// twitter 账户名称
    private String soundcloudAccount;// soundcloud 账户名称
    private String facebookAccount;// facebook 账户名称
    private String facebookUID; // facebook 登陆uid
    private String googleUID;// google 登陆uid
    private String stageName;// 表演者的艺名
    private String isNonFirstLogin;// 0-是首次登陆 1-不是首次登陆
    private String updateType;// 0-首次登陆表演者完善信息
    private String isMaskedSearch;//【表演者】是否屏蔽搜索（0-否 1-是）
    private String placeID;//google地点唯一标识【演出厅】
    private String distance;// 距离
    private String starLevel;// 平均星级

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPerformTypeName() {
        return performTypeName;
    }

    public void setPerformTypeName(String performTypeName) {
        this.performTypeName = performTypeName;
    }

    public String getVenueTypeName() {
        return venueTypeName;
    }

    public void setVenueTypeName(String venueTypeName) {
        this.venueTypeName = venueTypeName;
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

    public String getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(String packageCount) {
        this.packageCount = packageCount;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(String musicFile) {
        this.musicFile = musicFile;
    }

    public String getPaypalAccount() {
        return paypalAccount;
    }

    public void setPaypalAccount(String paypalAccount) {
        this.paypalAccount = paypalAccount;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public void setTwitterAccount(String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }

    public String getSoundcloudAccount() {
        return soundcloudAccount;
    }

    public void setSoundcloudAccount(String soundcloudAccount) {
        this.soundcloudAccount = soundcloudAccount;
    }

    public String getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(String facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    public String getFacebookUID() {
        return facebookUID;
    }

    public void setFacebookUID(String facebookUID) {
        this.facebookUID = facebookUID;
    }

    public String getGoogleUID() {
        return googleUID;
    }

    public void setGoogleUID(String googleUID) {
        this.googleUID = googleUID;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getIsNonFirstLogin() {
        return isNonFirstLogin;
    }

    public void setIsNonFirstLogin(String isNonFirstLogin) {
        this.isNonFirstLogin = isNonFirstLogin;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getIsMaskedSearch() {
        return isMaskedSearch;
    }

    public void setIsMaskedSearch(String isMaskedSearch) {
        this.isMaskedSearch = isMaskedSearch;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }
}
