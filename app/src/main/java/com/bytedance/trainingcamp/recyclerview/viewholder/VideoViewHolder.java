package com.bytedance.trainingcamp.recyclerview.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bytedance.trainingcamp.R;
import com.bytedance.trainingcamp.recyclerview.bean.VideoBean;

public class VideoViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvDesc;
    private ImageView ivCover;
    private TextView tvViews;

    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);

        ivAvatar = itemView.findViewById(R.id.iv_avatar);
        tvName   = itemView.findViewById(R.id.tv_name);
        tvDesc   = itemView.findViewById(R.id.tv_desc);
        ivCover  = itemView.findViewById(R.id.iv_cover);
        tvViews  = itemView.findViewById(R.id.tv_views);
    }

    public void bindData(VideoBean videoBean) {
        // 加载封面
        Glide.with(itemView.getContext())
                .load(videoBean.getCover())
                .centerCrop()
                .into(ivCover);

        // 加载头像
        Glide.with(itemView.getContext())
                .load(videoBean.getAvatar())
                .circleCrop() // 圆形裁剪
                .into(ivAvatar);

        // 设置文本
        tvName.setText(videoBean.getUsername());
        tvDesc.setText(videoBean.getDescription());
        tvViews.setText(String.valueOf(videoBean.getViews()));

        ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoBean.getVideoLink()));
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
