package com.example.administrator.campus;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TitleActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTitleTextView;//标题文本
    private Button mBackwardbButton;//返回键
    private Button mSettingButton;//设置键
    private FrameLayout mContentLayout;//下方内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView() {
        super.setContentView(R.layout.activity_title);
        mTitleTextView = findViewById(R.id.text_title);
        mBackwardbButton = findViewById(R.id.button_backward);
        mSettingButton = findViewById(R.id.button_setting);
        mContentLayout = findViewById(R.id.content_fl);
        mBackwardbButton.setOnClickListener(this);
        mSettingButton.setOnClickListener(this);

    }
    public  void  setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }
    //返回键
    protected void onBackward(View backwardView) {
       finish();
    }
    //设置键
    protected void onForward(View forwardView) {
        Toast.makeText(this, "进入设置界面", Toast.LENGTH_SHORT).show();
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_backward:
                onBackward(view);
                break;
            case R.id.button_setting:
                onForward(view);
                break;
                default:
                    break;
        }
    }
//设置是否显示返回按钮
    protected void showBackwardView( boolean show) {
        if (mBackwardbButton != null) {
            if (show) {
                mBackwardbButton.setVisibility(View.VISIBLE);
            } else {
                mBackwardbButton.setVisibility(View.INVISIBLE);
            }
        }
    }
    //设置是否显示设置按钮
    protected void showSettingView( boolean show) {
        if (mSettingButton != null) {
            if (show) {
                mSettingButton.setVisibility(View.VISIBLE);
            } else {
                mSettingButton.setVisibility(View.INVISIBLE);
            }
        }
    }
    //设置是否显示下方内容
    protected void showContentView( boolean show) {
        if (mContentLayout != null) {
            if (show) {
                mContentLayout.setVisibility(View.VISIBLE);
            } else {
                mContentLayout.setVisibility(View.GONE);
            }
        }
    }
}
