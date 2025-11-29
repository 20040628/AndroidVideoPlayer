package com.bytedance.trainingcamp.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.trainingcamp.R;
import com.bytedance.trainingcamp.recyclerview.viewholder.VideoViewHolder;
import com.bytedance.trainingcamp.recyclerview.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * StaggeredAdapter 支持瀑布流，展示视频封面、头像、昵称、简介和观看数
 */
public class StaggeredAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<VideoBean> dataList;
//    private List<Integer> heights; // 随机高度，用于瀑布流效果

    public StaggeredAdapter(Context context, List<VideoBean> dataList) {
        this.context = context;
        this.dataList = dataList != null ? dataList : new ArrayList<>();
        this.inflater = LayoutInflater.from(context);

//        // 随机高度模拟瀑布流
//        heights = new ArrayList<>();
//        for (int i = 0; i < this.dataList.size(); i++) {
//            heights.add((int) (800 + Math.random() * 400)); // 高度 400~800px
//        }

    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        // 设置瀑布流随机高度
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
//        lp.height = heights.get(position);
        holder.itemView.setLayoutParams(lp);

        // 绑定数据
        VideoBean video = dataList.get(position);
        holder.bindData(video);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // 更新数据源
    public void updateData(List<VideoBean> newData) {
        this.dataList.clear();
        if (newData != null) {
            this.dataList.addAll(newData);
        }
//        heights.clear();
//        for (int i = 0; i < this.dataList.size(); i++) {
//            heights.add((int) (400 + Math.random() * 400));
//        }
        notifyDataSetChanged();
    }
}
