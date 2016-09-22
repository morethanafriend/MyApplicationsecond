package com.example.administrator.myapplication.moudle;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.example.administrator.myapplication.MainActivity;
import com.tendcloud.appcpa.Order;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.piaofun.common.PFApplication;
import cn.piaofun.common.util.ToastUtil;

/**
 * Created by Administrator on 2016/9/22.
 */
public class UserApplication extends PFApplication {

    /** 新版本apk第一次启动时是否进入引导页 */
    public final boolean forceGuide = true;

    /** true 为测试环境； false 为正式环境 */
    public final boolean isDebug = BuildConfig.DEBUG;

    /** 极光推送注册ID */
    public String REGISTER_ID = "";

    /** 5001(session过期)时是否已调用过重启方法 */
    public boolean hasRestart = false;

    /** 待支付订单 */
    public Order payingOrder;

    /** 获取列表数据单页个数 */
    public final int PAGE_SIZE = 20;

    /** 订单所在页码 */
    public final int orderPage = 2;

    /** 未处理的push */
//    public Push push;

    public LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    public String tempcoor = "bd09ll";

    //定位相关
    public LocationClient mLocationClient;
//    public UserLocationListener mMyLocationListener;
//    private City chosenCity;

    private double locationLatitude = 31.230393;
    private double locationLongitude = 121.473704;
    private String locationCityName = "上海";
    private String locationCityCode = null;
    public boolean isLocated = false;

    private int foreGroundCount;
    private int orderDetailForeGround;

    public static UserApplication getInstance() {
        return (UserApplication) mContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请取消日志
        JPushInterface.init(this);// 初始化 JPush
        mLocationClient = new LocationClient(this.getApplicationContext());
        initLocation();
//        setChosenCity(null);//清除上次定位结果
        mLocationClient.start();
    }

//        //city默认值
//        chosenCity = new City();
//        chosenCity.name = "上海";//上海
//        chosenCity.longitude = 121.473704 + "";
//        chosenCity.latitude = 31.230393 + "";
//        chosenCity.sid = "shanghai";
//        chosenCity.cityCode = "shanghai";
//        chosenCity.opened = true;
//        setChosenCity(chosenCity);

//        //TalkingData初始化
//        if (!isDebug) {
//            TalkingDataAppCpa.init(mContext, BuildConfig.TALKING_DATA_AD_TRACKING_ID, channelName);//channelName
//            TCAgent.init(mContext, BuildConfig.TALKING_DATA_ANALYTICS_ID, channelName);
//        }

        //扑捉程序奔溃信息，并且，写入本地文件夹
//        if (isDebug) {
//            ExceptionHandler catchHandler = ExceptionHandler.getInstance();
//            catchHandler.init(getApplicationContext());
//            catchHandler.collectDeviceInfo(getApplicationContext());
//            catchHandler.getSDPath();

            //检测内存泄露的第三方库，需要时打开，同时需反注释 gradle 中的依赖
            //会写出相当大的数据量，不需要时最好关闭
//            LeakCanary.install(this);
//        }
//    }

    /**
     * 注意 Application 本身没有这个生命周期，这是拿来给 Activity 调用的，用来判断应用是否在前台
     */
    public void onResume() {
        foreGroundCount++;
//        //回到前台时检查是否有未处理的推送
//        if (push != null) {
////            onPushReceived(push);
//        }
    }

    /**
     * 注意 Application 本身没有这个生命周期，这是拿来给 Activity 调用的，用来判断应用是否在前台
     */
    public void onPause() {
        foreGroundCount--;
    }

    /**
     * 用来判断订单详情是否在前台
     */
    public void onOrderDetailResume() {
        orderDetailForeGround++;
    }

    /**
     * 用来判断订单详情是否在前台
     */
    public void onOrderDetailPause() {
        orderDetailForeGround--;
    }
//
//    /**
//     * 接收推送
//     * @param push
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onPushReceived(Push push) {
//        //应用在前台则弹出dialog的Activity
//        if (isApplicationForeground()) {
//            SharedPreferences mShare = this.getSharedPreferences("piaofun.share", Context.MODE_PRIVATE);
//            boolean showDialog = mShare.getBoolean("showDialog", false);
//
//            if (showDialog) {
//                SharedPreferences.Editor mEditor = mShare.edit();
//                mEditor.putBoolean("showDialog", false).commit();
//
//                Intent i = new Intent(this, PushDialogActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
//                i.putExtra("isOrderDetailForeground", isOrderDetailForeGround());
//                i.putExtra("push", push);
//                startActivity(i);
//            } else {
//            }
//            this.push = null;
//        } else {
//            this.push = push;
//        }
//    }

    @Override
    public void onTerminate() {
        EventBus.getDefault().unregister(this);
        super.onTerminate();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType(tempcoor);//可选，默认gcj02，设置返回的定位结果坐标系，
        option.setScanSpan(60 * 1000);
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
//
//        mMyLocationListener = new UserLocationListener();
//        mLocationClient.registerLocationListener(mMyLocationListener);
    }

    public void reLocation() {
        if (mLocationClient != null) {
            isLocated = false;
            mLocationClient.stop();
            initLocation();
            mLocationClient.start();
        }
    }

    /**
     * 实现实时位置回调监听
     */
//    public class UserLocationListener implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            if (location.getCity() != null) {
//                if (!isLocated) {
//                    LogUtil.log("insert new city to chosen");
//                    City city = new City();
//                    city.sid = "";
//                    city.latitude = location.getLatitude() + "";
//                    city.longitude = location.getLongitude() + "";
//                    city.name = location.getCity();
//                    if (city.name.contains("市")) {
//                        city.name = city.name.replaceAll("市", "");
//                    }
//                    city.cityCode = findCityCodeByName(city.name);
//                    setChosenCity(city);
//                    isLocated = true;
//                }
//            }
//            setCurrentLocation(location);
//        }
//    }

//    public void setCurrentLocation(BDLocation location) {
//        if (location.getCity() != null) {
//            locationLatitude = location.getLatitude();
//            locationLongitude = location.getLongitude();
//            locationCityName = location.getCity();
//            if (locationCityName.contains("市")) {
//                locationCityName = locationCityName.replaceAll("市", "");
//            }
//            locationCityCode = findCityCodeByName(locationCityName);
//        } else {
//            mLocationClient.requestLocation();
//        }
//    }

//    public String findCityCodeByName(String cityName) {
//        for (City city : this.getCityList()) {
//            if (cityName.equals(city.name)) {
//                return city.cityCode;
//            }
//        }
//        return Pinyin4jUtil.converterToSpell(cityName);
//    }

//    public boolean isCityOpenedByName(String cityName) {
//        for (City city : this.getCityList()) {
//            if (cityName.equals(city.name)) {
//                return city.opened;
//            }
//        }
//        return false;
//    }
//
//    public String getChannelName() {
//        return channelName;
//    }
//
////    @Override
////    public String getChosenCityCode() {
////        return chosenCity.cityCode;
////    }

    @Override
    public void onInvalid() {
        if (!hasRestart) {
            hasRestart = true;
            UserInfo userInfo = new UserInfo(mContext);
            //根据userName判断是否已登录过，以此判断是登录失效 还是正常未登录的情况
            if (!userInfo.getUserName().isEmpty()) {
                ToastUtil.showToast(mContext, "登录失效，请重新登录");
            }
            userInfo.clear();
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public void setPayingOrder(Order payingOrder) {
        this.payingOrder = payingOrder;
    }

    public Order getPayingOrder() {
        return payingOrder;
    }

//    public void saveCity(List<City> cities) {
//        this.saveObject("sharedCity", cities);
//    }
//
//    public List<City> getCityList() {
//        return loadObject("sharedCity", City.class);
//    }

    public void putLong(String key, Long value) {
        getSharePreferences().edit().putLong(key, value).commit();
    }

    public Long getLong(String key) {
        return this.getSharePreferences().getLong(key, 0L);
    }

//    public void saveBanner(List<Banner> banners) {
//        this.saveObject("sharedBanner", banners);
//        this.putLong("sharedBannerTime", System.currentTimeMillis());
//    }

    public long getBannerSaveTime() {
        return this.getLong("sharedBannerTime");
    }

//    public List<Banner> getBanners() {
//        return loadObject("sharedBanner", Banner.class);
//    }
//
//    public void saveCommentTags(List<CommentTag> commentTags) {
//        this.saveObject("sharedTag", commentTags);
//        this.putLong("sharedTagTime", System.currentTimeMillis());
//    }
//
//    public List<CommentTag> getCommentTags() {
//        return loadObject("sharedTag", CommentTag.class);
//    }
//
//    public long getCommentTagSaveTime() {
//        return this.getLong("sharedTagTime");
//    }
//
//    public void setChosenCity(City city) {
//        getSharePreferences()
//                .edit()
//                .putString("chosenCity", city == null ? "{}" : JSON.toJSONString(city))
//                .commit();
//    }
//
//    public City getChosenCity() {
//        chosenCity = JSON.parseObject(getSharePreferences().getString("chosenCity", "{}"), City.class);
//        return chosenCity;
//    }

//    public String getIMEI() {
//        String imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
//        return StringUtil.isNull(imei) ? "" : imei;
//    }

//    public boolean isGpsEnable() {
//        LocationManager locationManager =
//                ((LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE));
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getLocationCityName() {
        return locationCityName;
    }

    public void setLocationCityName(String locationCityName) {
        this.locationCityName = locationCityName;
    }

    public String getLocationCityCode() {
        return locationCityCode;
    }

    public void setLocationCityCode(String locationCityCode) {
        this.locationCityCode = locationCityCode;
    }

    public boolean isApplicationForeground() {
        return foreGroundCount > 0;
    }

    public boolean isOrderDetailForeGround() {
        return orderDetailForeGround > 0;
    }

    /**
     * 原本是用来判断 App 是否在前台的方法
     * 但经测试表现不稳定，且官方在高版本 Android 已经不支持这样做
     * 详情见 AndroidManifest 中 <uses-permission android:name="android.permission.GET_TASKS" /> 权限
     * @return
     */
    private String getTopPackageName() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(Integer.MAX_VALUE);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            return cpn.getPackageName();
        }

        return null;
    }
}