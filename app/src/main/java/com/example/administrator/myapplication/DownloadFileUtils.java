package com.example.administrator.myapplication;

import android.content.Context;

import com.litesuits.http.HttpConfig;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.request.FileRequest;
import com.litesuits.http.request.param.HttpMethods;

import cn.piaofun.common.PFApplication;

/**
 * Created by Administrator on 2016/9/22.
 */
public class DownloadFileUtils {

    private Context context;
    private static DownloadFileUtils instance;

    private DownloadFileUtils(Context context) {
        this.context = context;
    }

    public static DownloadFileUtils getInstance(Context context) {
        if (instance == null) {
            instance = new DownloadFileUtils(context);
        }
        return instance;
    }


    public boolean openDownloadFile(String url) {
        try {
            HttpConfig config = (new HttpConfig(context))
                    .setDebugged(true)
                    .setDetectNetwork(true)
                    .setDoStatistics(true)
                    .setDefaultHttpMethod(HttpMethods.Get)
                    .setTimeOut(10000, 10000);
            LiteHttp liteHttp = LiteHttp.newApacheHttpClient(config);
            liteHttp.execute(new FileRequest(url, PFApplication.getInstance().SDCARD_PATH + "apk/piaofun.apk"));
            return true;
        } catch (Exception e) {
            return false;
        }


    }
}
