package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.badoo.mobile.util.WeakHandler;
import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.administrator.myapplication.moudle.ImageLoaderUtil;
import com.example.administrator.myapplication.moudle.PFConvenientBanner;
import com.example.administrator.myapplication.moudle.UrlConstant;
import com.example.administrator.myapplication.moudle.UserInfo;
import com.litesuits.http.data.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import cn.piaofun.common.adapter.ListAdapter;
import cn.piaofun.common.base.BaseRefreshFragment;
import cn.piaofun.common.http.HttpRequest;
import cn.piaofun.common.ui.refresh.RefreshHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/9/22.
 */
public class HomepageSingleFragment extends RefreshableListFragment<Show> {

    private String cityCode = "";
    private String searchType;
    private ImageLoaderUtil pfImageLoader;

    /** 记录最后一次停止滑动的时间 */
    private long lastScrollTime;

    /** 广告 banner */
    private PFConvenientBanner advertisementBanner;

    private SearchButtonStatusController searchButtonStatusController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayoutRes(R.layout.fragment_homepage_single);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected List<NameValuePair> insertRequestParameters() {
//        City city = getUserApplication().getChosenCity();
        List<NameValuePair> data = new ArrayList<>();

//        data.add(new NameValuePair("beginDate", CalendarUtil.getCurrentDate()));
//        data.add(new NameValuePair("cityCode", city.getCityCode()));
//        data.add(new NameValuePair("longitude", city.getLongitude()));
//        data.add(new NameValuePair("latitude", city.getLatitude()));

        if (!StringUtil.isNull(searchType)) {
            data.add(new NameValuePair("type", searchType));
        }

        return data;
    }

    @Override
    protected ListAdapter getAdapter() {
        pfImageLoader = new ImageLoaderUtil(this);
        return new ShowAdapter(activity, dataSource, R.layout.item_homepage, pfImageLoader) {
            @Override
            protected void setItem(View convertView, Show data, int i) {
                super.setItem(convertView, data, i);
                //处理点击事件
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, RequestTicketActivity.class);
                        intent.putExtra(IntentKey.KEY_SHOW, (Show) view.getTag(R.layout.item_homepage));
                        startActivity(intent);
                    }
                });
            }
        };
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        emptyIv.setImageResource(R.mipmap.home_page_no_such_type_show_icon);
        emptyTv.setText("当前分类暂无商品");
        emptyBtn.setVisibility(View.GONE);

        setUrl(UrlConstant.URL_HOMEPAGE_LIST);
        adapter.refresh(dataSource);
    }

    @Override
    protected View getHeaderView() {
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.include_banner_advertisement, null);
        advertisementBanner = (PFConvenientBanner) headerView.findViewById(R.id.banner_advertisement);
        return headerView;
    }

    @Override
    public void refresh() {
        super.refresh();
        doGetAdvertisements();
    }

    @Override
    protected Show parseModel(Object item) {
        return JSON.parseObject(item.toString(), Show.class);
    }

    @Override
    protected void setListener() {
        super.setListener();
        //监听列表滑动
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                final long thisTime = System.currentTimeMillis();
                lastScrollTime = thisTime;

                if (scrollState == SCROLL_STATE_IDLE) {
                    new WeakHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //如果是最后一次滑动发出的延时请求
                            if (lastScrollTime == thisTime) {
                                if (searchButtonStatusController != null) {
                                    searchButtonStatusController.setSearchButtonVisible(true);
                                }
                            }
                        }
                    }, 1000);
                } else {
                    if (searchButtonStatusController != null) {
                        searchButtonStatusController.setSearchButtonVisible(false);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != HomepageSingleFragment.this.firstVisibleItem) {
                    HomepageSingleFragment.this.firstVisibleItem = firstVisibleItem;
                    if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                        if (!isLastPage && !isRefreshing()) {
                            loadMore();
                        }
                    }
                }
                HomepageSingleFragment.this.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });

        //自定义是否可以下拉刷新的条件
        setReadyToRefreshListener(new BaseRefreshFragment.OnReadyToRefreshListener() {
            @Override
            public boolean canDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
                return RefreshHandler.checkContentCanBePulledDown(ptrFrameLayout, listView, view1) &&
                        !advertisementBanner.isGrabbed();
            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (getUserApplication().getChosenCity().cityCode != null && (!getUserApplication().getChosenCity().cityCode.equals(cityCode)) || "".equals(cityCode)) {
//            refresh();
//            cityCode = getUserApplication().getChosenCity().cityCode;
//        }
//    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    /**
     * 获取广告 banner
     */
    private void doGetAdvertisements() {
        new HttpRequest(activity, UrlConstant.URL_GET_ADVERTISEMENT) {
            @Override
            protected void onSuccess(String response) {
//                LogUtil.log("advertisement res = " + response);
                BannerResponse result = JSON.parseObject(response, BannerResponse.class);
                addBanner(result.data);
            }
        }.addParameter("cityCode", getUserApplication().getChosenCityCode())
                .post();
    }

    /**
     * 添加广告 banner
     * @param banners
     */
    private void addBanner(List<Banner> banners) {
        if (advertisementBanner != null && banners != null && !banners.isEmpty()) {
            advertisementBanner.setVisibility(View.VISIBLE);
            setAllowErrorViewShow(true);

            advertisementBanner.setPages(
                    new CBViewHolderCreator<LocalImageHolderView>() {
                        @Override
                        public LocalImageHolderView createHolder() {
                            return new LocalImageHolderView();
                        }
                    }, banners);
            if (banners.size() > 1) {
                advertisementBanner
                        .setPageIndicator(new int[]{R.drawable.shape_banner_point_normal, R.drawable.shape_banner_point_selected})
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
                advertisementBanner.startTurning(5000);
            }
        } else {
            advertisementBanner.setVisibility(View.GONE);
            setAllowErrorViewShow(true);
        }
    }

    public class LocalImageHolderView implements CBPageAdapter.Holder<Banner> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, final Banner data) {
            String imageUrl = data.getAdvertisement();
            new ImageLoaderUtil(HomepageSingleFragment.this).display(imageUrl , imageView, ImageLoaderUtil.LoadType.bannerType);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (data.isLotteryType()) {
                        if (new UserInfo(activity).isLogin()) {
                            Intent intent = new Intent(mContext, LotteryActivity.class);
                            intent.putExtra(IntentKey.KEY_COMMON, data);
                            startActivity(intent);
                        } else {
//                            startLoginActivity();
                        }
                    } else if (data.isAuctionType()) {
                        if (new UserInfo(activity).isLogin()) {
                            Intent intent = new Intent(mContext, AuctionActivity.class);
                            intent.putExtra(IntentKey.KEY_COMMON, data);
                            startActivity(intent);
                        } else {
//                            startLoginActivity();
                        }
                    }

                    //yang
                    else if (data.isGroupBuyType()) {
                        if (new UserInfo(activity).isLogin()) {
                            Intent intent = new Intent(mContext, GroupBuyActivity.class);
                            intent.putExtra(IntentKey.KEY_COMMON, data);
                            startActivity(intent);
                        } else {
//                            startLoginActivity();
                        }
                    }

                    else {
                        if (!StringUtil.isNull(data.showSid)) {
                            Show show = new Show();
                            show.sid = data.showSid;
                            show.name = data.showName;

                            Intent intent = null;
                            if (data.isRequestTicketType()) {
                                intent = new Intent(mContext, RequestTicketActivity.class);
                            } else if (data.isPostTicketType()) {
                                intent = new Intent(mContext, DiscoveryRequestTicketActivity.class);
                            } else if (data.isLiveType()) {
                                intent = new Intent(mContext, LiveOrderTicketActivity.class);
                            }

                            if (intent != null) {
                                intent.putExtra(IntentKey.KEY_SHOW, show);
                                startActivity(intent);
                            }
                        }
                    }
                }
            });
        }
    }

    public void setSearchButtonStatusController(SearchButtonStatusController searchButtonStatusController) {
        this.searchButtonStatusController = searchButtonStatusController;
    }

    public interface SearchButtonStatusController {
        void setSearchButtonVisible(boolean visible);
    }

}
