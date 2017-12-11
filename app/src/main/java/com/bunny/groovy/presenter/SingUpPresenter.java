package com.bunny.groovy.presenter;

import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.view.ISingUpView;
import com.socks.library.KLog;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/12/9.
 */

public class SingUpPresenter extends BasePresenter<ISingUpView> {
    public SingUpPresenter(ISingUpView view) {
        super(view);
    }

    public void checkAccount(String account) {
        addSubscription(apiService.checkAccountUsed(account), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                mView.showMessage("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                mView.showMessage(e.toString());
            }

            @Override
            public void onNext(ResponseBody body) {
                try {
                    KLog.a("checkAccount", body.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
