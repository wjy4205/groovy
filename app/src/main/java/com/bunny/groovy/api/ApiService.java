package com.bunny.groovy.api;

import com.bunny.groovy.model.FavoriteModel;
import com.bunny.groovy.model.GoogleMapLoc;
import com.bunny.groovy.model.NextShowModel;
import com.bunny.groovy.model.OpportunityModel;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.model.ShowHistoryModel;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.model.VenueModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @GET("PerformerMeController/getPerformerMeList")
    Observable<ResultResponse<PerformerUserModel>> getPerformerInfo(@Query("performerID") String userID);


    //获取表演者下一个演出
    @GET("PerformerOverviewController/getNextScheduledShow")
    Observable<ResultResponse<List<NextShowModel>>> getNextShow(@Query("performerID") String performerID);

    //获取表演类型
    @GET("PerformerBasicsController/getPerformTypeListNotLogin")
    Observable<ResultResponse<List<StyleModel>>> getPerformStyle();


    //获取演出厅列表-关键字查询
    @GET("PerformerOverviewController/getVenueListBykeyword")
    Observable<ResultResponse<List<VenueModel>>> getVenueList(@Query("keyword") String keyword);


    //Release Show: 发布演出-待验证演出
    @FormUrlEncoded
    @POST("PerformerOverviewController/addPerformApply")
    Observable<ResultResponse<Object>> releaseShow(@FieldMap Map<String, String> map);


    //获取收藏演出厅记录
    @GET("PerformerMeController/getPerformerMeMyFavoriteV")
    Observable<ResultResponse<List<FavoriteModel>>> getMyFavorite(@Query("performerID") String performerID);


    //获取演出记录
    @GET("PerformerMeController/getPerformerMeShowHistory")
    Observable<ResultResponse<List<ShowHistoryModel>>> getHistoryList(@Query("performerID") String performerID);


    //登出
    @GET("FrontUserController/loginOut")
    Observable<ResultResponse<Object>> loginOut(@Query("userID") String userId);


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
    @GET("PerformerOverviewController/findPerformerOverviewOpportunityList")
    Observable<ResultResponse<List<OpportunityModel>>> findOpportunityList(@QueryMap Map<String, String> map);
}
