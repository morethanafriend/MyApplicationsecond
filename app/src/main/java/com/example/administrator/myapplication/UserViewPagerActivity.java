package com.example.administrator.myapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.myapplication.moudle.UserApplication;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;

import cn.piaofun.common.base.ViewPagerActivity;

/**
 * Created by Administrator on 2016/9/22.
 */
public abstract class UserViewPagerActivity extends ViewPagerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEmptyLayoutId(R.layout.activity_empty);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
    }

    /**
     * 设置状态栏颜色
     * @param on
     */
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
// SystemBarTintManager色彩管理器
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(Color.parseColor("#fff000"));//通知栏所需颜色
    }

    public UserApplication getUserApplication() {
        return (UserApplication) super.getPFApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserApplication().onResume();
        if (!getUserApplication().isDebug) {
            try {
                MobclickAgent.onResume(this);
                TCAgent.onPageStart(this, getClass().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        getUserApplication().onPause();
        if (!getUserApplication().isDebug) {
            try {
                MobclickAgent.onPause(this);
                TCAgent.onPageEnd(this, getClass().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

