package com.bytedance.trainingcamp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bytedance.trainingcamp.data.MyDatabaseHelper;
import com.bytedance.trainingcamp.data.VideoApi;
import com.bytedance.trainingcamp.recyclerview.adapter.StaggeredAdapter;
import com.bytedance.trainingcamp.recyclerview.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private StaggeredAdapter staggeredAdapter;
//    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_video);

        // 初始化数据库
//        dbHelper = new MyDatabaseHelper(this);

        // 获取 RecyclerView
        mRecyclerView = findViewById(R.id.recyclerview_id);

        // 设置 Adapter
        staggeredAdapter = new StaggeredAdapter(this, new ArrayList<>());

        // 2列垂直瀑布流
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        // 设置 Adapter
        mRecyclerView.setAdapter(staggeredAdapter);

        // 获取在线视频列表
        fetchVideoList();
    }

    private void fetchVideoList() {
        VideoApi.getInstance().getVideoList(new VideoApi.ApiCallback<List<VideoBean>>() {
            @Override
            public void onSuccess(List<VideoBean> data) {
                Log.d(TAG, "Fetch video list success, size: " + data.size());
                staggeredAdapter.updateData(data);
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Fetch video list error: ", e);
            }
        });
    }
}
