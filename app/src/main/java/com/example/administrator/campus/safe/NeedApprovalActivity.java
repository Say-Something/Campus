package com.example.administrator.campus.safe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.campus.CacheActivity;
import com.example.administrator.campus.R;
import com.example.administrator.campus.SettingActivity;
import com.example.administrator.campus.TransactionActivity;
//需我审批
public class NeedApprovalActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTitleTextView;//标题文本
    private Button mBackwardbButton;//返回键
    private Button mCookieButton;//查看记录
    private FrameLayout mContentLayout;//下方内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_approval);
        CacheActivity.addActivity(this);
        initTitle();
    }

    private void initTitle() {
        mTitleTextView = findViewById(R.id.safe_text_title);
        mTitleTextView.setText("需我审批");
        mBackwardbButton = findViewById(R.id.safe_button_backward);
        mBackwardbButton.setVisibility(View.VISIBLE);
        mCookieButton = findViewById(R.id.safe_button_more);
        mCookieButton.setVisibility(View.VISIBLE);
        mContentLayout = findViewById(R.id.safe_content_fl);
        mContentLayout.setVisibility(View.VISIBLE);
        mBackwardbButton.setOnClickListener(this);
        mCookieButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CacheActivity.finishSingleActivity(NeedApprovalActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.safe_button_backward :
                finish();
                break;
            case R.id.safe_button_more ://跳转审批记录
                Intent intent = new Intent();
                intent.setClass(NeedApprovalActivity.this,SettingActivity.class);
                intent.putExtra("class","TransactionActivity");
                startActivityForResult(intent,0);
                break;
        }
    }
}
