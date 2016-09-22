package com.example.administrator.myapplication.moudle;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/9/22.
 */
public class UserInfo {
    /** 手机号 */
    private String mobile;
    /** 登录用户名 */
    private String userName;
    /** 登录用户ID */
    private String userId;
    /** 推送用别名 */
    private String alias;
    /** 是否已绑定推送 */
    private boolean bound;
    /** 头像 */
    private String avatar;
    /** 服务端返回的密码 */
    private String serverPassword;
    /** session */
    private String sessionId;
    /** 是否进入引导页 */
    private boolean isGuide;
    /** 当前版本是否强制进入引导页（第一次登录） */
    private boolean isForced;

    /** 是否进入过演出详情 */
    private boolean hasEnterShowDetail;

    /** 是否进入过现场列表 */
    private boolean hasEnterLive;

    /** 是否进入过待支付的约票订单详情 */
    private boolean hasEnterAppointOrderDetail;

    /** 是否登录成功 */
    private boolean isLogin;

    /** 最近刷新时间 */
    private String latestRefreshTime;

    /** 星级 */
    private float stars;

    private String version;

    private boolean isAutoLogin;

    private boolean isExit;
    private boolean isPush;

    private SharedPreferences mShare;
    private SharedPreferences.Editor mEditor;

    public UserInfo(Context context) {
        mShare = context.getSharedPreferences("piaofun.share", Context.MODE_PRIVATE);
        mEditor = mShare.edit();
        init();
    }

//    public UserInfo(Context context, LoginResponse info) {
//        mShare = context.getSharedPreferences("piaofun.share", Context.MODE_PRIVATE);
//        mEditor = mShare.edit();
//        init();
////        setInfo(info);
//        setAutoLogin(true);
//        setIsLogin(true);
//        setExit(false);
//        UserApplication.getInstance().hasRestart = false;
//    }

    /** 根据服务端返回info修改数据 */
//    private void setInfo(LoginResponse info) {
//        setUserId(info.data.userSid);
//        setUserName(info.data.userNickname);
//        setMobile(info.data.userCellphone);
//        setAvatar(info.data.userAvatarImageUrl);
//        setBound(info.data.bound);
//        setAlias(info.data.alias);
//    }

    public void clear() {
        setUserId("");
        setUserName("");
        setAvatar("");
        setIsLogin(false);
        setServerPassword("");
        setSessionId("");
    }

    /**
     * @author 张璐琳
     * @version 1.0
     * @Description: 得到当前已存的数据
     * @return void
     * @创建时间：2015-5-27 上午10:40:27
     */
    private void init() {
        userId = mShare.getString("userId", "");
        userName = mShare.getString("userName", "");
        avatar = mShare.getString("avatar", "");
        sessionId = mShare.getString("session", "");
        mobile = mShare.getString("mobile", "");
        isGuide = mShare.getBoolean("isGuide", true);
        isPush = mShare.getBoolean("isPush", false);
        isLogin = mShare.getBoolean("isLogin", false);
        serverPassword = mShare.getString("serverPassword", "");
        latestRefreshTime = mShare.getString("latestRefreshTime", "");
        stars = mShare.getFloat("stars", 0);
        isForced = mShare.getBoolean("isForced", false);
        version = mShare.getString("version", "");
        hasEnterShowDetail = mShare.getBoolean("hasEnterShowDetail", false);
        hasEnterLive = mShare.getBoolean("hasEnterLive", false);
        hasEnterAppointOrderDetail = mShare.getBoolean("hasEnterAppointOrderDetail", false);
        alias = mShare.getString("alias", "");
        bound = mShare.getBoolean("bound", false);
    }

    private void save(String key, Object value) {
        if (value instanceof String) {
            mEditor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            mEditor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            mEditor.putLong(key, (Long) value);
        } else if (value instanceof Float || value instanceof Double) {
            mEditor.putFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) value);
        }
        mEditor.commit();
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
        save("userId", userId);
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
        save("mobile", mobile);
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
        save("userName", userName);
    }

    public boolean isAutoLogin() {
        return isAutoLogin;
    }

    public void setAutoLogin(boolean isAutoLogin) {
        this.isAutoLogin = isAutoLogin;
        save("isAutoLogin", isAutoLogin);
    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean isExit) {
        this.isExit = isExit;
        save("isExit", isExit);
    }

    public void setGuide(boolean isGuide) {
        this.isGuide = isGuide;
        save("isGuide", isGuide);
    }

    public boolean isGuide() {
        return isGuide;
    }

    public void setForced(boolean isForced) {
        this.isForced = isForced;
        save("isForced", isForced);
    }

    public boolean isForced() {
        return isForced;
    }

    public void setVersion(String version) {
        this.version = version;
        save("version", version);
    }

    public String getVersion() {
        return version;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        save("session", sessionId);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        save("avatar", avatar);
    }

    public boolean isPush() {
        return isPush;
    }

    public void setPush(boolean isPush) {
        this.isPush = isPush;
        save("isPush", isPush);
    }


    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
        save("isLogin", isLogin);
    }

    public String getServerPassword() {
        return serverPassword;
    }

    public void setServerPassword(String serverPassword) {
        this.serverPassword = serverPassword;
        save("serverPassword", "serverPassword");
    }

    public String getLatestRefreshTime() {
        return latestRefreshTime;
    }

    public void setLatestRefreshTime(String latestRefreshTime) {
        this.latestRefreshTime = latestRefreshTime;
        save("latestRefreshTime", latestRefreshTime);
    }

    public double getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
        save("stars", stars);
    }

    public boolean hasEnterShowDetail() {
        return hasEnterShowDetail;
    }

    public void setHasEnterShowDetail(boolean hasEnterShowDetail) {
        this.hasEnterShowDetail = hasEnterShowDetail;
        save("hasEnterShowDetail", hasEnterShowDetail);
    }

    public boolean hasEnterLive() {
        return hasEnterLive;
    }

    public void setHasEnterLive(boolean hasEnterLive) {
        this.hasEnterLive = hasEnterLive;
        save("hasEnterLive", hasEnterLive);
    }

    public boolean hasEnterAppointOrderDetail() {
        return hasEnterAppointOrderDetail;
    }

    public void setHasEnterAppointOrderDetail(boolean hasEnterAppointOrderDetail) {
        this.hasEnterAppointOrderDetail = hasEnterAppointOrderDetail;
        save("hasEnterAppointOrderDetail", hasEnterAppointOrderDetail);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
        save("alias", alias);
    }

    public boolean isBound() {
        return bound;
    }

    public void setBound(boolean bound) {
        this.bound = bound;
        save("bound", bound);
    }
}