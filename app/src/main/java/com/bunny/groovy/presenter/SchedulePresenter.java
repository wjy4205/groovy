package com.bunny.groovy.presenter;

import android.widget.Toast;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.model.ScheduleModel;
import com.bunny.groovy.model.ShowModel;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.IScheduleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/12/28.
 */
public class SchedulePresenter extends BasePresenter<IScheduleView> {
    public SchedulePresenter(IScheduleView view) {
        super(view);
    }

    private ScheduleModel mScheduleModel;
    private Gson mGson = new Gson();

    public void requestWeekList(String startDate, String endTime) {
        addSubscription(apiService.getScheduleList(startDate, endTime), new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                UIUtils.showBaseToast(e.toString());
                mView.setFailureView();
            }

            @Override
            public void onNext(String json) {
                mScheduleModel = new ScheduleModel();
                try {
                    JSONObject oJson = new JSONObject(json);
                    String success = oJson.getString("success");
                    if (success.equals("true")) {
                        JSONObject resultData = oJson.getJSONObject("resultData");
                        if (resultData != null) {
                            for (int i = 1; i < 8; i++) {
                                JSONObject objDay = resultData.getJSONObject(String.valueOf(i));
                                if (objDay.getInt("count") != 0) {
                                    List<ShowModel> listModel;
                                    Type type = new TypeToken<List<ShowModel>>() {
                                    }.getType();

                                    JSONArray arrjson = objDay.getJSONArray("performerInfo");
                                    listModel = mGson.fromJson(arrjson.toString(), type);
                                    mScheduleModel.setMap(String.valueOf(i), listModel);
                                }
                            }
                        }
                        //set view
                        mView.setSuccessView(mScheduleModel);
                    } else {
                        mView.setFailureView();
                        UIUtils.showBaseToast(oJson.getString("errorMsg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mView.setFailureView();
                    UIUtils.showBaseToast(e.toString());
                }

            }
        });
    }
}
