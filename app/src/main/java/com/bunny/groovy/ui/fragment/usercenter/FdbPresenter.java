package com.bunny.groovy.ui.fragment.usercenter;

import com.bunny.groovy.api.SubscriberCallBack;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.model.ResultResponse;
import com.bunny.groovy.utils.UIUtils;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2018/1/12.
 ****************************************/

public class FdbPresenter extends BasePresenter<IFView> {
    public FdbPresenter(IFView view) {
        super(view);
    }

    public void feedback(String content){
        addSubscription(apiService.addFeedback(content, "1"), new SubscriberCallBack(mView.get()) {
            @Override
            protected void onSuccess(Object response) {
                UIUtils.showBaseToast("success");
            }

            @Override
            protected void onFailure(ResultResponse response) {

            }

        });
    }
}
