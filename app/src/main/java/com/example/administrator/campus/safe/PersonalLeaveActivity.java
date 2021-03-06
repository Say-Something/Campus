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

//个人请假-右上方筛选按钮弹出FrameLayout,根据选择时间轴查看个人请假记录
public class PersonalLeaveActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTitleTextView;//标题文本
    private Button mBackwardbButton;//返回键
    private Button mCookieButton;//查看记录
    private FrameLayout mContentLayout;//下方内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presonal_leave);
        CacheActivity.addActivity(this);
        initTitle();
    }
    private void initTitle() {
        mTitleTextView = findViewById(R.id.safe_text_title);
        mTitleTextView.setText("个人请假");
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
        CacheActivity.finishSingleActivity(PersonalLeaveActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.safe_button_backward :
                finish();
                break;
            case R.id.safe_button_more ://弹出FrameLayout,根据选择时间轴查看个人请假记录
                Intent intent = new Intent();
                intent.setClass(PersonalLeaveActivity.this,SettingActivity.class);
                intent.putExtra("class","TransactionActivity");
                startActivityForResult(intent,0);
                break;
        }
    }
}
