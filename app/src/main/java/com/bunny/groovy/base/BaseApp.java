package com.bunny.groovy.base;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.bunny.groovy.utils.InfAutoInflaterConvert;
import com.bunny.groovy.utils.InflaterConvert;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.yan.inflaterauto.AutoBaseOn;
import com.yan.inflaterauto.InflaterAuto;

public class BaseApp extends MultiDexApplication{

    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static Context mContext;//上下文
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//循环队列
    private static Handler mHandler;//主线程Handler

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(InflaterAuto.wrap(newBase));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        //对全局属性赋值
        mContext = getApplicationContext();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();

        /*
         * 以下可以写在任何地方，只要在生成View之前
         */
        InflaterAuto.init(new InflaterAuto.Builder()
                .width(720)
                .height(1280)
                .baseOnDirection(AutoBaseOn.Both)// 宽度根据宽度比例缩放，长度根据长度比例缩放
                // 由 com.yan.inflaterautotest.InflaterConvert 编译生成，自动添加前缀InfAuto
                // 你也可以添加你自己的实现AutoConvert的类，替换任何一种view成为你想替换的view
                .inflaterConvert(new InfAutoInflaterConvert())
                .build()
        );
    }

    /**
     * 重启当前应用
     */
    public static void restart() {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        BaseApp.mContext = mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static void setMainThread(Thread mMainThread) {
        BaseApp.mMainThread = mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        BaseApp.mMainThreadId = mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static void setMainThreadLooper(Looper mMainLooper) {
        BaseApp.mMainLooper = mMainLooper;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void setMainHandler(Handler mHandler) {
        BaseApp.mHandler = mHandler;
    }
}
