package com.bunny.groovy.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.bunny.groovy.api.ApiRetrofit;
import com.bunny.groovy.weidget.ProgressHUD;
import com.socks.library.KLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/****************************************
 * 功能说明:  音乐播放工具，缓存工具
 *
 * Author: Created by bayin on 2018/1/8.
 ****************************************/

public class MusicBox {
    private static final String TAG = MusicBox.class.getSimpleName();
    private static String music_path = Environment.getExternalStorageDirectory() + "/groovy/music";
    private static MediaPlayer sPlayer;
    private static MusicPlayCallback callback;

    private MusicBox() {
    }

    private static MusicBox mInstance = new MusicBox();

    public static MusicBox getInstance(){
        return mInstance;
    }

    public interface MusicPlayCallback {
        void playMusic();

        void stopPlay();

        void musicEnd();

    }

    public void setMusicPlayCallback(MusicPlayCallback l) {
        callback = l;
    }

    /**
     * 播放音乐
     *
     * @param path 音乐文件路径
     */
    public void playMusic(String path) {
        if (sPlayer == null)
            prepareMusicPlayer(path);

        if (sPlayer.isPlaying()) {//暂停
            sPlayer.pause();
            if (callback != null) callback.stopPlay();
        } else {//播放
            sPlayer.start();
            if (callback != null) callback.playMusic();
        }
    }

    /**
     * 初始化播放器
     *
     * @param path
     */
    private void prepareMusicPlayer(String path) {
        sPlayer = new MediaPlayer();
        sPlayer.reset();
        try {
            sPlayer.setDataSource(path);
            sPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //音乐播放结束，执行回调，音乐播放器置0
                    if (callback != null) callback.musicEnd();
                    sPlayer.seekTo(0);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放在线音乐
     *
     * @param url 音乐url
     */
    public void playOnLineMusic(String url,Context context) {
        if (TextUtils.isEmpty(url)) {
            UIUtils.showBaseToast("音乐URL为空");
        } else {
            //播放音乐
            executeMusicUrl(url,context);
        }
    }

    /**
     *
     * @param url
     * @param context
     */
    private void executeMusicUrl(final String url, Context context) {
        KLog.d("音乐路径:" + music_path);

        File file = new File(music_path + File.separator + getMusicFileName(url));

        if (file.exists()) {

            //播放本地音乐
            playMusic(file.getPath());

        } else {

            final ApiRetrofit instance = ApiRetrofit.getInstance();

            final ProgressHUD progressHUD = ProgressHUD.show(context, "缓冲音乐...", true, false, null);

            //下载缓存到本地，在播放
            Observable<ResponseBody> observable = instance.getApiService().downLoadMusic(url);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onStart() {
                            //show progress
                            progressHUD.show();
                        }

                        @Override
                        public void onCompleted() {
                            if (progressHUD != null && progressHUD.isShowing())
                                progressHUD.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (progressHUD != null && progressHUD.isShowing())
                                progressHUD.dismiss();
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            File musicFile = writeResponseBodyToDisk(responseBody, getMusicFileName(url));
                            if (musicFile != null)
                                playMusic(musicFile.getPath());
                        }
                    });
        }
    }

    /**
     * 缓存到本地
     *
     * @param body
     * @param fileName
     * @return
     */

    private File writeResponseBodyToDisk(ResponseBody body, String fileName) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(music_path + File.separator + fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return futureStudioIconFile;
            } catch (IOException e) {
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @param url 音乐地址
     * @return 缓存的音乐名称
     */
    private String getMusicFileName(String url) {
        String[] split = url.split("/");
        if (split.length > 0) {
            return split[split.length - 1];
        }
        return "";
    }
}
