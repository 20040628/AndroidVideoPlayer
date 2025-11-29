package com.bytedance.trainingcamp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.bytedance.trainingcamp.recyclerview.bean.VideoBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    // 数据库文件名
    public static final String DATABASE_NAME = "DB.db";
    // 数据库版本号
    private static final int DATABASE_VERSION = 1;

    // 表名
    public static final String TABLE_NOTES = "videos";
    // 列名
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_AVATAR = "avatar";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_COVER = "cover";
     public static final String COLUMN_VIDEO_LINK = "video_link";
    public static final String COLUMN_VIEWS = "views";


    // 创建表的 SQL 语句
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT NOT NULL, " +
                    COLUMN_AVATAR + " TEXT NOT NULL, " +
                    COLUMN_DESC + " TEXT NOT NULL," +
                    COLUMN_COVER + " TEXT NOT NULL, " +
                    COLUMN_VIDEO_LINK + " TEXT NOT NULL, " +
                    COLUMN_VIEWS + " INTEGER NOT NULL" +
                    ");";
    private Context context;
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表
        db.execSQL(TABLE_CREATE);

        // 首次创建数据库时调用，执行建表语句
        try {
            InputStream is = context.getAssets().open("videos.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            reader.close();

            // 转成 JSONArray
            JSONArray jsonArray = new JSONArray(jsonBuilder.toString());

            // 逐条插入数据库
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                ContentValues values = new ContentValues();
                values.put(COLUMN_USERNAME, obj.getString("username"));
                values.put(COLUMN_AVATAR, obj.getString("avatar"));
                values.put(COLUMN_DESC, obj.getString("description"));
                values.put(COLUMN_COVER, obj.getString("cover"));
                values.put(COLUMN_VIDEO_LINK, obj.getString("video_link"));
                values.put(COLUMN_VIEWS, obj.getInt("views"));

                db.insert(TABLE_NOTES, null, values);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * - 数据库升级：当应用更新，数据库版本号增加时，自动调用 onUpgrade() 方法，让你可以在此执行数据迁移、修改表结构等操作。
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 数据库降级处理
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public List<VideoBean> getAllVideos() {
        List<VideoBean> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                VideoBean video = new VideoBean();
                video.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                video.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)));
                video.setAvatar(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR)));
                video.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC)));
                video.setCover(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COVER)));
                video.setVideoLink(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VIDEO_LINK)));
                video.setViews(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VIEWS)));
                list.add(video);
            }
            cursor.close();
        }
        return list;
    }

}

