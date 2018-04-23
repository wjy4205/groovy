package com.bunny.groovy.presenter;

import com.bunny.groovy.api.ApiRetrofit;
import com.bunny.groovy.api.ApiService;
import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.model.ScheduleModel;
import com.bunny.groovy.model.ShowModel;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.utils.Utils;
import com.bunny.groovy.view.IScheduleView;
import com.bunny.groovy.weidget.ProgressHUD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

        //show progress
        final ProgressHUD show = ProgressHUD.show(mView.get(), "", false, false, null);
        addSubscription(apiService.getScheduleList(startDate, endTime), new Subscriber<String>() {
            @Override
            public void onCompleted() {
                if (show!=null && show.isShowing()) show.dismiss();
            }

            @Override
            public void onStart() {
                super.onStart();
                if (show!=null) show.show();
            }

            @Override
            public void onError(Throwable e) {
                if (show!=null && show.isShowing()) show.dismiss();
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

    /**
     * 推广
     */
    public void spotlightPerform(String performID,String userID) {
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        apiService.spotlightPerform(performID, userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultResponse<Object>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        UIUtils.showBaseToast(e.toString());
                        KLog.d(e.toString());
                    }

                    @Override
                    public void onNext(ResultResponse<Object> response) {
                        if (response.success) {
                            requestUserData();
                            UIUtils.showBaseToast("This show has joined the promotion.");

                        } else {
                            UIUtils.showBaseToast(response.errorMsg);
                        }
                    }
                });
    }

    /**
     * 获取用户数据
     */
    public void requestUserData() {
        addSubscription(apiService.getPerformerInfo(),
                new SubscriberCallBack<PerformerUserModel>(mView.get()) {
                    @Override
                    protected void onSuccess(PerformerUserModel response) {
                        response.setUserType(String.valueOf(AppConstants.USER_TYPE_MUSICIAN));
                        Utils.initLoginData(mView.get(), response);
                    }

                    @Override
                    protected void onFailure(ResultResponse response) {

                    }
                });
    }
}
