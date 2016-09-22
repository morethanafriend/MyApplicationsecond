package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2016/9/22.
 */

import android.view.View;

import com.example.administrator.myapplication.moudle.UserApplication;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;

import cn.piaofun.common.base.BaseFragment;

/**
 * Created by zhangll on 16/1/7.
 * 用户端基类 Fragment
 */
public abstract class UserBaseFragment extends BaseFragment {

    public UserApplication getUserApplication() {
        return (UserApplication) super.getPFApplication();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserApplication().isDebug) {
            try {
                MobclickAgent.onPageStart(getClass().getName()); //统计页面
                TCAgent.onPageStart(activity, getClass().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!getUserApplication().isDebug) {
            try {
                MobclickAgent.onPageStart(getClass().getName()); //统计页面
                TCAgent.onPageEnd(activity, getClass().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转至登录界面
     */
//    public void startLoginActivity() {
//        startActivity(new Intent(activity, LoginActivity.class));
//        activity.overridePendingTransition(R.anim.bottom_in, R.anim.stand);
//    }

    /**
     * 跳转至登录界面，并等待返回
     */
//    public void startLoginActivityForResult(int requestCode) {
//        startActivityForResult(new Intent(activity, LoginActivity.class), requestCode);
//        activity.overridePendingTransition(R.anim.bottom_in, R.anim.stand);
//    }

    /**
     * 设置 view 可见性的安全方法
     * @param id view 对应 id
     * @param visibility 可见性状态
     */
    protected void setVisibility(int id, int visibility) {
        setVisibility(findViewById(id), visibility);
    }

    /**
     * 设置 view 可见性的安全方法
     * @param view
     * @param visibility
     */
    protected void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }
}