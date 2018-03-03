package com.bunny.groovy.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.bunny.groovy.api.ApiConstants;
import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.GoogleMapLoc;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.ui.MainActivity;
import com.bunny.groovy.ui.setfile.SetFile2Activity;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ISingUpView;
import com.bunny.groovy.weidget.ProgressHUD;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/12.
 ****************************************/

public class VenueRegisterPresenter extends BasePresenter<ISingUpView> {

    public VenueRegisterPresenter(ISingUpView view) {
        super(view);
    }

    /**
     * 检验邮箱验证码
     *
     * @param code
     */
    public void checkEmailCode(String code, final String userAccount) {
        addSubscription(apiService.chekEmailCodeRegister(code, userAccount), new SubscriberCallBack<ResultResponse>(mView.get()) {
            @Override
            protected void onSuccess(ResultResponse response) {
                EventBus.getDefault().post(AppConstants.Code_Verify_Correct);
            }

            @Override
            protected void onFailure(ResultResponse response) {
                EventBus.getDefault().post(AppConstants.Code_Verify_Invalid);
            }
        });
    }

    public void requestStyle() {
    }

    /**
     * 上次用户资料
     *
     * @param fileMap
     */
    public void updateUserInfo(Map<String, String> fileMap) {
        fileMap.put("userID", AppCacheData.getPerformerUserModel().getUserID());
        //头像图片
        String imagePath = fileMap.get("imgfile");
        //音频文件
        String musicPath = fileMap.get("music");
        //构建body
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Set<Map.Entry<String, String>> entries = fileMap.entrySet();
        for (Map.Entry<String, String> entry :
                entries) {
            //文本参数
            if (!TextUtils.isEmpty(entry.getValue()) && (!entry.getValue().equals("imgfile") || !entry.getValue().equals("music"))) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        //文件参数
        if (!TextUtils.isEmpty(imagePath)) {
            File img = new File(imagePath);
            if (img.isFile()) {
                builder.addFormDataPart("imgfile", img.getName(), RequestBody.create(MediaType.parse("image/*"), img));
            }
        } else {
            builder.addFormDataPart("imgfile", "image.jpg", RequestBody.create(MediaType.parse("image/*"), ""));
        }

        if (!TextUtils.isEmpty(musicPath)) {
            File music = new File(musicPath);
            if (music.isFile()) {
                builder.addFormDataPart("music", music.getName(), RequestBody.create(MediaType.parse("*/*"), music));
            }
        } else {
            builder.addFormDataPart("music", "music.mp3", RequestBody.create(MediaType.parse("*/*"), ""));
        }


        RequestBody build = builder.build();

        addSubscription(apiService.updatePerformerInfo(build), new SubscriberCallBack<Object>(mView.get()) {
            @Override
            protected boolean isShowProgress() {
                return true;
            }

            @Override
            protected void onSuccess(Object response) {
                MainActivity.launch(mView.get());
                mView.get().setResult(AppConstants.ACTIVITY_FINISH);
                mView.get().finish();
            }

            @Override
            protected void onFailure(ResultResponse response) {
            }
        });

    }

    public void registerVenue(String userName, String userPwd, String telephone, String userEmail,
                               String checkCode, String venueTypeName, String venueAddress, String phoneNumber,
                               String webSiteAddress, String longitude, String latitude,String placeID, String twitterAccount,
                               String facebookAccount, String imgfile) {
        Map<String, String> fileMap = new HashMap<>();
        fileMap.put("userName",userName);
        fileMap.put("userPwd",userPwd);
        fileMap.put("telephone",telephone);
        fileMap.put("userEmail",userEmail);
        fileMap.put("checkCode",checkCode);
        fileMap.put("venueTypeName",venueTypeName);
        fileMap.put("venueAddress",venueAddress);
        fileMap.put("phoneNumber",phoneNumber);
        fileMap.put("webSiteAddress",webSiteAddress);
        fileMap.put("longitude",longitude);
        fileMap.put("latitude",latitude);
        fileMap.put("twitterAccount",twitterAccount);
        fileMap.put("facebookAccount",facebookAccount);
        fileMap.put("placeID",placeID);
//        addSubscription(apiService.venueRegister(userName, userPwd, telephone, userEmail,
//                checkCode, venueTypeName, venueAddress, phoneNumber,
//                webSiteAddress, mLongitude, mLatitude, twitterAccount,
//                facebookAccount,mPlaceId), new SubscriberCallBack<ResultResponse>(mView.get()) {
//            @Override
//            protected void onSuccess(ResultResponse response) {
//                UIUtils.showBaseToast("注册成功！");
//                mView.registerSuccess();
//            }
//
//            @Override
//            protected void onFailure(ResultResponse response) {
//            }
//        });

        //构建body
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Set<Map.Entry<String, String>> entries = fileMap.entrySet();
        for (Map.Entry<String, String> entry :
                entries) {
            //文本参数
                if(!TextUtils.isEmpty(entry.getValue()))builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        //文件参数
        if (!TextUtils.isEmpty(imgfile)) {
            File img = new File(imgfile);
            if (img.isFile()) {
                builder.addFormDataPart("imgfile", img.getName(), RequestBody.create(MediaType.parse("image/*"), img));
            }
        } else {
            builder.addFormDataPart("imgfile", "image.jpg", RequestBody.create(MediaType.parse("image/*"), ""));
        }


        RequestBody build = builder.build();

        addSubscription(apiService.venueRegister(build), new SubscriberCallBack<Object>(mView.get()) {
            @Override
            protected boolean isShowProgress() {
                return true;
            }

            @Override
            protected void onSuccess(Object response) {
                MainActivity.launch(mView.get());
                mView.get().setResult(AppConstants.ACTIVITY_FINISH);
                mView.get().finish();
            }

            @Override
            protected void onFailure(ResultResponse response) {
            }
        });

    }
}
