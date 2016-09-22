package com.example.administrator.myapplication.moudle;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.badoo.mobile.util.WeakHandler;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;

import cn.jpush.android.api.JPushInterface;
import cn.piaofun.common.base.BaseActivity;
import cn.piaofun.common.http.HttpRequest;

/**
 * Created by Administrator on 2016/9/22.
 */
public class LaunchActivity extends BaseActivity {

    /** 已跳转 */
    private boolean jumped = false;
    private WeakHandler handler;
    private LocationClient mLocationClient;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;

    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEmptyLayoutId(R.layout.activity_empty);
        // 不显示程序的标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_launch);
        //初始化百度地图
        mLocationClient = new LocationClient(this.getApplicationContext());
        initLocation();
        initVersion();
        jumpToGuide();
//        doGetCityList();
    }
    private UserApplication getUserApplication() {
        return (UserApplication) getApplication();
    }
    private void initVersion() {
        UserInfo mUserInfo = new UserInfo(mContext);
        PackageManager packageManager = mContext.getPackageManager();
        String versionName = "";

        try {
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException var5) {
            var5.printStackTrace();
        }
        //版本号发生变动则根据Constants.forceGuide决定是否进入引导页
        if (!versionName.equals(mUserInfo.getVersion())) {
            mUserInfo.setVersion(versionName);
            mUserInfo.setForced(getUserApplication().forceGuide);
            ImageLoaderUtil.clearCache();
        }
    }

    private void jumpToGuide() {
        startActivity(new Intent(this, GuideActivity.class));
        finish();
    }

    private void jumpToMain() {
        startActivity(new Intent(this, MainActivity.class));
//        startActivity(new Intent(this, TestActivity.class));
        finish();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setListener() {
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);

//        mLocationClient.registerLocationListener(new LocationListener());
    }

//    /**
//     * 获取城市列表
//     */
//    private void doGetCityList() {
//        httpRequest = new HttpRequest(this, UrlConstant.URL_GET_CITIES) {
//            @Override
//            protected void onSuccess(String res) {
//                CityResponse result = JSON.parseObject(res, CityResponse.class);
//                LaunchActivity.this.getUserApplication().saveCity(result.data);
//                mLocationClient.start();
//
//                handler = new WeakHandler();
//                handler.postDelayed(runnable, 3000);
//            }
//
//            @Override
//            protected void onFailed(BaseResponse response) {
//                super.onFailed(response);
//                jump();
//            }
//
//            @Override
//            protected void onError(HttpException e) {
//                super.onError(e);
//                jump();
//            }
//
//            @Override
//            protected void onNetworkDown() {
//                super.onNetworkDown();
//                jump();
//            }
//        };
//        httpRequest.post();
//    }

//    private class LocationListener implements BDLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            if (location.getCity() != null) {
//                City city = new City();
//                city.sid = "";
//                city.latitude = location.getLatitude() + "";
//                city.longitude = location.getLongitude() + "";
//                city.name = location.getCity();
//                if (city.name.contains("市")) {
//                    city.name = city.name.replaceAll("市", "");
//                }
//
//                UserApplication userApplication = getUserApplication();
//
//                city.cityCode = userApplication.findCityCodeByName(city.name);
//                city.opened = userApplication.isCityOpenedByName(city.name);
//
//                userApplication.setChosenCity(city);
//                userApplication.isLocated = true;
//
//                userApplication.setLocationLatitude(location.getLatitude());
//                userApplication.setLocationLongitude(location.getLongitude());
//                userApplication.setLocationCityName(city.name);
//                userApplication.setLocationCityCode(city.cityCode);
//            } else {
//                ToastUtil.showToast(mContext, "定位失败");
//            }
//            mLocationClient.unRegisterLocationListener(this);
//            jump();
//        }
//    }

    private void jump() {
        if (!jumped) {
            jumped = true;
            UserInfo userInfo = new UserInfo(mContext);
            if (userInfo.isForced() || userInfo.isGuide()) {
                userInfo.setGuide(false);
                userInfo.setForced(false);
                jumpToGuide();
            } else {
                jumpToMain();
            }
        }
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            jump();
        }
    };
}
