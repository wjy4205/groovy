package com.bunny.groovy.api;

import android.text.TextUtils;

import com.bunny.groovy.model.ResultResponse;
import com.socks.library.KLog;

import rx.Subscriber;

/**
 * @author ChayChan
 * @description: 抽取CallBack
 * @date 2017/6/18  21:37
 */
public abstract class SubscriberCallBack<T> extends Subscriber<ResultResponse<T>> {

    @Override
    public void onNext(ResultResponse response) {
        if (response.success) {
            onSuccess((T) response.resultData);
        } else {
            com.bunny.groovy.utils.UIUtils.showToast(response.errorMsg);
            onFailure(response);
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        KLog.e(e.getLocalizedMessage());
        onError();
    }

    protected abstract void onSuccess(T response);

    protected abstract void onError();

    protected void onFailure(ResultResponse response) {
    }

}
