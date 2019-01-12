package com.example.administrator.campus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ApprovalActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTitleTextView;//标题文本
    private Button mBackwardbButton;//返回键
    private Button mSettingButton;//设置键
    private FrameLayout mContentLayout;//下方内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        CacheActivity.addActivity(this);
        initTitle();
    }
    private void initTitle() {
        mTitleTextView = findViewById(R.id.text_title);
        mTitleTextView.setText("审批中心");
        mBackwardbButton = findViewById(R.id.button_backward);
        mBackwardbButton.setVisibility(View.VISIBLE);
        mSettingButton = findViewById(R.id.button_setting);
        mSettingButton.setVisibility(View.VISIBLE);
        mContentLayout = findViewById(R.id.content_fl);
        mContentLayout.setVisibility(View.VISIBLE);
        mBackwardbButton.setOnClickListener(this);
        mSettingButton.setOnClickListener(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CacheActivity.finishSingleActivity(ApprovalActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_backward :
                finish();
                break;
            case R.id.button_setting :
                Intent intent = new Intent();
                intent.setClass(ApprovalActivity.this,SettingActivity.class);
                intent.putExtra("class","ApprovalActivity");
                startActivityForResult(intent,0);
                break;
        }
    }
}
