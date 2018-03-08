package com.bunny.groovy.model;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/25.
 ****************************************/

public class ShowMusicianHistoryModel {

    public String performID; // 演出编号
    public String venueID; // 演出厅编号【用户】
    public String performStartDate; // 开始时间
    public String performEndDate; // 结束时间
    public String performType; // 表演类型
    public String performerID; // 表演者编号【用户】
    public String performState; // 演出状态（0-待验证1-已发布 2-已取消）
    public String isOpportunity; // 是否是推广演出（0-否 1-是）
    public String performerName;// 表演者名称（艺名）
    public String performDesc;// 表演描述
    public String venueName;// 演出厅名称
    public String venueAddress;// 演出厅地址
    public String publishType;// 0-申请 1-主动发布的
    public String venueLongitude;// 演出厅经度（°）
    public String venueLatitude;// 演出厅纬度（°）
    // 表演时间
    public String performDate;// 日期
    public String performTime;// 时间

    // 表演者信息
    public String performerImg;// 图片
    public String performerMusic;// 音频文件
    public String performerScore;// 平均评分
    public String performerFacebook;// facebook
    public String performerTwitter;// twitter
    public String performerSoundcloud;// soundcloud
    public String performerSignature;// 个性签名

    // 演出厅信息
    public String venueScore;// 评分数
    public String venueFacebook;// facebook
    public String venueTwitter;// twitter
    public String venueTypeName;// 演出厅类型
    public String isHaveCharges;// 当天是否付费
    public String venueBookingPhone;// 预定电话
    public String venueWebSite;// 网址
    public String venueImg;// 图片

}
