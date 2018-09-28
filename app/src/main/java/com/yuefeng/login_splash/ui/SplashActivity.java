package com.yuefeng.login_splash.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.common.base.codereview.BaseActivity;
import com.common.utils.AppManager;
import com.common.utils.Constans;
import com.common.utils.PreferencesUtils;
import com.luck.picture.lib.permissions.RxPermissions;
import com.yuefeng.commondemo.R;
import com.yuefeng.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.text)
    ImageView imageView;

    @Override
    protected void initData() {

    }

    @Override
    protected void setLisenter() {

    }

    @Override
    protected void widgetClick(View v) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        imageView.setBackgroundResource(R.drawable.bg_login);
        requestPermissions();

    }

    private void initCountDown() {
        boolean isHaveDatas = PreferencesUtils.getBoolean(this, Constans.HAVE_USER_DATAS);
        if (isHaveDatas) {
            String string = PreferencesUtils.getString(this, Constans.COOKIE_PREF);
            if (!TextUtils.isEmpty(string)) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        finish();
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(SplashActivity.this);
        //请求权限全部结果 Manifest.permission.CAMERA,
        rxPermission.request(

                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (!granted) {
                            showSuccessToast("App未能获取全部需要的相关权限，部分功能可能不能正常使用.");
                        }
                        //不管是否获取全部权限，进入主页面
                        initCountDown();
                    }
                });

    }



    @Override
    protected int getContentViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().getAllActivity();
    }
}
