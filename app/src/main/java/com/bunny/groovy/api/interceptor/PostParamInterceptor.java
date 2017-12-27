package com.bunny.groovy.api.interceptor;

import android.text.TextUtils;

import com.bunny.groovy.base.BaseApp;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.SharedPreferencesUtils;
import com.bunny.groovy.utils.Utils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/27.
 ****************************************/

public class PostParamInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        //添加统一参数
        RequestBody formBody;
        String userID = (String) SharedPreferencesUtils.getUserParam(BaseApp.getContext(), AppConstants.KEY_USERID, "");
        if (!TextUtils.isEmpty(userID)) {
            System.out.println("performerID:" + userID);
            formBody = new FormBody.Builder()
                    .add("performerID", userID)
                    .build();
            String postBodyString = bodyToString(request.body());
            postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
            request = requestBuilder
                    .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"),
                            postBodyString))
                    .build();
        }

        return chain.proceed(request);
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
