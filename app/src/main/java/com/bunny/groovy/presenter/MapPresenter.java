package com.bunny.groovy.presenter;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.view.IMapView;

/**
 * 地图页面控制器
 *
 * Created by Administrator on 2017/12/17.
 */

public class MapPresenter extends BasePresenter<IMapView> {

    public MapPresenter(IMapView view) {
        super(view);
    }

    public void getVenueByKeyword(String keyword){
        addSubscription(apiService.getVenueList(keyword), new SubscriberCallBack(null) {
            @Override
            protected void onSuccess(Object response) {

            }

            @Override
            protected void onFailure(ResultResponse response) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }
}
