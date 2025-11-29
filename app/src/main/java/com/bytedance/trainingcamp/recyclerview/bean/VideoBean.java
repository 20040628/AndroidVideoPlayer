package com.bytedance.trainingcamp.recyclerview.bean;

/**
 * create by WUzejian on 2025/11/17
 */
public class VideoBean {
    // 用户名
    private int id;                 // 主键自增 id
    private String username;        // 用户昵称
    private String avatar;          // 头像 URL
    private String description;     // 视频描述（长标题）
    private String cover;           // 封面 URL
    private String videoLink;       // 视频链接
    private int views;              // 观看人数

    public VideoBean() {

    };
    // 构造方法
    public VideoBean(int id, String username, String avatar, String description,
                     String cover, String videoLink, int views) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.description = description;
        this.cover = cover;
        this.videoLink = videoLink;
        this.views = views;
    }

    // --- Getter 和 Setter ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
