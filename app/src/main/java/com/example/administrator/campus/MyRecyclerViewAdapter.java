package com.example.administrator.campus;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

class MyRecyclerViewAdapter extends RecyclerView.Adapter{
    private List<CampusItem> campusItemList;
    private int position = 0;
    private static final int TYPE_CAMPUS = 0;
    private static final int TYPE_CAMPUS_TITLE = 1;
private CampusHolder campusHolder;
    private CampusTitleHolder campusTitleHolder;
    private Context mContext;
    public MyRecyclerViewAdapter(List<CampusItem> campusItemList) {
        this.campusItemList = campusItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        //创建不同的 ViewHolder
        View view;
        //根据viewtype来创建条目
        if (viewType == TYPE_CAMPUS) {
            view =  View.inflate(parent.getContext(),R.layout.item_campus,null);
            campusHolder = new CampusHolder(view);
            return campusHolder;
        } else {
            view = View.inflate(parent.getContext(),R.layout.item_campus,null);
            campusTitleHolder = new CampusTitleHolder(view);
            return campusTitleHolder;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(campusItemList.get(position).getType()==TYPE_CAMPUS) {
                    return 1;
                }else {
                    return 3;
                }
            }
        });

    }

    /**
     * 创建两种ViewHolder
     */

    private class CampusHolder extends RecyclerView.ViewHolder {
        public CampusHolder(View itemView) {
            super(itemView);
            LinearLayout title_ll = itemView.findViewById(R.id.ll_campus_title);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); // , 1是可选写的
            lp.setMargins(0, 50, 0, 10);
            title_ll.setLayoutParams(lp);
            ImageView imageView = itemView.findViewById(R.id.item_campus_iv);
            TextView textView = itemView.findViewById(R.id.item_campus_tv);
            ImageView lineImageView = itemView.findViewById(R.id.title_line_iv);
            imageView.setVisibility(View.VISIBLE);
            lineImageView.setVisibility(View.INVISIBLE);
            imageView.setImageResource(campusItemList.get(position).getImageId());
            textView.setText(campusItemList.get(position).getItemName());
        }

    }
    private class CampusTitleHolder extends RecyclerView.ViewHolder {
        public CampusTitleHolder(View itemView) {
            super(itemView);
            ImageView imageView = itemView.findViewById(R.id.item_campus_iv);
            TextView textView = itemView.findViewById(R.id.item_campus_tv);
            ImageView lineImageView = itemView.findViewById(R.id.title_line_iv);
            imageView.setVisibility(View.GONE);
            lineImageView.setVisibility(View.VISIBLE);
            textView.setText(campusItemList.get(position).getItemName());
            textView.setTextColor(Color.GRAY);
        }

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position==6||position==9) {
            LinearLayout title_ll = holder.itemView.findViewById(R.id.ll_campus_title);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1); // , 1是可选写的
            lp.setMargins(0, 50, 0, 0);
            title_ll.setLayoutParams(lp);
        }
    }
    @Override
    public int getItemCount() {
        if (campusItemList != null) {
            return campusItemList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        this.position = position;
        CampusItem campusItem = campusItemList.get(position);
        if (campusItem.getType() == 0) {
            return TYPE_CAMPUS;
        } else {
            return TYPE_CAMPUS_TITLE;
        }
    }
}

