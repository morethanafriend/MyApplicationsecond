package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.myapplication.moudle.UserInfo;

import org.greenrobot.eventbus.EventBus;

import cn.piaofun.common.ui.dialog.PFDialog;
import cn.piaofun.common.util.ToastUtil;

public class MainActivity extends UserViewPagerActivity implements HomepageFragment.CallBacks {

    /** 评论标签过期时间 */
    private static final int COMMENT_TAG_EXPIRED_DURATION_TIME = 24 * 60 * 60 * 1000;

    private MineFragment mineFragment;
    private OrderFragment orderFragment;

//    private MoveToOrderReceiver moveToOrderReceiver;

    private TextView tipText;

//    private HomeListener mHomeListener;
    private boolean isAppInBackground;

    private ScreenListener mScreenListener;
    private boolean hasScreenLocked;

    private PFDialog pushDialog;

    private boolean canClose = false;
    private DiscoveryFragment discoveryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        initReceivers();
        initView();
        setListener();
//        checkPush();
//
//        doGetCommentTags();
        CheckVersionUtils checkVersionUtils = new CheckVersionUtils(this);
        checkVersionUtils.checkVersion(true);

        //如果已登录，检查是否绑定推送
        UserInfo userInfo = new UserInfo(this);
        if (userInfo.isLogin()) {
            if (StringUtil.isNull(userInfo.getAlias())) {
//                doGetAlias();
            } else {
//                doGetBindPushInfo();
            }
        }

        /**
         * 首次进入则弹出承诺
         */
        if (!userInfo.hasEnterShowDetail()) {
            userInfo.setHasEnterShowDetail(true);
            new PromiseDialog(MainActivity.this).show();
        }
    }

//    /**
//     * 监听推送
//     * @param push
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onPushReceived(Push push) {
//        checkPush();
//    }

//    private void initReceivers() {
//        moveToOrderReceiver = new MoveToOrderReceiver();
//        registerReceiver(moveToOrderReceiver, new IntentFilter(BroadCast.MOVE_TO_ORDER));
//    }

    @Override
    protected void initViewPager(boolean scrollAble) {
        super.initViewPager(scrollAble);
        pager.setOffscreenPageLimit(4);
    }

    @Override
    protected void initView() {
        init(false, false);
        tipText = (TextView) findViewById(R.id.tv_tip_push);
    }

    @Override
    protected void setListener() {
        //监听锁屏
        mScreenListener = new ScreenListener(this);
        mScreenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                //TODO
            }

            @Override
            public void onScreenOff() {
                hasScreenLocked = true;
            }

            @Override
            public void onUserPresent() {
                //TODO
            }
        });
    }

    @Override
    protected void initFragment() {
        HomepageFragment homepageFragment = new HomepageFragment();
        mineFragment = new MineFragment();
        orderFragment = new OrderFragment();
        discoveryFragment = new DiscoveryFragment();

        list.add(homepageFragment);
        list.add(discoveryFragment);
        list.add(orderFragment);
        list.add(mineFragment);list.add(new WarrantFragment());
//
//        orderFragment.setListRefreshListener(new OrderFragment.ListRefreshListener() {
//            @Override
//            public void onListRefresh() {
//                clearTipView();
//            }
//        });

        super.initFragment();
    }


    @Override
    public void onAppointmentSuccess() {
        setCurrentItem(getUserApplication().orderPage, 0);
        if (orderFragment != null && orderFragment.isAdded()) {
//            orderFragment.refreshWithLoading();
        }
    }

//    @Override
//    public void setCurrentView(int page) {
//        doWhenCheckedChange(page);
//    }

    /**
     * 解决在约票界面进行的登录后，处理ui
     */
//    @Override
//    protected void onResume() {
//        EventBus.getDefault().register(this);
//        //监听home键
//        mHomeListener = new HomeListener(this);
//        mHomeListener.setOnHomePressedListener(this);
//        mHomeListener.startWatch();
//
//        if (isAppInBackground || hasScreenLocked) {
//            isAppInBackground = false;
//            hasScreenLocked = false;
//
//            if (orderFragment != null && orderFragment.isAdded()) {
//                orderFragment.refreshWhenMainScreenReturn();
//            }
//        }
//
//        changeLoginStatus();
//        checkPush();
//
//        super.onResume();
//    }

//    @Override
//    protected void onPause() {
//        mHomeListener.setOnHomePressedListener(null);
//        mHomeListener.stopWatch();
//        EventBus.getDefault().unregister(this);
//        super.onPause();
//    }

    @Override
    protected void onDestroy() {
        mScreenListener.unregisterListener();
//        unregisterReceiver(moveToOrderReceiver);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case HomepageFragment.REQUEST_SHOW_DETAIL:
                    onAppointmentSuccess();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 在Fragment中操作了登录后，改变其他Fragment中的状态
//     */
//    @Override
//    public void changeLoginStatus() {
//        if (mineFragment != null && mineFragment.isAdded()) {
//            mineFragment.triggerLogin(true);
//        }
//        if (orderFragment != null && orderFragment.isAdded()) {
//            orderFragment.currentStatus();
//        }
//    }

//    @Override
//    public void changeLogOutStatus() {
//        if (orderFragment != null && orderFragment.isAdded()) {
//            orderFragment.currentStatus();
//        }
//
//        if (mineFragment != null && mineFragment.isAdded()) {
//            UserInfo userInfo = new UserInfo(MainActivity.this);
//            mineFragment.triggerLogin(userInfo.isLogin());
//        }
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        moveToPage(intent.getIntExtra(IntentKey.KEY_POSITION, -1));
//        changeLoginStatus();
//        checkPush();
//        super.onNewIntent(intent);
//    }
//
//    private void moveToPage(int page) {
//        if (page >= 0) {
//            pager.setCurrentItem(page, false);
//            if (page == getUserApplication().orderPage) {
//                toNotifyOrderFragment();
//            }
//        }
//    }

//    @Override
//    protected void doWhenCheckedChange(int page) {
//        super.doWhenCheckedChange(page);
//        if (page == getUserApplication().orderPage) {
//            toNotifyOrderFragment();
//        }
//    }

//    /**
//     * 通知订单列表 Fragment
//     */
//    private void toNotifyOrderFragment() {
//        if (orderFragment != null && orderFragment.isAdded()) {
//            orderFragment.afterGetFocus();
//        }
//    }

//    /**
//     * 查看是否有推送
//     */
//    private void checkPush() {
//        SharedPreferences mShare = this.getSharedPreferences("piaofun.share", Context.MODE_PRIVATE);
//
//        int tipNumber = mShare.getInt("tipNumber", 0);
//        if (tipNumber == 0 || !new UserInfo(mContext).isLogin()) {
//            tipText.setVisibility(View.GONE);
//        } else {
//            tipText.setVisibility(View.VISIBLE);
//            toRefreshOrderFragment();
//        }
//    }

    /**
     * 查看是否已经绑定推送
     */
//    private void doGetBindPushInfo() {
//        new HttpRequest(this, UrlConstant.GET_IF_HAS_BIND_PUSH) {
//            @Override
//            protected void onSuccess(String response) {
//                CheckBindResponse checkBindResponse = JSON.parseObject(response, CheckBindResponse.class);
//                new UserInfo(mContext).setBound(checkBindResponse.data.bound);
//                initJPush(MainActivity.this);
//            }
//
//            @Override
//            protected void onFailed(BaseResponse result) {
//
//            }
//
//            @Override
//            protected void onError(HttpException e) {
//
//            }
//
//            @Override
//            protected void onInvalid() {
//
//            }
//        }.addParameter("imei", getUserApplication().getIMEI())
//                .post();
//    }

    /**
     * 获取 JPush 别名
     */
//    private void doGetAlias() {
//        new HttpRequest(this, UrlConstant.GET_BIND_PUSH_ALIAS) {
//            @Override
//            protected void onSuccess(String response) {
//                super.onSuccess(response);
//                GetAliasResponse getAliasResponse = JSON.parseObject(response, GetAliasResponse.class);
//                new UserInfo(mContext).setAlias(getAliasResponse.data.alias);
//                doGetBindPushInfo();
//            }
//
//            @Override
//            protected void onFailed(BaseResponse result) {
//            }
//
//            @Override
//            protected void onError(HttpException e) {
//            }
//
//            @Override
//            protected void onInvalid() {
//            }
//        }.addParameter("imei", getUserApplication().getIMEI())
//                .post();
//    }

//    private void initJPush(final Activity context) {
//        try {
//            JPushInterface.init(context.getApplicationContext());
//            JPushInterface.resumePush(context.getApplicationContext());
//            final UserInfo userInfo = new UserInfo(context);
//            //用户alias不为 空
//            if (!StringUtil.isNull(userInfo.getAlias())) {
//                JPushInterface.setAlias(MainActivity.this, userInfo.getAlias(), new TagAliasCallback() {
//                    @Override
//                    public void gotResult(int i, String s, Set<String> set) {
//                        getUserApplication().REGISTER_ID = JPushInterface.getRegistrationID(context);
//                        BindPushUtils.bindPush(mContext, userInfo.isLogin());
//                    }
//                });
//                userInfo.setBound(true);
//            }
//        } catch (Exception e) {
//        }
//    }
//
//    @Override
//    public void onHomePressed() {
//        isAppInBackground = true;
//    }

//    public class MoveToOrderReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            try {
//                moveToPage(getUserApplication().orderPage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     * 获取评论标签
     */
//    private void doGetCommentTags() {
//        //过期则重新获取 评论tag
//        if (System.currentTimeMillis() - getUserApplication().getCommentTagSaveTime() > COMMENT_TAG_EXPIRED_DURATION_TIME) {
//            new HttpRequest(this, UrlConstant.URL_GET_COMMENT_TAGS, false) {
//                @Override
//                protected void onSuccess(String res) {
//                    CommentTagResponse result = JSON.parseObject(res, CommentTagResponse.class);
//                    MainActivity.this.getUserApplication().saveCommentTags(result.data);
//                }
//            }.post();
//        }
//    }

    /**
     * 清除小红点
     */
    private void clearTipView() {
        getSharedPreferences("piaofun.share", Context.MODE_PRIVATE)
                .edit().putInt("tipNumber", 0)
                .commit();
        tipText.setVisibility(View.GONE);
    }

    /**
     * 调用订单列表刷新方法
     */
//    private void toRefreshOrderFragment() {
//        if (orderFragment != null && orderFragment.isAdded()) {
//            orderFragment.refresh();
//        }
//    }

    @Override
    public void onBackPressed() {
        if (canClose) {
            super.onBackPressed();
        } else {
            ToastUtil.showToast(getApplication(), "再按一次退出~");
            canClose = true;
            new CountDownTimer(2000, 2000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    canClose = false;
                }
            }.start();
        }
    }

}
