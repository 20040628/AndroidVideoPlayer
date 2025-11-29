package com.bytedance.trainingcamp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bytedance.trainingcamp.data.MyDatabaseHelper;
import com.bytedance.trainingcamp.recyclerview.adapter.StaggeredAdapter;
import com.bytedance.trainingcamp.recyclerview.bean.VideoBean;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<VideoBean> mVideoList;
    private StaggeredAdapter staggeredAdapter;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_video);

        // 初始化数据库
        dbHelper = new MyDatabaseHelper(this);

        // 获取 RecyclerView
        mRecyclerView = findViewById(R.id.recyclerview_id);

        // 从数据库读取所有视频数据
        mVideoList = dbHelper.getAllVideos();

        // 设置 Adapter
        staggeredAdapter = new StaggeredAdapter(this, mVideoList);

        // 2列垂直瀑布流
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        // 设置 Adapter
        mRecyclerView.setAdapter(staggeredAdapter);
    }
}
