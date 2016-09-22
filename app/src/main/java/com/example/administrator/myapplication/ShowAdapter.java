package com.example.administrator.myapplication;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.moudle.ImageLoaderUtil;

import java.util.List;

import cn.piaofun.common.adapter.ListAdapter;
import cn.piaofun.common.util.ViewHolder;


/**
 * Created by Administrator on 2016/9/22.
 */
public class ShowAdapter extends ListAdapter<Show> {

    private ImageLoaderUtil pfImageLoader;

    public ShowAdapter(Activity activity, List<Show> data, int layoutRes, ImageLoaderUtil pfImageLoader) {
        super(activity, data, layoutRes);
        this.pfImageLoader = pfImageLoader;
    }

    @Override
    protected void setItem(View convertView, Show data, int i) {
        ImageView coverImage = ViewHolder.get(convertView, R.id.iv_cover);
        TextView titleTv = ViewHolder.get(convertView, R.id.tv_event_title);
        TextView timeTv = ViewHolder.get(convertView, R.id.tv_event_time);
        TextView placeTv = ViewHolder.get(convertView, R.id.tv_event_place);
        TextView priceTv = ViewHolder.get(convertView, R.id.tv_start_price);
        TextView priceTailTv = ViewHolder.get(convertView, R.id.tv_start_price_tail);

        //yang
        ImageView hotView = ViewHolder.get(convertView, R.id.iv_hot);
        ImageView liveView = ViewHolder.get(convertView, R.id.iv_live);
        ImageView discountView = ViewHolder.get(convertView, R.id.iv_discount);
        hotView.setVisibility(data.isHot ? View.VISIBLE : View.GONE);
        liveView.setVisibility(data.live ? View.VISIBLE : View.GONE);
        discountView.setVisibility(data.isOnSales ? View.VISIBLE : View.GONE);

        pfImageLoader.display(data.coverImageUrl, coverImage, ImageLoaderUtil.LoadType.piaofunType);
        titleTv.setText(data.name);
        timeTv.setText(data.getRangeTime());
        placeTv.setText(data.venueName);

        if (data.minPrice >= 0) {
            priceTv.setText("￥" + StringUtil.getPriceString(data.minPrice));
            priceTailTv.setText("起");
        } else {
            priceTv.setText("");
            priceTailTv.setText("暂无价格");
        }

        ViewHolder.get(convertView, R.id.divider).setVisibility(View.VISIBLE);

        convertView.setTag(R.layout.item_homepage, data);
    }
}
