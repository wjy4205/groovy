package com.bunny.groovy.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.bunny.groovy.model.MusicBean;
import com.bunny.groovy.model.PerformerUserModel;
import com.socks.library.KLog;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/****************************************
 * 功能说明:统一工具类
 *
 * Author: Created by bayin on 2017/12/13.
 ****************************************/

public class Utils {
    /**
     * 获取时区
     *
     * @return 时区
     */
    public static String getTimeZone() {
        DateFormat dateFormat = new SimpleDateFormat("Z");//‘z’小写CST；'Z'大写+0800
        String format = String.format(AppConstants.GMT_FORMAT, dateFormat.format(new Date()));
        KLog.d(format);
        return format;
    }


    /**
     * @param date
     * @return 周几, 月份. 日期.年
     */
    private static String str = "%s,%s. %s,%s";

    public static String getFormatDate(Date date) {
        String[] split = date.toString().split(" ");
        if (split.length == 6)
            return String.format(str, split[0], split[1], split[2], split[5]);
        return "";
    }

    /**
     * 判断是否为英文
     *
     * @param word
     * @return
     */
    public static boolean isEnglish(String word) {
        return word.matches("^[a-zA-Z]*");
    }

    public static void main(String[] args) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(date);
        int i = calendar.get(Calendar.MONTH);

        System.out.println(i);
    }

    /**
     * 打电话给我
     */
    public static void CallPhone(Context context, String phone) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:" + phone));//mobile为你要拨打的电话号码，模拟器中为模拟器编号也可  4001816622
        context.startActivity(intent);
    }

    /**
     * 发送邮件
     * @param context
     * @param eamil
     */
    public static void sendEmail(Context context,String eamil){
        Intent data=new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:"+eamil));
//        data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
//        data.putExtra(Intent.EXTRA_TEXT, "这是内容");
        context.startActivity(data);
    }


    /**
     * 扫描本地音频文件
     *
     * @return
     */
    public static ArrayList<HashMap<String, String>> getPlayList(String rootPath) {
        ArrayList<HashMap<String, String>> fileList = new ArrayList<>();
        try {
            File rootFolder = new File(rootPath);
            File[] files = rootFolder.listFiles(); //here you will get NPE if directory doesn't contains  any file,handle it like this.
            if (files != null)
                for (File file : files) {
                    if (file.isDirectory()) {
                        if (getPlayList(file.getAbsolutePath()) != null) {
                            fileList.addAll(getPlayList(file.getAbsolutePath()));
                        } else {
                            break;
                        }
                    } else if (file.getName().endsWith(".mp3")) {
                        HashMap<String, String> song = new HashMap<>();
                        song.put("file_path", file.getAbsolutePath());
                        song.put("file_name", file.getName());
                        song.put("file_size", (file.length() / 1024) + "M");
                        fileList.add(song);
                    }
                }
            return fileList;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取目录下的歌曲
     *
     * @param dirName
     */
    public static ArrayList<MusicBean> queryMusic(String dirName, Context context) {
        ArrayList<MusicBean> mMediaLists = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media.DATA + " like ?",
                new String[]{dirName + "%"},
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        if (cursor == null) return null;

        // id title singer data time image
        MusicBean music;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // 如果不是音乐
            String isMusic = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.IS_MUSIC));
            if (isMusic != null && isMusic.equals("")) continue;

            music = new MusicBean();
            long bytesize = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
            //小于500KB的忽略
            if (bytesize < 1024 * 500) continue;
            music.setLength(div(bytesize, 1024 * 1024, 2));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            music.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
            music.setTitle(title);
            music.setArtist(artist);
            music.setMusicPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
            KLog.d("Utils", music.getMusicPath());
//            music.setLength(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
//            music.setImage(getAlbumImage(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))));
            mMediaLists.add(music);
        }

        cursor.close();
        return mMediaLists;
    }


    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

//    private boolean isRepeat(String title, String artist) {
//        for (MusicBean music : mMediaLists) {
//            if (title.equals(music.getTitle()) && artist.equals(music.getArtist())) {
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * 登录成功缓存数据
     *
     * @param context
     * @param model
     */
    public static void initLoginData(Context context, PerformerUserModel model) {
        SharedPreferencesUtils.setParam(context, AppConstants.KEY_LOGIN, true);
        SharedPreferencesUtils.setParam(context, AppConstants.KEY_USERID, model.getUserID());
        AppCacheData.setPerformerUserModel(model);
    }
}
