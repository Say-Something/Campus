package com.example.administrator.campus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.campus.myclass.AnnouncementAndNoticeActivity;
import com.example.administrator.campus.myclass.MyTimeTableActivity;
import com.example.administrator.campus.myclass.PublishHomeworkActivity;
import com.example.administrator.campus.myclass.PublishNoticeActivity;
import com.example.administrator.campus.myclass.SearchAttendanceActivity;
import com.example.administrator.campus.myclass.SearchTimeTableActivity;
import com.example.administrator.campus.pay.CardLossReportActivity;
import com.example.administrator.campus.pay.CardRechargeActivity;
import com.example.administrator.campus.safe.AttendanceApplyActivity;
import com.example.administrator.campus.safe.ClassAttdanceActivity;
import com.example.administrator.campus.safe.NeedApprovalActivity;
import com.example.administrator.campus.safe.PersonalAttdanceActivity;
import com.example.administrator.campus.safe.PersonalLeaveActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    LinearLayout bottom_ll;
    Button message_bt;
    Button campus_bt;
    Button mine_bt;
    FrameLayout fl_message;
    LinearLayout ll_campus;
    FrameLayout fl_mine;
    public static ListView listView;
    public static MyAdapter myAdapter;
    private List<Item> itemList = new ArrayList<Item>();
    private TextView mTitleTextView;//标题文本
    private Button mBackwardbButton;//返回键
    private Button mSettingButton;//设置键
    private FrameLayout mContentLayout;//下方内容
    //新通知
    private String newTransaction;
    private String newApproval;
    private String newCardManagement;
    private String newAnnouncement;
    private String newAttendence;
    private SharedPreferences sp;
    private Item transaction;
    private Item approval;
    private Item  cardManagement;
    private Item announcement;
    private Item attendence;
    private RecyclerView campus_rv;
    private List<CampusItem> campusItemList = new ArrayList<CampusItem>();

    //todo 消息模板页面，首页添加时间的view,每个子item内容的完善
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CacheActivity.addActivity(this);
        sp = getSharedPreferences("Keeping",MODE_PRIVATE);
        bottom_ll = findViewById(R.id.bottom_ll);
        message_bt = findViewById(R.id.message_bt);
        campus_bt = findViewById(R.id.campus_bt);
        mine_bt = findViewById(R.id.mine_bt);
        fl_message = findViewById(R.id.fl_message);
        ll_campus = findViewById(R.id.ll_campus);
        fl_mine = findViewById(R.id.fl_mine);
        listView = findViewById(R.id.main_lv);
        campus_rv = findViewById(R.id.campus_rv);
        //注册广播接收者
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshActivity");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        //初始化标题
        initTitle();
        //把新动态更新到这些变量
        initNewAction();
        // 初始化消息页面ListView Item实例
        initItemList();
        //初始化listView
        initListView();
        message_bt.setOnClickListener(this);
        campus_bt.setOnClickListener(this);
        mine_bt.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    private void initNewAction() {
        newTransaction = "刘雅提交了交易申请";
        newApproval = "刘雅提交了请假申请，需审批";
        newCardManagement = "刘雅提交了卡挂失申请，需审批";
        newAnnouncement = "刘雅发布了公告";
        newAttendence = "刘雅签到打卡";
    }

    private void initTitle() {
        fl_message.setVisibility(View.VISIBLE);
        ll_campus.setVisibility(View.GONE);
        fl_mine.setVisibility(View.GONE);
        message_bt.setBackgroundResource(R.drawable.bottom_main_selected);
        campus_bt.setBackgroundResource(R.drawable.bottom_search_normal);
        mine_bt.setBackgroundResource(R.drawable.bottom_more_normal);

        mTitleTextView = findViewById(R.id.text_title);
        mTitleTextView.setText("消息");
        mBackwardbButton = findViewById(R.id.button_backward);
        mBackwardbButton.setVisibility(View.INVISIBLE);
        mSettingButton = findViewById(R.id.button_setting);
        mSettingButton.setVisibility(View.INVISIBLE);
        mContentLayout = findViewById(R.id.content_fl);
        mContentLayout.setVisibility(View.GONE);
    }
    /**
     *定义校园模块实例
     */
    private CampusItem campusItem;
    private String[] campuSecurityName = {"需我审批","请假录入","班级考勤","个人考勤","个人请假"};
    private int[] campuSecurityIcons = {R.mipmap.teacher_approval, R.mipmap.teacher_rest, R.mipmap.teacher_check,
            R.mipmap.icon_personal_attendance, R.mipmap.icon_personal_leave};
    private String[] campusPayName = {"卡片充值","卡片挂失"};
    private int[] campusPayIcons = {R.mipmap.teacher_recharge, R.mipmap.teacher_lost};
    private String[] campusClassName = {"我的课表","发布作业","通知公告","发布通知","查询课表","查询出勤"};
    private int[] campusClassIcons = {R.mipmap.teacher_schedule, R.mipmap.teacher_homework, R.mipmap.teacher_notice,
            R.mipmap.icon_release_notice, R.mipmap.icon_query_schedule,R.mipmap.icon_query_attendance};
    private String[] campusTitle = {"校园安全","校园支付","我的班级"};
    //定义三种常量  表示三种条目类型
    public static final int TYPE_CAMPUS= 0;
    public static final int TYPE_CAMPUS_TITLE = 1;
    /*
     * 初始化消息页面ListView Item实例
     * */
    private void initItemList() {
        transaction = new Item("交易记录", R.drawable.item_pay, newTransaction, getTransactionNotice());
        approval = new Item("审批中心", R.drawable.item_approval, newApproval, getApprovalNotice());
        cardManagement = new Item("卡管理中心", R.drawable.teacher_card_img, newAnnouncement, getCardManagementNotice());
        announcement = new Item("通知公告", R.drawable.teacher_notice_img, newAnnouncement, getAnnouncementNotice());
        attendence = new Item("每日出勤", R.drawable.teacher_location_img, newAttendence, getAttendenceNotice());
        itemList.add(transaction);
        itemList.add(approval);
        itemList.add(cardManagement);
        itemList.add(announcement);
        itemList.add(attendence);
        //初始化校园模块的item实例
        campusItemList.add(new CampusItem(campusTitle[0],0,TYPE_CAMPUS_TITLE));
        for(int i=0; i<campuSecurityName.length; i++) {

            campusItem = new CampusItem(campuSecurityName[i],campuSecurityIcons[i],TYPE_CAMPUS);
            campusItemList.add(campusItem);
        }
        campusItemList.add(new CampusItem(campusTitle[1],0,TYPE_CAMPUS_TITLE));
        for(int i=0; i<campusPayName.length; i++) {
            campusItem = new CampusItem(campusPayName[i],campusPayIcons[i],TYPE_CAMPUS);
            campusItemList.add(campusItem);
        }
        campusItemList.add(new CampusItem(campusTitle[2],0,TYPE_CAMPUS_TITLE));
        for(int i=0; i<campusClassName.length; i++) {
            campusItem = new CampusItem(campusClassName[i],campusClassIcons[i],TYPE_CAMPUS);
            campusItemList.add(campusItem);
        }
    }

    /*
     * 初始化消息页面listView、校园页面RecyclerView
     * */
    private void initListView() {
        //消息模块item
        myAdapter = new MyAdapter(HomeActivity.this,R.layout.adapter_item,itemList);
        listView.setAdapter(myAdapter);
        //校园模块item
        GridLayoutManager gridLayoutManager = new GridLayoutManager (this,3);
        campus_rv.setLayoutManager(gridLayoutManager);
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(campusItemList);
        myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                Toast.makeText(HomeActivity.this,"点击了第 "+position+" 个item",Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 1:
                        //需我审批
                        intent.setClass(HomeActivity.this, NeedApprovalActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //请假录入
                        intent.setClass(HomeActivity.this, AttendanceApplyActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        //"班级考勤"
                        intent.setClass(HomeActivity.this, ClassAttdanceActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        //"个人考勤"
                        intent.setClass(HomeActivity.this, PersonalAttdanceActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        //"个人请假"
                        intent.setClass(HomeActivity.this, PersonalLeaveActivity.class);
                        startActivity(intent);
                        break;

                    case 7:
                        //"卡片充值"
                        intent.setClass(HomeActivity.this, CardRechargeActivity.class);
                        startActivity(intent);
                        break;
                    case 8:
                        //"卡片挂失"
                        intent.setClass(HomeActivity.this, CardLossReportActivity.class);
                        startActivity(intent);
                        break;
                    case 10:
                        //"我的课表"
                        intent.setClass(HomeActivity.this, MyTimeTableActivity.class);
                        startActivity(intent);
                        break;
                    case 11:
                        //"发布作业"
                        intent.setClass(HomeActivity.this, PublishHomeworkActivity.class);
                        startActivity(intent);
                        break;
                    case 12:
                        //"通知公告"
                        intent.setClass(HomeActivity.this, AnnouncementAndNoticeActivity.class);
                        startActivity(intent);
                        break;
                    case 13:
                        //"发布通知"
                        intent.setClass(HomeActivity.this, PublishNoticeActivity.class);
                        startActivity(intent);
                        break;
                    case 14:
                        //"查询课表"
                        intent.setClass(HomeActivity.this, SearchTimeTableActivity.class);
                        startActivity(intent);
                        break;
                    case 15:
                        //"查询出勤"
                        intent.setClass(HomeActivity.this, SearchAttendanceActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        campus_rv.setAdapter(myRecyclerViewAdapter);

    }
    private void refreshNotice() {
        transaction.setNotice( sp.getBoolean("TransactionActivity", false));
        approval .setNotice( sp.getBoolean("ApprovalActivity", false));
        cardManagement.setNotice( sp.getBoolean("CardManagementActivity", false));
        announcement.setNotice( sp.getBoolean("AnnouncementActivity", false));
        attendence.setNotice( sp.getBoolean("AttendanceActivity", false));
    }


    private Boolean getTransactionNotice() {
        return  sp.getBoolean("TransactionActivity", false);
    }

    private Boolean getApprovalNotice() {
        return sp.getBoolean("ApprovalActivity", false);
    }
    private Boolean getCardManagementNotice() {
        return sp.getBoolean("CardManagementActivity", false);
    }

    private Boolean getAnnouncementNotice() {
        return sp.getBoolean("AnnouncementActivity", false);
    }

    private Boolean getAttendenceNotice() {
        return sp.getBoolean("AttendanceActivity", false);
    }

    /*
     * 首页底部三个Button点击事件
     * */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.message_bt:
            mTitleTextView.setText("消息");
            message_bt.setBackgroundResource(R.drawable.bottom_main_selected);
            campus_bt.setBackgroundResource(R.drawable.bottom_search_normal);
            mine_bt.setBackgroundResource(R.drawable.bottom_more_normal);
            fl_message.setVisibility(View.VISIBLE);
            ll_campus.setVisibility(View.GONE);
            fl_mine.setVisibility(View.GONE);
            break;
        case R.id.campus_bt:
            //之后根据地图定位获取学校名称
            mTitleTextView.setText("江西省电子信息技师学院");
            campus_bt.setBackgroundResource(R.drawable.bottom_search_selected);
            message_bt.setBackgroundResource(R.drawable.bottom_main_normal);
            mine_bt.setBackgroundResource(R.drawable.bottom_more_normal);
            fl_message.setVisibility(View.GONE);
            ll_campus.setVisibility(View.VISIBLE);
            fl_mine.setVisibility(View.GONE);
            break;
        case R.id.mine_bt:
            mTitleTextView.setText("我");
            mine_bt.setBackgroundResource(R.drawable.bottom_more_selected);
            campus_bt.setBackgroundResource(R.drawable.bottom_search_normal);
            message_bt.setBackgroundResource(R.drawable.bottom_main_normal);
            fl_message.setVisibility(View.GONE);
            ll_campus.setVisibility(View.GONE);
            fl_mine.setVisibility(View.VISIBLE);
            break;
        }
    }
    private Intent intent = new Intent() ;
    /*
     * 监听消息界面ListView Item的点击事件
     * */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

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
            ImageView imageView = view.findViewById(R.id.adapter_item_title_iv);
            TextView textView = view.findViewById(R.id.adapter_item_content_tv);
            TextView new_action_tv = view.findViewById(R.id.new_action_tv);
            ImageView noticeRing = view.findViewById(R.id.notice_ring_img);
            imageView.setImageResource(item.getImageId());
            if(!item.getNewAction().isEmpty()) {
                new_action_tv.setVisibility(View.VISIBLE);
                new_action_tv.setText(item.getNewAction());
            }else {
                new_action_tv.setVisibility(View.INVISIBLE);
            }
            if(item.isNotice()) {
                noticeRing.setVisibility(View.VISIBLE);
            }else {
                noticeRing.setVisibility(View.INVISIBLE);
            }
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
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshActivity"))
            {
                refreshNotice();
                myAdapter.notifyDataSetChanged();
//                Toast.makeText(HomeActivity.this,"接收到了广播",Toast.LENGTH_SHORT).show();

            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        unregisterReceiver(mRefreshBroadcastReceiver);
    }
}
