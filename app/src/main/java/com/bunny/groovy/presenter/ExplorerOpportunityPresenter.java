package com.bunny.groovy.presenter;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.OpportunityModel;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.view.IExploreView;

import java.util.List;
import java.util.Map;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/25.
 ****************************************/

public class ExplorerOpportunityPresenter extends BasePresenter<IExploreView> {
    public ExplorerOpportunityPresenter(IExploreView view) {
        super(view);
    }

    //请求周边地点数据
    public void requestOpportunityList(Map<String, String> map) {
        addSubscription(apiService.findOpportunityList(map), new SubscriberCallBack<List<OpportunityModel>>(mView.get()) {
            @Override
            protected void onSuccess(List<OpportunityModel> response) {
                if (response != null && response.size() > 0)
                    mView.setListData(response);
            }

            @Override
            protected void onFailure(ResultResponse response) {

            }

        });
    }
}
