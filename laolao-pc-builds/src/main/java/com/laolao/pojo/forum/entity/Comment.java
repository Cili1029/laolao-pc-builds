package com.laolao.pojo.forum.entity;

import java.time.LocalDateTime;

public class Comment {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 关联帖子ID
     */
    private Integer postId;

    /**
     * 评论用户ID
     */
    private Integer userId;

    /**
     * 回复给哪个用户ID,0则为直接评论
     */
    private Integer replyToUserId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论图片，最多一张
     */
    private String image;

    /**
     * 状态：1正常，2删除
     */
    private Integer status;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getReplyToUserId() {
        return replyToUserId;
    }

    public void setReplyToUserId(Integer replyToUserId) {
        this.replyToUserId = replyToUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Comment() {
    }

    public Comment(Integer id, Integer postId, Integer userId, Integer replyToUserId, String content, String image, Integer status, Integer likeCount, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.replyToUserId = replyToUserId;
        this.content = content;
        this.image = image;
        this.status = status;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ForumComment{" +
                "id=" + id +
                ", postId=" + postId +
                ", userId=" + userId +
                ", replyToUserId=" + replyToUserId +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", status=" + status +
                ", likeCount=" + likeCount +
                ", createdAt=" + createdAt +
                '}';
    }
}
