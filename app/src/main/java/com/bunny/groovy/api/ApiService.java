package com.bunny.groovy.api;

import com.bunny.groovy.model.ResultResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/12/6.
 */

public interface ApiService {
    //表演者用户注册
    @FormUrlEncoded
    @POST("FrontUserController/performerFrontUserRegister")
    Observable<ResponseBody> showerRegister(@Field("userName") String userName, @Field("userPwd") String userPwd,
                                            @Field("telephone") String telephone, @Field("userEmail") String userEmail);

    //检查邮箱是否被占用
    @FormUrlEncoded
    @POST("FrontUserController/checkUserAccountRegister")
    Observable<ResultResponse> checkAccountUsed(@Field("userAccount") String userAccount);

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

    //test
    @GET("FrontUserController/setSessiontest")
    Call<Object> testSet(@Query("code")String code);

    @GET("FrontUserController/getSessiontest")
    Observable<Object> testGet();
}
