package com.example.administrator.campus.safe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.campus.CacheActivity;
import com.example.administrator.campus.R;
import com.example.administrator.campus.SettingActivity;

//请假录入-学生请假表格-提交表单数据
//下方假条表单布局及监听事件
public class AttendanceApplyActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTitleTextView;//标题文本
    private Button mBackwardbButton;//返回键
    private Button mCookieButton;//查看记录
    private FrameLayout mContentLayout;//下方内容
    private EditText et_student_name;
    private EditText et_student_stype;
    private EditText et_start_time;
    private EditText et_over_time;
    private EditText et_apply_reason;
    private Button bt_apply;
    private String studentName;
    private String studentType;
    private String startTime;
    private String overTime;
    private String applyReason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_apply);
        CacheActivity.addActivity(this);
        initTitle();
        et_student_name = findViewById(R.id.et_student_name);
        et_student_stype = findViewById(R.id.et_student_stype);
        et_start_time = findViewById(R.id.et_start_time);
        et_over_time = findViewById(R.id.et_over_time);
        et_apply_reason = findViewById(R.id.et_apply_reason);
        bt_apply = findViewById(R.id.bt_apply);
         studentName = et_student_name.getText().toString().trim();
         studentType = et_student_stype.getText().toString().trim();
         startTime = et_start_time.getText().toString().trim();
         overTime = et_over_time.getText().toString().trim();
         applyReason = et_apply_reason.getText().toString().trim();
         bt_apply.setOnClickListener(this);
    }
    private void initTitle() {
        mTitleTextView = findViewById(R.id.safe_text_title);
        mTitleTextView.setText("学生请假");
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
        CacheActivity.finishSingleActivity(AttendanceApplyActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.safe_button_backward :
                finish();
                break;
            case R.id.bt_apply:
                Toast.makeText(AttendanceApplyActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
            break;
        }
    }
}
