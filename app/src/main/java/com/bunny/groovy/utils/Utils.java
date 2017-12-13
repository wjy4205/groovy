package com.bunny.groovy.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.socks.library.KLog;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
}
