package com.bunny.groovy.api;

import com.bunny.groovy.model.BooleanBean;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/12/6.
 */

public interface ApiService {
    //表演者用户注册
    @FormUrlEncoded
    @POST("FrontUserController/performerFrontUserRegister")
    Observable<ResponseBody> showerRegister(@Field("userName")String userName,@Field("userPwd")String userPwd,
                                            @Field("telephone")String telephone,@Field("userEmail")String userEmail);

    //发送邮箱验证码

    //检查邮箱是否被占用
    @FormUrlEncoded
    @POST("FrontUserController/checkUserAccountRegister")
    Observable<BooleanBean> checkAccountUsed(@Field("userAccount")String userAccount);
}
