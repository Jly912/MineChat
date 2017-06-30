package com.jal.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.jal.util.SPUtil;

import java.util.Iterator;
import java.util.List;

/**
 * Created by apollo on 2017/6/30.
 */

public class AppContext extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SPUtil.initSPUtil(context, "mineChat");

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        int pid=android.os.Process.myPid();
        String processName=getAppName(pid);
        if (processName==null||!processName.equalsIgnoreCase(context.getPackageName())){
            Log.e("print", "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

    }

    private String getAppName(int pid) {
        String processName=null;
        ActivityManager manager= (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l =
                manager.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }

        return processName;
    }


    public static Context getInstance() {
        return context;
    }
}
