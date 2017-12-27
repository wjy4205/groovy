package com.bunny.groovy.presenter;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.view.IScheduleView;

/**
 * Created by Administrator on 2017/12/28.
 */
public class SchedulePresenter extends BasePresenter<IScheduleView> {
    public SchedulePresenter(IScheduleView view) {
        super(view);
    }


    public void requestWeekList(String startDate,String endTime){
        addSubscription(apiService.getScheduleList(startDate, endTime), new SubscriberCallBack(mView.get()) {
            @Override
            protected void onSuccess(Object response) {

            }

            @Override
            protected void onFailure(ResultResponse response) {

            }
        });
    }
}
