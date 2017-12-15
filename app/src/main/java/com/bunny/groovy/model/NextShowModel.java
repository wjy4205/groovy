package com.bunny.groovy.model;

/****************************************
 * 功能说明:  演出者下一个表演
 *
 * Author: Created by bayin on 2017/12/15.
 ****************************************/

public class NextShowModel {
    private String performID;//演出编号
    private String performStartDate;//开始时间
    private String performEndDate;//结束时间
    private String performType;//表演类型
    private String performDesc;//表演描述
    private String performerID;//表演者编号
    private String performerName;//表演者名称
    private String pheadImg;//表演者头像
    private String plongitude;//表演者经度（°）
    private String platitude;//表演者纬度（°）
    private String performerType;//演出者类型（0-系统内表演者 1-系统外部表演者）
    private String performState;//演出状态（0-待验证1-已发布 2-已取消）
    private String isOpportunity;//是否是推广演出（0-否 1-是）
    private String publishType;//0-申请 1-主动发布的
    private String ptwitterAccount;//twitter用户名【表演者】
    private String pfacebookAccount;//facebook用户名【表演者】
    private String distance;//演出厅到表演者的距离
    private String pvenueScore;//表演者评分
    private String signature;//【表演者】个人描述,个性签名
    private String soundcloudAccount;//soundcloud用户名【表演者】
    private String musicFile;//【表演者】音乐文件名称
    private String stageName;//艺名【表演者】
    private String zipCode;//【表演者】邮编号
    private String performDate;// 日期
    private String performTime;// 时间
    private String week;//星期
    public String venueLongitude;// 演出厅经度（°）
    public String venueLatitude;// 演出厅纬度（°）
    private String createDate;//创建时间


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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
