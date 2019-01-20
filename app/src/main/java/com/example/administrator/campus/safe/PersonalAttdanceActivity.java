package com.example.administrator.campus.safe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.campus.CacheActivity;
import com.example.administrator.campus.R;
//个人考勤-点击标题 弹出日历可翻阅个人历史考勤记录，标题左右键拨动时间轴
public class PersonalAttdanceActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTitleTextView;//标题文本
    private Button mBackwardbButton;//返回键
    private Button mCookieButton;//查看记录
    private FrameLayout mContentLayout;//下方内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_attdence);
        CacheActivity.addActivity(this);
        initTitle();
    }
    private void initTitle() {
        mTitleTextView = findViewById(R.id.safe_text_title);
        mTitleTextView.setText("今天");
        mBackwardbButton = findViewById(R.id.safe_button_backward);
        mBackwardbButton.setVisibility(View.VISIBLE);
        mCookieButton = findViewById(R.id.safe_button_more);
        mCookieButton.setVisibility(View.INVISIBLE);
        mContentLayout = findViewById(R.id.safe_content_fl);
        mContentLayout.setVisibility(View.VISIBLE);
        mBackwardbButton.setOnClickListener(this);
        mCookieButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CacheActivity.finishSingleActivity(PersonalAttdanceActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.safe_button_backward :
                finish();
                break;

        }
    }
}
