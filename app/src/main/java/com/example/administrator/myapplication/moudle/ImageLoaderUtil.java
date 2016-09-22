package com.example.administrator.myapplication.moudle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.administrator.myapplication.R;

import java.io.File;

import cn.piaofun.common.PFApplication;
import cn.piaofun.common.util.FileUtil;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ImageLoaderUtil<T> {

    protected T context;
    protected int radius;
    protected LoadType loadType;

    public ImageLoaderUtil(T context) {
        this.context = context;
    }

    public ImageLoaderUtil(T context, int radius) {
        this.context = context;
        this.radius = radius;
    }

    public void display(String url, ImageView imageView) {
        display(url, imageView, LoadType.defaultType);
    }

    public void display(String url, final ImageView imageView, LoadType loadType) {
        RequestManager requestManager = getRequestManager(context);

        if (requestManager != null) {
            BitmapImageViewTarget target = new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                    circularBitmapDrawable.setCornerRadius(radius);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            };

            requestManager.load(url)
                    .asBitmap()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(loadType.getValue())
                    .error(loadType.getValue())
                    .into(target);
        }
    }

    public void display(File file, ImageView imageView) {
        display(file, imageView, LoadType.defaultType);
    }

    public void display(File file, final ImageView imageView, LoadType loadType) {
        RequestManager requestManager = getRequestManager(context);

        if (requestManager != null) {
            BitmapImageViewTarget target = new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                    circularBitmapDrawable.setCornerRadius(radius);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            };

            requestManager.load(file)
                    .asBitmap()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(loadType.getValue())
                    .error(loadType.getValue())
                    .into(target);
        }
    }

    public void display(int resId, ImageView imageView) {
        display(resId, imageView, LoadType.defaultType);
    }

    public void display(int resId, final ImageView imageView, LoadType loadType) {
        RequestManager requestManager = getRequestManager(context);

        if (requestManager != null) {
            BitmapImageViewTarget target = new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                    circularBitmapDrawable.setCornerRadius(radius);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            };

            requestManager.load(resId)
                    .asBitmap()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(loadType.getValue())
                    .error(loadType.getValue())
                    .into(target);
        }
    }

    public void displayInGallery(String url, final ImageView imageView, LoadType loadType) {
        RequestManager requestManager = getRequestManager(context);

        if (requestManager != null) {
            BitmapImageViewTarget target = new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                    circularBitmapDrawable.setCornerRadius(radius);
                    imageView.setImageBitmap(circularBitmapDrawable.getBitmap());
                }
            };

            requestManager.load(url)
                    .asBitmap()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(loadType.getValue())
                    .error(loadType.getValue())
                    .into(target);
        }
    }

    public void displayBlur(String url, final ImageView imageView, LoadType loadType, final int blurRadius) {
        RequestManager requestManager = getRequestManager(context);

        if (requestManager != null) {
            BitmapImageViewTarget target = new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
//                    imageView.setImageBitmap(FastBlur.doBlur(resource, blurRadius, false));
//                    imageView.setImageBitmap(resource);
                    blur(resource, imageView);
                }
            };

            requestManager.load(url)
                    .asBitmap()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(loadType.getValue())
                    .error(loadType.getValue())
                    .into(target);
        }
    }

    protected RequestManager getRequestManager(T context) {
        try {
            if (context instanceof Activity) {
                return Glide.with((Activity) context);
            } else if (context instanceof Fragment) {
                return Glide.with((Fragment) context);
            } else if (context instanceof android.app.Fragment) {
                return Glide.with((android.app.Fragment) context);
            } else if (context instanceof Context) {
                return Glide.with((Context) context);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private void blur(Bitmap bkg, ImageView view) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 8;//图片缩放比例；
        float radius = 20;//模糊程度

        Bitmap overlay = Bitmap.createBitmap(
                (int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()/ scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);


        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        view.setImageDrawable(new BitmapDrawable(view.getResources(), overlay));
        /**
         * 打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，可是将scaleFactor设置大一些。
         */
        Log.i("jerome", "blur time:" + (System.currentTimeMillis() - startMs));
    }

    protected Context getContext() {
        if (context instanceof Activity) {
            return ((Activity) context);
        } else if (context instanceof Fragment) {
            return ((Fragment) context).getActivity();
        } else if (context instanceof android.app.Fragment) {
            return ((android.app.Fragment) context).getActivity();
        } else if (context instanceof Context) {
            return (Context) context;
        } else {
            return null;
        }
    }

    public static File getCacheFile() {
        return Glide.getPhotoCacheDir(PFApplication.getInstance().getAppContext());
    }

    public static void clearCache() {
        Glide.get(PFApplication.getInstance().getAppContext()).clearMemory();
        FileUtil.deleteDir(getCacheFile());
    }

    public static long getCacheSize() {
        return FileUtil.getFolderSize(getCacheFile());
    }

    public enum LoadType {
        defaultType(0),
        avatarType(R.mipmap.me_avatar_default),
        piaofunType(R.mipmap.common_no_data_image),
        photoType(R.mipmap.common_default_photo),
        bannerType(R.mipmap.discovery_banner_default);

        private int defaultIconRes;

        LoadType(int res) {
            this.defaultIconRes = res;
        }

        public String getName() {
            return toString();
        }

        public int getValue() {
            return defaultIconRes;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}