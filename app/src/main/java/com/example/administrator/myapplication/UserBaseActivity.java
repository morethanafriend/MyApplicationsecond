//package com.example.administrator.myapplication;
//
///**
// * Created by Administrator on 2016/9/22.
// */
//
//import android.annotation.TargetApi;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.administrator.myapplication.moudle.UserApplication;
//import com.readystatesoftware.systembartint.SystemBarTintManager;
//import com.tendcloud.tenddata.TCAgent;
//import com.umeng.analytics.MobclickAgent;
//
//import java.util.ArrayList;
//
//import cn.piaofun.common.base.BaseActivity;
//
///**
// * Created by zhangll on 15/8/14.
// * 用户端基类 Activity
// */
//public abstract class UserBaseActivity extends BaseActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setEmptyLayoutId(R.layout.activity_empty);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }
//    }
//
//    /**
//     * 设置状态栏颜色
//     * @param on
//     */
//    @TargetApi(19)
//    protected void setTranslucentStatus(boolean on) {
//        Window win = getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setNavigationBarTintEnabled(true);
////        tintManager.setStatusBarTintResource(R.color.navigation_black);//状态栏所需颜色
//        //tintManager.setStatusBarTintResource(R.color.transparent);//状态栏所需颜色
//    }
//
//    public UserApplication getUserApplication() {
//        return (UserApplication) super.getPFApplication();
//    }
//
//    /**
//     * 显示一张大图
//     * 跳转至 PhotoActivity
//     * @param url
//     */
//    protected void showPicture(String url) {
//        ArrayList<String> pics = new ArrayList<>();
////        pics.add(url.startsWith(UrlConstant.BASE_IMAGE_URL) ? url : UrlConstant.BASE_IMAGE_URL + url);
//        pics.add(url);
//
//        Intent intent = new Intent(this, PhotoActivity.class);
//        intent.putStringArrayListExtra(IntentKey.KEY_COMMON, pics);
//        intent.putExtra(IntentKey.KEY_POSITION, 0);
//        startActivity(intent);
//        overridePendingTransition(R.anim.stand, R.anim.stand);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        getUserApplication().onResume();
//        if (!getUserApplication().isDebug) {
//            try {
//                //友盟统计页面
//                MobclickAgent.onResume(this);
//                //TalkingData 统计页面
//                TCAgent.onPageStart(this, getClass().getName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        getUserApplication().onPause();
//        if (!getUserApplication().isDebug) {
//            try {
//                //友盟统计页面
//                MobclickAgent.onPause(this);
//                //TalkingData 统计页面
//                TCAgent.onPageEnd(this, getClass().getName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 跳转至登录界面，覆盖转场动画
//     */
//    public void startLoginActivity() {
//        startActivity(new Intent(this, LoginActivity.class));
//        overridePendingTransition(R.anim.bottom_in, R.anim.stand);
//    }
//
//    /**
//     * 跳转至登录界面并等待返回，覆盖转场动画
//     */
//    public void startLoginActivityForResult(int requestCode) {
//        startActivityForResult(new Intent(this, LoginActivity.class), requestCode);
//        overridePendingTransition(R.anim.bottom_in, R.anim.stand);
//    }
//
//    /**
//     * 跳转至主界面的订单列表fragment，并通知有新订单
//     */
//    protected void moveToOrderFragment() {
//        getSharedPreferences()
//                .edit()
//                .putBoolean("hasNewOrder", true)
//                .commit();
//        Intent moveToOrderIntent = new Intent(BroadCast.MOVE_TO_ORDER);
//        sendBroadcast(moveToOrderIntent);
//
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra(IntentKey.KEY_POSITION, getUserApplication().orderPage);
//        startActivity(intent);
//
//        finish();
//    }
//
//    /**
//     * 显示异常界面
//     * @param errorType 错误类型
//     * @param tip 错误提示
//     */
//    @Override
//    public void setErrorContentView(ErrorType errorType, String tip) {
////        super.setErrorContentView(errorType, tip);
//        setVisibility(R.id.layout_empty, View.VISIBLE);
//        TextView errorText = (TextView) findViewById(R.id.tv_error_tip);
//        ImageView errorImage = (ImageView) findViewById(R.id.iv_error_tip);
//
//        switch (errorType) {
//            case empty:
//                errorText.setText(tip == null ? "没有数据" : tip);
//                errorImage.setImageResource(cn.piaofun.common.R.mipmap.home_page_no_such_type_show);
//                break;
//            case failed:
//                errorText.setText(tip == null ? "请求失败" : tip);
//                errorImage.setImageResource(cn.piaofun.common.R.mipmap.home_page_no_such_type_show);
//                break;
//            case network:
//                errorText.setText(tip == null ? "没有网络" : tip);
//                errorImage.setImageResource(cn.piaofun.common.R.mipmap.common_no_net_work);
//                break;
//            case service:
//                errorText.setText(tip == null ? "服务器开小差去了，程序猿知道吗？" : tip);
//                errorImage.setImageResource(cn.piaofun.common.R.mipmap.common_server_error);
//                break;
//            case frozen:
//                errorText.setText(tip == null ? "您的帐号已被冻结" : tip);
//                errorImage.setImageResource(cn.piaofun.common.R.mipmap.icon_account_locked);
//                break;
//        }
//
//        findViewById(cn.piaofun.common.R.id.btn_reload).setVisibility(View.VISIBLE);
//        findViewById(cn.piaofun.common.R.id.btn_reload).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setVisibility(R.id.layout_empty, View.GONE);
//                initActivity();
//            }
//        });
//    }
//
//    /**
//     * 显示异常界面，并设置是否可以重新加载
//     * @param errorType 错误类型
//     * @param tip 错误提示
//     * @param showReload 是否可以重新加载
//     */
//    public void setErrorContentView(ErrorType errorType, String tip, boolean showReload) {
//        setErrorContentView(errorType, tip);
//        findViewById(cn.piaofun.common.R.id.btn_reload).setVisibility(showReload ? View.VISIBLE : View.GONE);
//    }
//
//    /**
//     * 公开的弹出键盘的方法，主要提供给 fragment 调用
//     * @param delay 延时
//     */
//    public void showActivityKeyboard(long delay) {
//        showKeybord(delay);
//    }
//
//    /**
//     * 公开的收起键盘的方法，主要提供给 fragment 调用
//     */
//    public void hideActivityKeyboard() {
//        hideKeybord();
//    }
//}
