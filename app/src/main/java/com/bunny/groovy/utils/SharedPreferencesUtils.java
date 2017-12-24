package com.bunny.groovy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 */
public class SharedPreferencesUtils {
    /**
     * 保存在手机里面的文件名
     */
    private static final String USER_FILE = "share_user_data";
    private static final String APP_FILE = "share_app_data";


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setUserParam(Context context, String key, Object object) {
        if (context == null) {
            return;
        }
//		if(Connect.isLogin(null)&&!key.startsWith("theFirstTime")){
//			key+=Connect.getUserBean().getResult().getUserId();
//		}
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(USER_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }
        editor.commit();
    }

    public static void clearUserData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public static void setAppParam(Context context, String key, Object object) {
        if (context == null) {
            return;
        }
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(APP_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }
        editor.commit();
    }

    public static Object getAppParam(Context context, String key, Object defaultObject) {
        if (context == null) {
            return null;
        }
//		if(Connect.isLogin(null)&&!key.startsWith("theFirstTime")){
//			key+=Connect.getUserBean().getResult().getUserId();
//		}
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(APP_FILE, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }



    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     * @param b       如果登陆，是否自动添加用户ID
     */
    public static void setUserParam(Context context, String key, Object object, boolean b) {
        if (context == null) {
            return;
        }
//		if(b){
//			if(Connect.isLogin(null)&&!key.startsWith("theFirstTime")){
//				key+=Connect.getUserBean().getResult().getUserId();
//			}
//		}
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(USER_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }
        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getUserParam(Context context, String key, Object defaultObject) {
        if (context == null) {
            return null;
        }
//		if(Connect.isLogin(null)&&!key.startsWith("theFirstTime")){
//			key+=Connect.getUserBean().getResult().getUserId();
//		}
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(USER_FILE, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @param b             如果登陆，是否自动添加用户ID
     * @return
     */
    public static Object getUserParam(Context context, String key, Object defaultObject, boolean b) {
        if (context == null) {
            return null;
        }
//		if(b){
//			if(Connect.isLogin(null)&&!key.startsWith("theFirstTime")){
//				key+=Connect.getUserBean().getResult().getUserId();
//			}
//		}
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(USER_FILE, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }


}
