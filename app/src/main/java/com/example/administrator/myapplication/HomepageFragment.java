package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.piaofun.common.adapter.PagerFragmentAdapter;
import cn.piaofun.common.util.DisplayUtil;

/**
 * Created by Administrator on 2016/9/22.
 */
public class HomepageFragment extends UserBaseFragment implements HomepageSingleFragment.SearchButtonStatusController {

    public static final int REQUEST_SHOW_DETAIL = 100;
    public static final int REQUEST_LOCATION = 101;
    public static final int SKIP_TO_MAP = 102;

//    private City chosenCity;

    private TextView cityTv;
    /**
     * 滑动标签栏
     */
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;

    private CallBacks listener;

    private Button searchBtn;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (CallBacks) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_homepage, null);
//        chosenCity = getUserApplication().getChosenCity();
        initView();
        setListener();
        return mView;
    }

    @Override
    protected void initView() {
        cityTv = findViewById(R.id.tv_city);
        searchBtn = findViewById(R.id.btn_search);
//        refreshCity();

//        mPagerSlidingTabStrip = findViewById(R.id.tabs);
        mViewPager = findViewById(R.id.vp_homepage);
        setPagerAdapter();

//        mPagerSlidingTabStrip.setViewPager(mViewPager);
//        initTabsValue();
    }

    /**
     * mPagerSlidingTabStrip默认值配置
     */
//    private void initTabsValue() {
//        // 底部游标颜色
//        mPagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.theme_color));
//        // 游标高度
//        mPagerSlidingTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                2, getResources().getDisplayMetrics()));
//        // tab的分割线颜色
//        mPagerSlidingTabStrip.setDividerColor(Color.TRANSPARENT);
//        // tab背景
//        mPagerSlidingTabStrip.setBackgroundColor(Color.WHITE);
//        // tab底线高度
//        mPagerSlidingTabStrip.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                0.5f, getResources().getDisplayMetrics()));
//        // tab底线颜色
//        mPagerSlidingTabStrip.setUnderlineColor(Color.parseColor("#efefef"));
//        // 选中的文字颜色
//        mPagerSlidingTabStrip.setSelectedTextColor(getResources().getColor(R.color.theme_color));
//        // 正常文字颜色
//        mPagerSlidingTabStrip.setTextColor(Color.parseColor("#909090"));
//        // 字体大小
//        mPagerSlidingTabStrip.setTextSize(DisplayUtil.sp2px(mContext, 14));
//    }

    @Override
    protected void setListener() {
//        findViewById(R.id.tv_live).setOnClickListener(this);
        findViewById(R.id.layout_city).setOnClickListener(this);
        findViewById(R.id.layout_title_right_search).setOnClickListener(this);
        searchBtn.setOnClickListener(this);
    }

    private void setPagerAdapter() {
        List<Fragment> fragments = new ArrayList<>();
        for (SearchType searchType : SearchType.values()) {
            HomepageSingleFragment homepageSingleFragment = new HomepageSingleFragment();
            homepageSingleFragment.setSearchType(searchType.getName());
            homepageSingleFragment.setSearchButtonStatusController(this);
            fragments.add(homepageSingleFragment);
        }
        mViewPager.setAdapter(new HomePagerAdapter(((FragmentActivity) activity).getSupportFragmentManager(), fragments));
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.layout_title_right_search:
//                Intent intent = new Intent(mContext, SearchActivity.class);
//                startActivity(intent);
//                activity.overridePendingTransition(R.anim.stand, R.anim.stand);
//                break;
//            case R.id.layout_city:
//                startActivityForResult(new Intent(mContext, LocationActivity.class), REQUEST_LOCATION);
//                activity.overridePendingTransition(R.anim.bottom_in, R.anim.stand);
//                break;
//        }
//        super.onClick(view);
//    }

//    private void refreshCity() {
//        if (!cityTv.getText().toString().equals(chosenCity.name)) {
//            getUserApplication().setChosenCity(chosenCity);
//            cityTv.setText(chosenCity.name);
//        }
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//                case REQUEST_SHOW_DETAIL:
//                    listener.onAppointmentSuccess();
//                    break;
//                case REQUEST_LOCATION:
//                    chosenCity = (City) data.getSerializableExtra(IntentKey.KEY_CITY);
//                    refreshCity();
//                    break;
//                case SKIP_TO_MAP:
//                    chosenCity = (City) data.getSerializableExtra(IntentKey.KEY_CITY);
//                    refreshCity();
//                    break;
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    /**
     * 因现场改版，搜索按钮已移动至标题栏右侧
     * 右下角的搜索按钮目前是隐藏了，但暂时不排除改回来的可能性
     *
     * @param visible
     */
    @Override
    public void setSearchButtonVisible(boolean visible) {
//        searchBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
        searchBtn.setVisibility(View.GONE);
    }

    private class HomePagerAdapter extends PagerFragmentAdapter {

        private List<String> titles = new ArrayList<>();

        public HomePagerAdapter(FragmentManager fragmentManager, List<Fragment> list) {
            super(fragmentManager, list);
            for (SearchType searchType : SearchType.values()) {
                titles.add(searchType.getValue());
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    /**
     * 分类
     */
    private enum SearchType {
        liveshow("演唱会"),
        drama("舞台剧"),
        music("音乐会"),
        holiday("展览活动"),
        sports("体育赛事"),
        chinaplay("舞蹈曲艺"),
        children("儿童亲子"),
        other("其它"),
        /*liveshow("演唱会"),
        drama("话z剧"),
        music("音乐会"),
        dance("舞蹈"),
        chinaplay("曲艺"),
        sports("赛事"),
        holiday("休闲"),
        children("亲子"),
        other("其它"),*/;

        private String type;

        SearchType(String type) {
            this.type = type;
        }

        public String getName() {
            return toString();
        }

        public String getValue() {
            return type;
        }
    }

    public interface CallBacks {
        void onAppointmentSuccess();
    }
}
