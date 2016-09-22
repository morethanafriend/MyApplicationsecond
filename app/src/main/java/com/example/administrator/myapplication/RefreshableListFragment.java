package com.example.administrator.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.myapplication.moudle.UserApplication;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpException;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cn.piaofun.common.adapter.ListAdapter;
import cn.piaofun.common.base.BaseRefreshFragment;
import cn.piaofun.common.http.BaseResponse;
import cn.piaofun.common.http.HttpRequest;
import cn.piaofun.common.ui.dialog.LoadingDialog;

/**
 * Created by zhangll on 16/3/22.
 * 包含一个 ListView 的可下拉刷新、可分页加载的 fragment
 *
 */
public abstract class RefreshableListFragment<T> extends BaseRefreshFragment {

    /** 是否不分页（默认是可以分页的，所以为false） */
    protected boolean isNotPageable = false;
    /** 记录的第一个可见item的position */
    protected int firstVisibleItem = 0;

    protected View rootView;

    private String url;

    protected List<T> dataSource;
    protected ListView listView;
    protected ListAdapter<T> adapter;

    protected View dataSizeZeroView;
    protected View frozenView;
    protected View serverErrorView;
    protected View noWifiView;
    protected View noLoginView;
    protected Button loginBtn;

    protected Button emptyBtn;
    protected ImageView emptyIv;
    protected TextView emptyTv;

    protected boolean isLastPage;
    protected boolean isLocked;
    protected int currentPage = 0;

    protected boolean allowErrorViewShow = true;

    protected LoadingDialog loadingDialog;

    @Override
    protected View getRefreshView() {
        if (rootView == null) {
            rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_refresh, null);
        }
        return rootView;
    }

    @Override
    public void refresh() {
        onListRefresh();
        isLastPage = false;
        doGetDataByPage(0, getUserApplication().PAGE_SIZE);
    }

    /**
     * 刷新当前所有数据
     */
    public void refreshAllList() {
        doGetDataByPage(0, (currentPage + 1) * getUserApplication().PAGE_SIZE);
    }

    protected void onListRefresh() {
        //子类可选择实现
    }

    protected void loadMore() {
        doGetDataByPage(currentPage, getUserApplication().PAGE_SIZE);
    }

    /**
     * 带有 loading 的刷新
     */
    public void refreshWithLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(activity);
        }
        loadingDialog.show();

        listView.setSelection(0);

        refresh();
    }

    @Override
    protected void initView() {
        dataSizeZeroView = rootView.findViewById(R.id.layout_no_data);
        frozenView = rootView.findViewById(R.id.layout_frozen);
        emptyBtn = (Button) rootView.findViewById(R.id.btn_empty);
        serverErrorView = rootView.findViewById(R.id.layout_server_error);
        noWifiView = rootView.findViewById(R.id.layout_no_net_wifi);
        emptyIv = (ImageView) rootView.findViewById(R.id.iv_empty);
        emptyTv = (TextView) rootView.findViewById(R.id.tv_empty);
        noLoginView = rootView.findViewById(R.id.layout_no_login);
        loginBtn = (Button) rootView.findViewById(R.id.btn_login);

        dataSource = new ArrayList<>();
        adapter = getAdapter();

        listView = findViewById(R.id.list_view);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        View headerView = getHeaderView();
        if (headerView != null) {
            listView.addHeaderView(headerView);
        }


        setScrollableView(listView);
        afterInit();
    }

    @Override
    protected void setListener() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != RefreshableListFragment.this.firstVisibleItem) {
                    RefreshableListFragment.this.firstVisibleItem = firstVisibleItem;
                    if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                        if (!isLastPage && !isRefreshing()) {
                            loadMore();
                        }
                    }
                }

                RefreshableListFragment.this.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
    }

    protected void setUrl(String url) {
        this.url = url;//yang
    }

    /**
     * 根据指定的 URL 以及分页参数获取数据
     * @param page
     * @param pageSize
     */
    protected void doGetDataByPage(final int page, int pageSize) {
        if (isLocked) {
            resetState();
        } else {
            isLocked = true;
            insertRequestParameters();
            final HttpRequest request = new HttpRequest(activity, url) {
                @Override
                protected void onSuccess(String res) {
                    Log.d("yang", res);
                    resetState(true);
                    List<T> showDatas;
                    List<T> hotShowDatas = null;
                    if (url.endsWith("1")) {
                        CommonRefreshResponseMuti<T> one = JSON.parseObject(res, CommonRefreshResponseMuti.class);
                        showDatas = one.data.shows;
                        //Show show_yang1 = (Show) showDatas.get(0);
                        // Log.d("yang1", show_yang1.sid + "--" + show_yang1.isSoldOut());
                        hotShowDatas = one.data.hotShows;
                        // if(hotShowDatas.size()> 3){
                        // Show show_yang3 = (Show) hotShowDatas.get(2);
                        // Log.d("yang3", show_yang3.sid + "--" + show_yang3.isSoldOut());}

                        toRefreshHotBlock(hotShowDatas, showDatas);
                    } else{
                        CommonRefreshResponse<T> result = JSON.parseObject(res, CommonRefreshResponse.class);
                        showDatas = result.data;
                    }

                    for (int i = 0; i < showDatas.size(); i++) {
                        Object object = showDatas.get(i);
                        if (object instanceof JSONObject) {
                            object = parseModel(object);
                            showDatas.remove(i);
                            showDatas.add(i, (T) object);
                        }
                    }

                    if (page == 0) {
                        dataSource.clear();
                    }
                    dataSource.addAll(showDatas);

                    if (adapter == null) {
                        adapter = getAdapter();
                    }
                    adapter.refresh(dataSource);

                    if (showDatas.size() >= getUserApplication().PAGE_SIZE) {
                        dataSizeZeroView.setVisibility(View.GONE);
                        currentPage = page == 0 ? 1 : currentPage + 1;
                        isLastPage = false;
                    } else {
                        currentPage = 1;
                        isLastPage = true;
                    }

                    //yang将热门数据添加进来，判断是否数据为空
                    //if (hotShowDatas != null) {
                    //    dataSource.addAll(hotShowDatas);
                    //}

                    //空列表等处理
                    if (dataSource.isEmpty() && allowErrorViewShow) {
                        serverErrorView.setVisibility(View.VISIBLE);
                        noWifiView.setVisibility(View.VISIBLE);
                        frozenView.setVisibility(View.VISIBLE);
                        dataSizeZeroView.setVisibility(View.GONE);
                    } else {
                        serverErrorView.setVisibility(View.GONE);
                        noWifiView.setVisibility(View.GONE);
                        dataSizeZeroView.setVisibility(View.GONE);
                        frozenView.setVisibility(View.GONE);
                    }

                }

                @Override
                protected void onFailed(BaseResponse response) {
                    resetState(false);
                    if (dataSource.isEmpty() && allowErrorViewShow) {
                        serverErrorView.setVisibility(View.VISIBLE);
                        noWifiView.setVisibility(View.VISIBLE);
                        dataSizeZeroView.setVisibility(View.VISIBLE);
                        frozenView.setVisibility(View.VISIBLE);
                    } else {
                        serverErrorView.setVisibility(View.VISIBLE);
                        noWifiView.setVisibility(View.VISIBLE);
                        dataSizeZeroView.setVisibility(View.VISIBLE);
                        frozenView.setVisibility(View.VISIBLE);
                    }
                    super.onFailed(response);
                }

                @Override
                protected void onError(HttpException e) {
                    resetState(false);
                    if (dataSource.isEmpty() && allowErrorViewShow) {
                        serverErrorView.setVisibility(View.VISIBLE);
                        noWifiView.setVisibility(View.VISIBLE);
                        dataSizeZeroView.setVisibility(View.VISIBLE);
                        frozenView.setVisibility(View.VISIBLE);
                    }
                    super.onError(e);
                }

                @Override
                protected void onNetworkDown() {
                    resetState(false);
                    if (dataSource.isEmpty() && allowErrorViewShow) {
                        serverErrorView.setVisibility(View.VISIBLE);
                        noWifiView.setVisibility(View.VISIBLE);
                        dataSizeZeroView.setVisibility(View.VISIBLE);
                        frozenView.setVisibility(View.VISIBLE);
                    } else {
                        serverErrorView.setVisibility(View.VISIBLE);
                        noWifiView.setVisibility(View.VISIBLE);
                        dataSizeZeroView.setVisibility(View.VISIBLE);
                        frozenView.setVisibility(View.VISIBLE);
                    }
                    super.onNetworkDown();
                }

                @Override
                protected void onFrozen(BaseResponse result) {
                    resetState(false);
                    if (dataSource.isEmpty() && allowErrorViewShow) {
                        serverErrorView.setVisibility(View.VISIBLE);
                        noWifiView.setVisibility(View.VISIBLE);
                        dataSizeZeroView.setVisibility(View.VISIBLE);
                        frozenView.setVisibility(View.VISIBLE);

                        ((TextView) findViewById(R.id.tv_frozen)).setText(result.message);
                    }
                }

                @Override
                protected void onInvalid() {
                    resetState();
                    super.onInvalid();
                }
            }.addParameter(insertRequestParameters());

            if (!isNotPageable) {
                request.addParameter("start", page * getUserApplication().PAGE_SIZE + "")
                        .addParameter("pageSize", pageSize + "");
            }
            //Log.d("yang", request.toString());
            request.post();
        }
    }

    /**
     * 套票专区界面实现添加热门专区
     * @param hotShowDatas
     */
    protected void toRefreshHotBlock(List<T> hotShowDatas,List<T> showDatas){}

    protected void resetState() {
        isLocked = false;
        dismissLoading();
        notifyRefreshFinished();
    }

    protected void resetState(boolean success) {
        isLocked = false;
        dismissLoading();
        notifyRefreshFinished();
    }

    protected void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public UserApplication getUserApplication() {
        return (UserApplication) super.getPFApplication();
    }

//    public UserBaseActivity getUserBaseActivity() {
//        return (UserBaseActivity) activity;
//    }

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

    protected void setAllowErrorViewShow(boolean allowErrorViewShow) {
        this.allowErrorViewShow = allowErrorViewShow;
        checkErrorViews();
    }

    protected void checkErrorViews() {
        if (!allowErrorViewShow) {
            serverErrorView.setVisibility(View.VISIBLE);
            noWifiView.setVisibility(View.VISIBLE);
            dataSizeZeroView.setVisibility(View.VISIBLE);
            frozenView.setVisibility(View.VISIBLE);
        }
    }

//    public void startLoginActivity() {
//        startActivity(new Intent(activity, LoginActivity.class));
//        activity.overridePendingTransition(R.anim.bottom_in, R.anim.stand);
//    }

    /**
     * 由子类扩展滑动的监听
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    protected void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    protected void afterInit() {
        listView.setAdapter(adapter);
    }

    /**
     * 从子类得到 header
     * @return
     */
    protected abstract View getHeaderView();

    /**
     * 从子类得到 adapter
     * @return
     */
    protected abstract ListAdapter getAdapter();

    /**
     * 从子类得到请求接口的额外参数
     * @return
     */
    protected abstract List<NameValuePair> insertRequestParameters();

    /**
     * 由子类解析泛型对象
     * @param item
     * @return
     */
    protected abstract T parseModel(Object item);
}