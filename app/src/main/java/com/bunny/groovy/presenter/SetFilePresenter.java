package com.bunny.groovy.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.bunny.groovy.api.ApiConstants;
import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.GoogleMapLoc;
import com.bunny.groovy.model.PerformStyleModel;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.ui.setfile.SetFile2Activity;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.ISetFileView;
import com.bunny.groovy.weidget.ProgressHUD;
import com.google.gson.Gson;
import com.google.gson.internal.Excluder;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import rx.Subscriber;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/12.
 ****************************************/

public class SetFilePresenter extends BasePresenter<ISetFileView> {
    public SetFilePresenter(ISetFileView view) {
        super(view);
    }

    /**
     * 查询地址经纬度
     *
     * @param zipCode
     */
    public void searchLocation(String zipCode) {
        addSubscription(apiService.getLocation(zipCode, ApiConstants.GoogleMapAppKey), new Subscriber<GoogleMapLoc>() {
            ProgressHUD mProgressHUD;

            @Override
            public void onStart() {
                super.onStart();
                mProgressHUD = ProgressHUD.show(mView.get(), "", true, true, null);
                mProgressHUD.setCancelable(false);
                mProgressHUD.setMessage("");
                mProgressHUD.show();
            }

            @Override
            public void onCompleted() {
                if (mProgressHUD != null) mProgressHUD.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                if (mProgressHUD != null) mProgressHUD.dismiss();
                UIUtils.showBaseToast(e.toString());
            }

            /**
             * @param loc
             */
            @Override
            public void onNext(GoogleMapLoc loc) {
                try {
                    if ("OK".equals(loc.getStatus())) {
                        AppCacheData.getFileMap().put("longitude", loc.getResults().get(0).getGeometry().getLocation().getLng());
                        AppCacheData.getFileMap().put("latitude", loc.getResults().get(0).getGeometry().getLocation().getLat());
                        //下一页
                        mView.get().startActivityForResult(new Intent(mView.get(), SetFile2Activity.class), 1);
                    } else {
                        UIUtils.showBaseToast("ZipCode incorrect. " + loc.getError_message());
                    }
                } catch (Exception e) {
                    UIUtils.showBaseToast(e.toString());
                }
            }
        });
    }


    /**
     * 获取表演style
     */
    public void rquestStyle(){
        addSubscription(apiService.getPerformStyle(), new SubscriberCallBack<List<PerformStyleModel>>(mView.get()) {
            @Override
            protected void onSuccess(List<PerformStyleModel> response) {
                if (response!=null && response.size()>0){
                    mView.showStylePop(response);
                }else {
                    UIUtils.showBaseToast("获取style失败，稍后再试");
                }
            }

            @Override
            protected void onFailure(ResultResponse response) {
                super.onFailure(response);
            }
        });
    }

    /**
     * 上次用户资料
     *
     * @param fileMap
     */
    public void updateUserInfo(Map<String, String> fileMap) {
        HashMap<String, RequestBody> map = new HashMap<>();

        //头像图片
        String imagePath = fileMap.get("imgfile");
        if (!TextUtils.isEmpty(imagePath)) {
            File imageFile = new File(imagePath);
            if (imageFile.isFile()) {
                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                map.put("imgfile\"; filename=\"" + imageFile.getName(), imageBody);
            }
        }
        fileMap.remove("imgfile");
        //音频文件
        String musicPath = fileMap.get("music");
        KLog.d("音频文件路径", musicPath);
        if (!TextUtils.isEmpty(musicPath)) {
            File musicFile = new File(musicPath);
            if (musicFile.isFile()) {
                RequestBody musicBody = RequestBody.create(MediaType.parse("multipart/form-data"), musicFile);
                map.put("music\"; filename=\"" + musicFile.getName(), musicBody);
            }
        }
        fileMap.remove("music");

        addSubscription(apiService.updatePerformerInfo(fileMap, map), new SubscriberCallBack(mView.get()) {
            @Override
            protected boolean isShowProgress() {
                return true;
            }

            @Override
            protected void onSuccess(Object response) {

            }

            @Override
            protected void onFailure(ResultResponse response) {
                super.onFailure(response);
            }
        });
    }
}
