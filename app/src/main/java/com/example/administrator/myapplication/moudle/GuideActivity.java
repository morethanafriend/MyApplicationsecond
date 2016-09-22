package com.example.administrator.myapplication.moudle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import cn.piaofun.common.base.BaseActivity;

/**
 * Created by Administrator on 2016/9/22.
 */
public class GuideActivity extends BaseActivity {

    private PFConvenientBanner guideBanner;
    private List<Integer> guideImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 不显示程序的标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_guide);
        initView();
    }

    @Override
    protected void initView() {
        guideImages = new ArrayList<>();
        guideImages.add(R.mipmap.guide_one);
        guideImages.add(R.mipmap.guide_two);
        guideImages.add(R.mipmap.guide_three);
        guideImages.add(R.mipmap.guide_four);
//        guideImages.add(R.mipmap.guide_five);

        guideBanner = (PFConvenientBanner) findViewById(R.id.banner_guide);
        guideBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, guideImages)
        ;
    }

    private class LocalImageHolderView implements CBPageAdapter.Holder<Integer> {

        private ImageView imageView;
        private Button enterBtn;

        @Override
        public View createView(Context context) {
            View rootView = LayoutInflater.from(GuideActivity.this).inflate(R.layout.fragment_guide, null);
            imageView = (ImageView) rootView.findViewById(R.id.img_back);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            enterBtn = (Button) rootView.findViewById(R.id.btn_sure);
            return rootView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            if (position == guideImages.size() - 1) {
                enterBtn.setVisibility(View.VISIBLE);
                enterBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickEnter();
                    }
                });
            } else {
                enterBtn.setVisibility(View.GONE);
            }
//导入图片
            new ImageLoaderUtil(GuideActivity.this).display(data.intValue(), imageView);
        }
    }

    @Override
    protected void setListener() {

    }

    public void onClickEnter() {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }
}
