package com.jal.util;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.jal.minechat.R;

import org.apache.http.message.BasicNameValuePair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by apollo on 2017/6/28.
 */

public class Utils {

    public static void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 关闭activity
     *
     * @param activity
     */
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 打开activity
     *
     * @param activity
     * @param cls
     * @param name
     */
    public static void start_Activity(Activity activity, Class<?> cls, BasicNameValuePair... name) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (name != null)
            for (BasicNameValuePair basicNameValuePair : name) {
                intent.putExtra(basicNameValuePair.getName(), basicNameValuePair.getValue());
            }
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }

    /**
     * 判断是否有网络
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager == null) {
                Log.w("Util", "couldn't get ConnectivityManager");
            } else {
                NetworkInfo[] info = manager.getAllNetworkInfo();
                if (info == null) {
                    for (NetworkInfo networkInfo : info) {
                        if (networkInfo.isAvailable()) {
                            Log.d("Util", "network is available");
                            return true;
                        }
                    }
                }
            }

        }
        Log.d("Util", "network is not available");
        return false;
    }

    /**
     * 移除SP
     *
     * @param context
     * @param key
     */
    public static final void removeValue(Context context, String key) {
        SharedPreferences.Editor edit = getSP(context).edit();
        edit.remove(key);
        boolean result = edit.commit();
        if (!result) {
            Log.e("Util", "save " + key + " failed");
        }
    }

    public static final SharedPreferences getSP(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 字符串转日期
     *
     * @param string
     * @return
     */
    public static Date str2Date(String string) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 验证手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    private static float sDensity = 0;

    /**
     * dp转px
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dip2Px(Context context, int dip) {
        if (sDensity == 0) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(displayMetrics);
            sDensity = displayMetrics.density;
        }
        return (int) (sDensity * dip);
    }

    @SuppressWarnings("deprecation")
    public static void sendText(Context context, String msg, String title, String content, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification n = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)//点击通知后自动清除
                .setContentText(content)//
                .setContentTitle(title)//标题
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(0, n);


//        Notification notification = new Notification(R.drawable.ic_launcher, msg, System.currentTimeMillis());
//        notification.flags = Notification.FLAG_AUTO_CANCEL;

//        notification.setLatestEventInfo(context,title,content,intent);
    }


}
