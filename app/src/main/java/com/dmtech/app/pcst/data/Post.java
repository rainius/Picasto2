package com.dmtech.app.pcst.data;

import android.text.TextUtils;

import com.dmtech.app.pcst.entity.PostView;

import java.util.ArrayList;
import java.util.List;

public class Post {
    public static Post fromPostView(PostView pv) {
        //生成新的Post对象
        Post post = new Post();
        //用传入的PostView对象内容填充post
        post.setId(pv.getId());
        post.setAuthorHead(pv.getAuthorHead());
        post.setAuthor(pv.getAuthor());
        post.setText(pv.getTextContent());
        post.setLike(pv.getIsLike() == 1);
        post.setLocation(pv.getLocation());
        post.setTimestamp(pv.getTimestamp());
        post.setNumComment(pv.getNumComment());
        post.setNumLike(pv.getNumLike());
        //构建图片列表
        List<String> images = new ArrayList<>();
        //如果pv中的图片存在，就添加到图片列表
        if (!TextUtils.isEmpty(pv.getImage1())) { images.add(pv.getImage1()); }
        if (!TextUtils.isEmpty(pv.getImage2())) { images.add(pv.getImage2()); }
        if (!TextUtils.isEmpty(pv.getImage3())) { images.add(pv.getImage3()); }
        if (!TextUtils.isEmpty(pv.getImage4())) { images.add(pv.getImage4()); }
        if (!TextUtils.isEmpty(pv.getImage5())) { images.add(pv.getImage5()); }
        if (!TextUtils.isEmpty(pv.getImage6())) { images.add(pv.getImage6()); }
        if (!TextUtils.isEmpty(pv.getImage7())) { images.add(pv.getImage7()); }
        if (!TextUtils.isEmpty(pv.getImage8())) { images.add(pv.getImage8()); }
        if (!TextUtils.isEmpty(pv.getImage9())) { images.add(pv.getImage9()); }
        //将图片列表填充到post对象
        post.setPhotos(images);

        return post;
    }

    private Long id;
    private String authorHead;
    private String author;
    private List<String> photos;
    private boolean like;
    private int numLike;
    private String text;
    private int numComment;
    private String location;
    private long timestamp;

    public Post() {
    }

    public Post(String author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorHead() {
        return authorHead;
    }

    public void setAuthorHead(String authorHead) {
        this.authorHead = authorHead;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public int getNumLike() {
        return numLike;
    }

    public void setNumLike(int numLike) {
        this.numLike = numLike;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumComment() {
        return numComment;
    }

    public void setNumComment(int numComment) {
        this.numComment = numComment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
