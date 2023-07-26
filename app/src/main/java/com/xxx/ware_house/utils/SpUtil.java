package com.xxx.ware_house.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: ZhangRuixiang
 * Date: 2020/12/24
 * DES:SharedPreferences 工具类
 */
public class SpUtil {

    private static String SP_NAME = "xxx_pda";

    private static Context context = null;

    public static void init() {
    }

    public static void init(String spName) {
        SP_NAME = spName;
    }

    public static void init(Context context1){
        context = context1;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (object instanceof String) {
            edit.putString(key, (String) object);
        } else if (object instanceof Integer) {
            edit.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            edit.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            edit.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            edit.putLong(key, (Long) object);
        } else {
            edit.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(edit);
    }

    public static Object get(String key, Object defaultValue) {

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (defaultValue instanceof String) {

            return sp.getString(key, (String) defaultValue);

        } else if (defaultValue instanceof Integer) {

            return sp.getInt(key, (Integer) defaultValue);

        } else if (defaultValue instanceof Boolean) {

            return sp.getBoolean(key, (Boolean) defaultValue);

        } else if (defaultValue instanceof Float) {

            return sp.getFloat(key, (Float) defaultValue);

        } else if (defaultValue instanceof Long) {

            return sp.getLong(key, (Long) defaultValue);

        }
        return null;
    }

    /**
     * 移除某个值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        SharedPreferencesCompat.apply(edit);

    }

    /**
     * 清除所有的值
     *
     * @param context
     */
    public static void clear(Context context) {

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        SharedPreferencesCompat.apply(edit);

    }

    /**
     * 查询某个key是否存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);

    }

    /**
     * 获取所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getAll();

    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {

        private static final Method S_APPLY_METHOD = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        private static Method findApplyMethod() {

            try {
                Class<SharedPreferences.Editor> editorClass = SharedPreferences.Editor.class;
                return editorClass.getMethod("apply");
            } catch (NoSuchMethodException e) {

            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        private static void apply(SharedPreferences.Editor editor) {

            try {
                if (S_APPLY_METHOD != null) {
                    S_APPLY_METHOD.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }
            editor.commit();


        }

    }

}
