package com.example.administrator.campus;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zcw.togglebutton.ToggleButton;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTitleTextView;//标题文本
    private Button mBackwardbButton;//返回键
    private Button mSettingButton;//设置键
    private FrameLayout mContentLayout;//下方内容
    private ToggleButton toggleButton;
    public static boolean messageAvoid_status = false;
    private String className = null;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    private LinearLayout ll_clear_cookie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initTitle();
        toggleButton = findViewById(R.id.toggle_message_avoid);
        ll_clear_cookie = findViewById(R.id.ll_clear_cookie);
        ll_clear_cookie.setOnClickListener(this);
        sp = this.getPreferences(Context.MODE_PRIVATE);
        editor = sp.edit();
        className = getIntent().getStringExtra("class");
        if (!className.isEmpty()& className!=null) {
            setToggleButton();
        }
        toggleButton.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if ((on == true)) {
                    if(!className.isEmpty()& className!=null) {
                        Toast.makeText(SettingActivity.this,  "消息通知打开",Toast.LENGTH_SHORT).show();
                        editor.putBoolean(className,true);
                        editor.commit();
                    }
                } else {
                    if (!className.isEmpty()& className!=null) {
                        Toast.makeText(SettingActivity.this,"消息通知关闭",Toast.LENGTH_SHORT).show();
                        editor.putBoolean(className,false);
                        editor.commit();
                    }
                }
            }
        });
    }

    private void setToggleButton() {
        messageAvoid_status = sp.getBoolean(className, false);
        if(messageAvoid_status) {
            toggleButton.toggleOn();
        }else {
            toggleButton.toggleOff();
        }
    }

    private void initTitle() {
        mTitleTextView = findViewById(R.id.text_title);
        mTitleTextView.setText("通知设置");
        mBackwardbButton = findViewById(R.id.button_backward);
        mBackwardbButton.setVisibility(View.VISIBLE);
        mSettingButton = findViewById(R.id.button_setting);
        mSettingButton.setVisibility(View.INVISIBLE);
        mContentLayout = findViewById(R.id.content_fl);
        mContentLayout.setVisibility(View.GONE);
        mBackwardbButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_backward :
                finish();
                break;
            case  R.id.ll_clear_cookie :
                Toast.makeText(SettingActivity.this,"清空成功",Toast.LENGTH_SHORT).show();
        }
    }
}
