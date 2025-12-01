package com.bytedance.trainingcamp.data;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bytedance.trainingcamp.recyclerview.bean.VideoBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoApi {

    private static final String TAG = "VideoApi";

    // 你的 API 地址
    private static final String VIDEO_API_URL =
            "https://raw.githubusercontent.com/20040628/AndroidVideoPlayer/refs/heads/main/app/src/main/assets/videos.json";

    private static VideoApi instance;

    private final OkHttpClient client;
    private final Gson gson;
    private final Handler mainHandler;

    private VideoApi() {
        client = new OkHttpClient.Builder()
                .addInterceptor(new TimingInterceptor())
                .build();
        gson = new Gson();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static VideoApi getInstance() {
        if (instance == null) {
            synchronized (VideoApi.class) {
                if (instance == null) {
                    instance = new VideoApi();
                }
            }
        }
        return instance;
    }

    /**
     * 统计网络请求耗时的拦截器
     */
    private static class TimingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();

            Log.d(TAG, "开始请求: " + request.url());

            try {
                Response response = chain.proceed(request);
                long cost = System.currentTimeMillis() - startTime;
                Log.d(TAG, "请求完成: " + request.url() + " | 耗时: " + cost + "ms | 状态码: " + response.code());
                return response;
            } catch (Exception e) {
                long cost = System.currentTimeMillis() - startTime;
                Log.e(TAG, "请求失败: " + request.url() + " | 耗时: " + cost + "ms | 错误: " + e.getMessage());
                throw e;
            }
        }
    }

    /**
     * 获取在线视频列表
     */
    public void getVideoList(final ApiCallback<List<VideoBean>> callback) {
        Request request = new Request.Builder()
                .url(VIDEO_API_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() -> callback.onError(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String json = response.body().string();
                        JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
                        List<VideoBean> list = new ArrayList<>();

                        jsonArray.forEach(item -> {
                            VideoBean video = gson.fromJson(item, VideoBean.class);
                            list.add(video);
                        });

                        mainHandler.post(() -> callback.onSuccess(list));

                    } catch (Exception e) {
                        mainHandler.post(() -> callback.onError(e));
                    }
                } else {
                    mainHandler.post(() -> callback.onError(
                            new IOException("Response not successful: " + response.code())));
                }
            }
        });
    }

    /**
     * API 回调接口
     */
    public interface ApiCallback<T> {
        void onSuccess(T data);
        void onError(Exception e);
    }
}
