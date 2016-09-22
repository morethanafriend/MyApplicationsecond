package com.example.administrator.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/22.
 */
public class UpdateApkDialog extends Dialog implements View.OnClickListener {

    private static int ISVISIBLE = View.VISIBLE;
    private final String TopContent;
    private final String BottomContent;
    private final String SureContent;
    private String leftButton;
    private Context context;
    private OnCustomListener listener;

    private Button sureBtn;


    public UpdateApkDialog(Context context, String TopContent, String BottomContent, String SureContent) {
        super(context, R.style.DialogTheme);
        this.context = context;
        setCanceledOnTouchOutside(false);
        this.TopContent = TopContent;
        this.BottomContent = BottomContent;
        this.SureContent = SureContent;

    }


    public UpdateApkDialog(Context context, String TopContent, String BottomContent, String SureContent, String leftButton, int ISVISIBLE) {
        super(context, R.style.DialogTheme);
        this.context = context;
        setCanceledOnTouchOutside(false);
        this.TopContent = TopContent;
        this.BottomContent = BottomContent;
        this.SureContent = SureContent;
        this.leftButton = leftButton;
        this.ISVISIBLE = ISVISIBLE;
    }


    public void setCustomListener(OnCustomListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cs_custom, null);
        setContentView(view);
        setListener();
        initView(view);

    }

    private void initView(View view) {
        TextView topTv = (TextView) view.findViewById(R.id.tv_top_str);
        TextView bottomTv = (TextView) view.findViewById(R.id.tv_bottom_str);
        sureBtn = (Button) view.findViewById(R.id.btn_sure);
        View lineView = findViewById(R.id.lineview);

        Button cancelBtn = (Button) view.findViewById(R.id.btn_cancel);
        if ("".equals(TopContent)) {
            topTv.setVisibility(View.GONE);
        } else {
            topTv.setText(TopContent);
        }

        bottomTv.setText(BottomContent);
        sureBtn.setText(SureContent);

        cancelBtn.setText(leftButton);
        cancelBtn.setVisibility(ISVISIBLE);
        lineView.setVisibility(ISVISIBLE);

    }

    private void setListener() {
        findViewById(R.id.btn_sure).setOnClickListener(this);

        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                if (listener != null) {
                    listener.rightUpdate();
                }

                break;
            case R.id.btn_cancel:
                if (listener != null) {
                    listener.cancel();
                }


                break;
        }
    }

    public interface OnCustomListener {
        void rightUpdate();

        void cancel();
    }

}