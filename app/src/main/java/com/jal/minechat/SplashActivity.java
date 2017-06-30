package com.jal.minechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jal.Constants;
import com.jal.util.SPUtil;

/**
 * Created by apollo on 2017/6/30.
 */

public class SplashActivity extends AppCompatActivity {

    private ImageView iv;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_splash);
        iv = (ImageView) findViewById(R.id.iv_wel);

        intent = new Intent();
        int run_count = SPUtil.getInt("RUN_COUNT", 0);
        boolean isLogin = SPUtil.getBoolean(Constants.LoginState, false);
        Log.d("print", "---------" + run_count);
        if (run_count == 0) {
            SPUtil.putInt("RUN_COUNT", ++run_count);
            //第一次运行,直接跳转登录页面
            iv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                    start_Activity(intent);
                }
            }, 1500);

        } else {
            if (isLogin) {//登录状态跳转到主页
                getLogin();
            } else {//跳转到登录页
                intent.setClass(this, LoginActivity.class);
                start_Activity(intent);
            }
        }

    }

    private void start_Activity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        finish();
    }

    private void getLogin() {
        String name = SPUtil.getString(Constants.User_ID, "");
        String pwd = SPUtil.getString(Constants.PWD, "");
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)) {
            //不为空，直接主页
            getChatService(name, pwd);
        } else {
            SPUtil.remove(Constants.LoginState);
            intent.setClass(this,MainActivity.class);
            start_Activity(intent);
        }
    }

    private void getChatService(final String name, final String pwd) {
        EMClient.getInstance().login(name, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                SPUtil.putBoolean(Constants.LoginState, true);
                SPUtil.putString(Constants.User_ID, name);
                SPUtil.putString(Constants.PWD, pwd);
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().getAllConversations();
                Log.d("print", "登录成功！");

                iv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intent.setClass(SplashActivity.this, LoginActivity.class);
                        start_Activity(intent);
                    }
                }, 3000);

            }

            @Override
            public void onError(int i, String s) {
                Log.d("print", "登录失败！");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
