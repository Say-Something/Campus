package com.example.administrator.campus.safe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.campus.CacheActivity;
import com.example.administrator.campus.R;
//班级考勤-显示班级整体出勤状态-时间-日期-星期-应到、实到、请假、迟到、未到人数，日历可翻阅历史班级出勤情况
public class ClassAttdanceActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mTitleTextView;//标题文本
    private Button mBackwardbButton;//返回键
    private Button mCookieButton;//查看记录
    private FrameLayout mContentLayout;//下方内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_attdence);
        CacheActivity.addActivity(this);
        initTitle();
    }
    private void initTitle() {
        mTitleTextView = findViewById(R.id.safe_text_title);
        mTitleTextView.setText("169901");
        mBackwardbButton = findViewById(R.id.safe_button_backward);
        mBackwardbButton.setVisibility(View.VISIBLE);
        mCookieButton = findViewById(R.id.safe_button_more);
        mCookieButton.setVisibility(View.INVISIBLE);
        mContentLayout = findViewById(R.id.safe_content_fl);
        mContentLayout.setVisibility(View.GONE);
        mBackwardbButton.setOnClickListener(this);
        mCookieButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CacheActivity.finishSingleActivity(ClassAttdanceActivity.this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.safe_button_backward :
                finish();
                break;
            case R.id.safe_text_title :
                //添加标题点击事件

                break;
        }
    }
}
