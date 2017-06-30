package com.jal.minechat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jal.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jal.minechat.R.id.btn_login;

/**
 * Created by apollo on 2017/6/30.
 */

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.tv_left)
    TextView tvLeft;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_usertel)
    EditText etUsertel;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(btn_login)
    Button btnLogin;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_del)
    ImageView ivDel;
    @Bind(R.id.iv_del2)
    ImageView ivDel2;
    @Bind(R.id.tv_tel)
    TextView tvTel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        tvTitle.setText(R.string.use_phone_login);
        etPassword.addTextChangedListener(new TextChange());
        etUsertel.addTextChangedListener(new TextChange());
        ivBack.setVisibility(View.VISIBLE);
    }


    @OnClick({btn_login, R.id.tv_forget_pwd, R.id.tv_use_other_style, R.id.tv_sms_code, R.id.iv_back, R.id.iv_del, R.id.iv_del2, R.id.rl_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case btn_login:
                String pwd = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    //密码为空
                    Utils.showShortToast(this, getResources().getString(R.string.toast_empty_pwd));
                    return;
                }


                break;
            case R.id.tv_forget_pwd:
                break;
            case R.id.tv_use_other_style:
                break;
            case R.id.tv_sms_code:
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_del:
                etUsertel.setText("");
                break;
            case R.id.iv_del2:
                etPassword.setText("");
                break;
            case R.id.rl_area:
                break;
        }
    }

    private void login(){
        String name = etUsertel.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();

    }

    class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean etUser = etUsertel.getText().length() > 0;
            boolean etPwd = etPassword.getText().length() > 0;
            if (etUser) {
                btnLogin.setBackground(getResources().getDrawable(R.drawable.shape_btn_green2));
                ivDel.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(true);
            } else {
                ivDel.setVisibility(View.GONE);
                btnLogin.setBackground(getResources().getDrawable(R.drawable.shape_btn_green));
                btnLogin.setTextColor(0xFFD0EFC6);
                btnLogin.setEnabled(false);
            }

            if (etPwd) {
                ivDel2.setVisibility(View.VISIBLE);
            } else {
                ivDel2.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
