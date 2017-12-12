package com.bunny.groovy.ui.login;

import android.content.Intent;

import com.bunny.groovy.R;
import com.bunny.groovy.api.ApiService;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.base.BasePresenter;
import com.bunny.groovy.presenter.SingUpPresenter;
import com.socks.library.KLog;
import com.xw.repo.XEditText;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 表演者登陆页面
 * <p>
 * Created by Administrator on 2017/12/5.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.login_et_account)
    XEditText etPhoneOrEmail;
    @Bind(R.id.login_et_password)
    XEditText etPassword;
    private ApiService mApiService;

    @OnClick(R.id.tv_sign_up)
    void signUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @OnClick(R.id.tv_musician_login)
    void testSet() {

        mApiService.testSet("123456").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            KLog.d(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick(R.id.tv_forget_password)
    void testGet() {
        mApiService.testGet()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            KLog.d(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void initView() {
        super.initView();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://120.136.175.43:8081/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login_layout;
    }
}
