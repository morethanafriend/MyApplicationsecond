package com.example.administrator.myapplication;


import java.util.List;

import cn.piaofun.common.http.BaseResponse;

/**
 * Created by zhangll on 15/11/20.
 * RefreshableListFragment 所用的接口返回模型
 */
public class CommonRefreshResponse<T> extends BaseResponse {
    public List<T> data;
}