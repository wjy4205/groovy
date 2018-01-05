package com.bunny.groovy.api;

import com.bunny.groovy.model.FavoriteModel;
import com.bunny.groovy.model.GoogleMapLoc;
import com.bunny.groovy.model.ShowModel;
import com.bunny.groovy.model.OpportunityModel;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.model.ShowHistoryModel;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.model.VenueModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/12/6.
 */

public interface ApiService {

    //检查邮箱是否被占用
    @FormUrlEncoded
    @POST("FrontUserController/checkUserAccountRegister")
    Observable<ResultResponse<Object>> checkAccountUsed(@Field("userAccount") String userAccount);

    //发送邮箱验证码
    //userType:角色类型（0-普通用户 1-表演者 2-演出厅）
    @FormUrlEncoded
    @POST("FrontUserController/getChekCodeRegister")
    Observable<ResultResponse> getEmailCheckCode(@Field("userAccount") String userAccount, @Field("userType") String userType);

    //检验邮箱验证码
    @FormUrlEncoded
    @POST("FrontUserController/chekEmailCodeRegister")
    Observable<ResultResponse> chekEmailCodeRegister(@Field("checkCode") String checkCode, @Field("userAccount") String userAccount);

    //表演者用户注册
    @FormUrlEncoded
    @POST("FrontUserController/performerFrontUserRegister")
    Observable<ResultResponse> performerRegister(@Field("userName") String userAccount, @Field("userPwd") String userPwd,
                                                 @Field("telephone") String phone, @Field("userEmail") String email);

    //表演者登录
    @FormUrlEncoded
    @POST("FrontUserController/login")
    Observable<ResultResponse<PerformerUserModel>> performerLogin(@Field("userAccount") String userAccount, @Field("userPwd") String password,
                                                                  @Field("UserType") String userType, @Field("userZone") String userZone);

    //表演者完善信息,上传文件
    @POST("FrontUserController/updatePerformerInfoFirstLogin")
    Observable<ResultResponse<Object>> updatePerformerInfo(@Body RequestBody body);


    //获取表演者个人信息
    @POST("PerformerMeController/getPerformerMeList")
    Observable<ResultResponse<PerformerUserModel>> getPerformerInfo();


    //获取表演者下一个演出
    @POST("PerformerOverviewController/getNextScheduledShow")
    Observable<ResultResponse<List<ShowModel>>> getNextShow();

    //获取表演类型
    @POST("PerformerBasicsController/getPerformTypeListNotLogin")
    Observable<ResultResponse<List<StyleModel>>> getPerformStyle();


    //获取演出厅列表-关键字查询
    @FormUrlEncoded
    @POST("PerformerOverviewController/getVenueListBykeyword")
    Observable<ResultResponse<List<VenueModel>>> getVenueList(@Field("keyword") String keyword);


    //Release Show: 发布演出-待验证演出
    @FormUrlEncoded
    @POST("PerformerOverviewController/addPerformApply")
    Observable<ResultResponse<Object>> releaseShow(@FieldMap Map<String, String> map);


    //获取收藏演出厅记录
    @POST("PerformerMeController/getPerformerMeMyFavoriteV")
    Observable<ResultResponse<List<FavoriteModel>>> getMyFavorite();


    //获取演出记录
    @POST("PerformerMeController/getPerformerMeShowHistory")
    Observable<ResultResponse<List<ShowHistoryModel>>> getHistoryList();


    //登出
    @FormUrlEncoded
    @POST("FrontUserController/loginOut")
    Observable<ResultResponse<Object>> loginOut(@Field("userID") String userId);


    //根据邮编获取经纬度
    @GET("https://maps.googleapis.com/maps/api/geocode/json")
    Observable<GoogleMapLoc> getLocation(@Query("address") String address, @Query("key") String apiKey);


    //    performerID
//    longitude
//    latitude
//    distance
//    performStartDate
//    performEndDate
    //获取表演机会列表-带条件
    @FormUrlEncoded
    @POST("PerformerOverviewController/findPerformerOverviewOpportunityList")
    Observable<ResultResponse<List<OpportunityModel>>> findOpportunityList(@FieldMap Map<String, String> map);


    //检查表演者是否已经申请过这个机会
    @FormUrlEncoded
    @POST("PerformerOverviewController/checkPerformerOpportunity")
    Observable<ResultResponse<Object>> checkPerformerApply(@Field("applyID") String applyID);

    //   venueID
//   performType
//   performStartDate
//   performEndDate
//   performDesc
//   performerID
//   opportunityID
    //申请演出机会
    @FormUrlEncoded
    @POST("PerformerOverviewController/addPerformOpportunityApply")
    Observable<ResultResponse<Observable>> applyOpportunity(@FieldMap Map<String, String> map);

    //获取演出机会申请列表
    @POST("PerformerOverviewController/getPerformerOverviewOpportunityList")
    Observable<ResultResponse<List<ShowModel>>> getApplyOpportunityList();

    //获取邀请的列表
    @POST("PerformerOverviewController/getPerformerOverviewInviteList")
    Observable<ResultResponse<List<ShowModel>>> getInviteList();

    //获取待验证演出申请列表
    @POST("PerformerOverviewController/getPerformerOverviewApplyList")
    Observable<ResultResponse<List<ShowModel>>> getReleaseShowList();

    //获取选中周期内的表演统计
    @FormUrlEncoded
    @POST("PerformerScheduleController/getPerformerScheduleList")
    Observable<String> getScheduleList(@Field("performStartDate")String performStartDate,
                                             @Field("performEndDate")String performEndDate);

    //演出厅个人主页：获取演出厅详细信息
    @FormUrlEncoded
    @POST("PerformerBasicsController/getVenueScheduleList")
    Observable<ResultResponse<VenueModel>> getVenueDetail(@Field("venueID")String venueID);


    //收藏演出厅
    @FormUrlEncoded
    @POST("PerformerBasicsController/collectionVenue")
    Observable<ResultResponse<Object>> collectionVenue(@Field("venueID")String venueID);

    //取消收藏演出厅
    @FormUrlEncoded
    @POST("PerformerBasicsController/cancelCollectionVenue")
    Observable<ResultResponse<Object>> cancelCollectionVenue(@Field("venueID")String venueID);


    //Notification：Invite-同意邀请
    @FormUrlEncoded
    @POST("PerformerOverviewController/agreePerformerOverviewInvite")
    Observable<ResultResponse<Object>> agreePerformerInvite(@Field("inviteID")String inviteID,
                                                            @Field("performType")String performType,@Field("performDesc")String performDesc);

    //Notification：Invite-拒绝邀请
    @FormUrlEncoded
    @POST("PerformerOverviewController/refusePerformerOverviewInvite")
    Observable<ResultResponse<Object>> rejectPerformInvite(@Field("inviteID")String inviteID);


    //修改用户信息
    @POST("PerformerMeController/updatePerformerMeInfo")
    Observable<ResultResponse<Object>> updateUserProfile(@Body RequestBody body);

}
