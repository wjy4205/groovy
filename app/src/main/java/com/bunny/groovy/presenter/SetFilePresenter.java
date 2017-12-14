package com.bunny.groovy.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.bunny.groovy.api.ApiConstants;
import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.GoogleMapLoc;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.ui.setfile.SetFile2Activity;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ISetFileView;
import com.google.gson.Gson;
import com.google.gson.internal.Excluder;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
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

    public void searchLocation(String zipCode) {
        addSubscription(apiService.getLocation(zipCode, ApiConstants.GoogleMapAppKey), new Subscriber<GoogleMapLoc>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
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
                        UIUtils.showBaseToast(loc.getError_message());
                    }
                } catch (Exception e) {
                    UIUtils.showBaseToast(e.toString());
                }
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
        String imagePath = fileMap.get("imgfile");
        if (!TextUtils.isEmpty(imagePath)) {
            File imageFile = new File(imagePath);
            if (imageFile.isFile()) {
                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
//                MultipartBody.Part headimg = MultipartBody.Part.createFormData("headimg", imageFile.getName(), imageBody);
                map.put("imgfile\"; filename=\"" + imageFile.getName(), imageBody);
            }
        }

        String musicPath = fileMap.get("music");
        if (!TextUtils.isEmpty(musicPath)) {
            File musicFile = new File(imagePath);
            if (musicFile.isFile()) {
                RequestBody musicBody = RequestBody.create(MediaType.parse("multipart/form-data"), musicFile);
                map.put("music\"; filename=\"" + musicFile.getName(), musicBody);
            }
        }

        fileMap.remove("imgfile");
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
