package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.administrator.myapplication.moudle.UrlConstant;
import com.example.administrator.myapplication.moudle.UserApplication;
import com.litesuits.http.exception.HttpException;

import java.io.File;

import cn.piaofun.common.PFApplication;
import cn.piaofun.common.http.BaseResponse;
import cn.piaofun.common.http.HttpRequest;
import cn.piaofun.common.ui.dialog.LoadingDialog;
import cn.piaofun.common.util.ToastUtil;

/**
 * Created by Administrator on 2016/9/22.
 */
public class CheckVersionUtils {
    private Context mContext;
    private MyHandler myHandler;
    private LoadingDialog loadingDialog;
    private CheckVersionListener listener;
    //下载apk时，提示的进度条
    private UpdateApkDialog updateApkDialog;

    public CheckVersionUtils(Context context) {
        mContext = context;
        myHandler = new MyHandler();
    }

    private boolean isMain = false;

    /**
     * 检查程序版本
     */
    public void checkVersion(final boolean isMain) {
        this.isMain = isMain;

        new HttpRequest(mContext, UrlConstant.UPDATE_APK_URL) {
            @Override
            protected void onSuccess(String res) {
                //TODO 判断是否有新版本
                try {
                    Log.d("yang", "--" + res + "--");
                    UpdateModel updateModel = JSON.parseObject(res, UpdateModel.class);
                    if ("2".equals(updateModel.data.updateCode)) {//强制更新
                        //强制更新回调
                        if (listener != null) {
                            listener.forcedUpdate();
                        }
                        updateApkDialog = new UpdateApkDialog(mContext, "提示", updateModel.data.appClient.clientDescription, "立即更新", "退出", View.VISIBLE);
                        updateApkDialog.setCanceledOnTouchOutside(false);
                        updateApkDialog.setCustomListener(new UpdateOnCustomListener(1, updateModel.data.appClient.updateUrl));
                        updateApkDialog.setCancelable(false);
                        updateApkDialog.show();
                    } else if ("1".equals(updateModel.data.updateCode) && !isMain) {
                        updateApkDialog = new UpdateApkDialog(mContext, "提示", updateModel.data.appClient.clientDescription, "更新", "", View.VISIBLE);
                        updateApkDialog.setCanceledOnTouchOutside(true);
                        updateApkDialog.setCustomListener(new UpdateOnCustomListener(0, updateModel.data.appClient.updateUrl));
                        updateApkDialog.show();
                    } else {
                        //可更新，可不更新
                        if (listener != null) {
                            listener.freewillUpdate();
                        }
                        if (!isMain) {
                            ToastUtil.showToast(mContext.getApplicationContext(), updateModel.message);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onFailed(BaseResponse response) {
                super.onFailed(response);
                if (listener != null) {
                    listener.updateError();
                }
            }

            @Override
            protected void onError(HttpException e) {
                super.onError(e);
                if (listener != null) {
                    listener.updateError();
                }
            }

            @Override
            protected void onNetworkDown() {
                super.onNetworkDown();
                if (listener != null) {
                    listener.updateError();
                }
            }
        }.post();
    }


    private class UpdateOnCustomListener implements UpdateApkDialog.OnCustomListener {
        private final int type;
        private String updateUrl;

        private UpdateOnCustomListener(int type, String updateUrl) {
            this.updateUrl = updateUrl;
            this.type = type;
        }


        @Override
        public void rightUpdate() {
            if (updateApkDialog != null) {
                updateApkDialog.dismiss();
            }
//            下载apk文件
            loadingDialog = new LoadingDialog(mContext);
            loadingDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean tag = DownloadFileUtils.getInstance(mContext).openDownloadFile(updateUrl);
                    // 下载成功
                    if (tag) {
                        Message msg = myHandler.obtainMessage();
                        msg.what = 0;
                        myHandler.sendMessage(msg);
                        //创建URI
                        Uri uri = Uri.fromFile(new File(PFApplication.getInstance().SDCARD_PATH + "apk/piaofun.apk"));
                        //创建Intent意图
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        //设置Uri和类型
                        intent.setDataAndType(uri, "application/vnd.android.package-archive");
                        //执行意图进行安装
                        mContext.startActivity(intent);
                    } else {
                        Message msg = myHandler.obtainMessage();
                        msg.what = 1;
                        myHandler.sendMessage(msg);
                    }
                }
            }) {
            }.start();
        }

        @Override
        public void cancel() {
            if (updateApkDialog != null && !isMain) {
                updateApkDialog.dismiss();
            } else if (updateApkDialog != null && isMain) {
                System.exit(0);
            }
        }
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    if (updateApkDialog != null) {
                        updateApkDialog.dismiss();
                    }
                    if (loadingDialog != null) {
                        loadingDialog.dismiss();
                    }
                    mContext = null;
                    break;
                case 1:
                    ToastUtil.showToast(mContext, "下载失败");
                    if (updateApkDialog != null) {
                        updateApkDialog.dismiss();
                    }
                    if (loadingDialog != null) {
                        loadingDialog.dismiss();
                    }
                    mContext = null;
                    UserApplication.getInstance().onInvalid();
                    break;
            }
            super.handleMessage(msg);

        }
    }


    /**
     * 设置更新回调
     */
    public void setCheckVersionListener(CheckVersionListener listener) {
        this.listener = listener;
    }

    /**
     * 版本更新回调
     */
    public interface CheckVersionListener {
        /**
         * 强制更新
         */
        void forcedUpdate();

        /**
         * 可更新，可不更新
         */
        void freewillUpdate();

        /**
         * 更新失败
         */
        void updateError();

    }
}