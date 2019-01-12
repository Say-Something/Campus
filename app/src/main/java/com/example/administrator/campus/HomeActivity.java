package com.example.administrator.campus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    LinearLayout bottom_ll;
    Button message_bt;
    Button campus_bt;
    Button mine_bt;
    FrameLayout fl_message;
    FrameLayout fl_campus;
    FrameLayout fl_mine;
    ListView listView;
    MyAdapter myAdapter;
    private List<Item> itemList = new ArrayList<Item>();
    private TextView mTitleTextView;//标题文本
    private Button mBackwardbButton;//返回键
    private Button mSettingButton;//设置键
    private FrameLayout mContentLayout;//下方内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CacheActivity.addActivity(this);
        bottom_ll = findViewById(R.id.bottom_ll);
        message_bt = findViewById(R.id.message_bt);
        campus_bt = findViewById(R.id.campus_bt);
        mine_bt = findViewById(R.id.mine_bt);
        fl_message = findViewById(R.id.fl_message);
        fl_campus = findViewById(R.id.fl_campus);
        fl_mine = findViewById(R.id.fl_mine);
        listView = findViewById(R.id.main_lv);
        initTitle();
        initItemList();
        initListView();
        message_bt.setOnClickListener(this);
        campus_bt.setOnClickListener(this);
        mine_bt.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    private void initTitle() {
        mTitleTextView = findViewById(R.id.text_title);
        mTitleTextView.setText("消息");
        mBackwardbButton = findViewById(R.id.button_backward);
        mBackwardbButton.setVisibility(View.INVISIBLE);
        mSettingButton = findViewById(R.id.button_setting);
        mSettingButton.setVisibility(View.INVISIBLE);
        mContentLayout = findViewById(R.id.content_fl);
        mContentLayout.setVisibility(View.GONE);
    }

    /*
     * 初始化消息页面ListView Item实例
     * */
    private void initItemList() {
        Item transaction = new Item("交易记录", R.drawable.item_pay);
        Item approval = new Item("审批中心", R.drawable.item_approval);
        Item  cardManagement = new Item("卡管理中心", R.drawable.teacher_card_img);
        Item announcement = new Item("通知公告", R.drawable.teacher_notice_img);
        Item attendance = new Item("每日出勤", R.drawable.teacher_location_img);
        itemList.add(transaction);
        itemList.add(approval);
        itemList.add(cardManagement);
        itemList.add(announcement);
        itemList.add(attendance);
    }
    /*
     * 初始化listView
     * */
    private void initListView() {
        myAdapter = new MyAdapter(HomeActivity.this,R.layout.adapter_item,itemList);
        listView.setAdapter(myAdapter);
    }
    /*
     * 首页底部三个Button点击事件
     * */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.message_bt:
            message_bt.setBackgroundResource(R.drawable.bottom_main_selected);
            campus_bt.setBackgroundResource(R.drawable.bottom_search_normal);
            mine_bt.setBackgroundResource(R.drawable.bottom_more_normal);
            fl_message.setVisibility(View.VISIBLE);
            fl_campus.setVisibility(View.GONE);
            fl_mine.setVisibility(View.GONE);
            break;
        case R.id.campus_bt:
            campus_bt.setBackgroundResource(R.drawable.bottom_search_selected);
            message_bt.setBackgroundResource(R.drawable.bottom_main_normal);
            mine_bt.setBackgroundResource(R.drawable.bottom_more_normal);
            fl_message.setVisibility(View.GONE);
            fl_campus.setVisibility(View.VISIBLE);
            fl_mine.setVisibility(View.GONE);
            break;
        case R.id.mine_bt:
            mine_bt.setBackgroundResource(R.drawable.bottom_more_selected);
            campus_bt.setBackgroundResource(R.drawable.bottom_search_normal);
            message_bt.setBackgroundResource(R.drawable.bottom_main_normal);
            fl_message.setVisibility(View.GONE);
            fl_campus.setVisibility(View.GONE);
            fl_mine.setVisibility(View.VISIBLE);
            break;
        }
    }
    /*
     * 监听消息界面ListView Item的点击事件
     * */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent() ;
        switch (position) {
            case 0:
                //交易记录
                intent.setClass(HomeActivity.this, TransactionActivity.class);
                startActivity(intent);
                break;
            case 1:
                //审批中心
                intent.setClass(HomeActivity.this, ApprovalActivity.class);
                startActivity(intent);
                break;
            case 2:
                //卡管理中心
                intent.setClass(HomeActivity.this, CardManagementActivity.class);
                startActivity(intent);
                break;
            case 3:
                //通知公告
                intent.setClass(HomeActivity.this, AnnouncementActivity.class);
                startActivity(intent);
                break;
            case 4:
                //每日出勤
                intent.setClass(HomeActivity.this, AttendanceActivity.class);
                startActivity(intent);
                break;
        }

    }
    /*
     * 自定义消息页面ListView的adapter
     * */
    private class MyAdapter extends ArrayAdapter {
        private final int adapter_item;
        public MyAdapter(Context context, int resourceId, List<Item> object) {
            super(context, resourceId, object);
            adapter_item = resourceId;
        }
        @Override
        public View getView(int position, View contentView, ViewGroup viewGroup) {
            Item item = (Item) getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(adapter_item,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.adapter_item_title_iv);
            TextView textView = (TextView)view.findViewById(R.id.adapter_item_content_tv);
            imageView.setImageResource(item.getImageId());
            textView.setText(item.getName());
            textView.setTextColor(Color.BLACK);
            return view;
        }
    }
    /*
     * 双击退出
     * */
    private long firstTime = 0;
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if(secondTime - firstTime >2000) {
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        }else {
            CacheActivity.finishActivity();
        }
    }
    //TODO 设置界面，已完成按钮，添加记录，监听事件
}
