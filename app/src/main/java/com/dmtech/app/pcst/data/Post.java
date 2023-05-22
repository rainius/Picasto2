package com.dmtech.app.pcst.data;
import android.text.TextUtils;

import com.dmtech.app.pcst.entity.PostView;

import java.util.ArrayList;
import java.util.List;
//帖子类
public class Post {

    public static Post fromPostView(PostView pv) {
        Post post = new Post();
        post.setId(pv.getId());
        post.setAuthorHead(pv.getAuthorHead());
        post.setAuthor(pv.getAuthor());
        post.setText(pv.getTextContent());
        post.setLike(pv.getIsLike() == 1);
        post.setLocation(pv.getLocation());
        post.setTimestamp(pv.getTimestamp());
        post.setNumComment(pv.getNumComment());
        post.setNumLike(pv.getNumLike());
        //帖子的图集
        List<String> images = new ArrayList<>();
        if (!TextUtils.isEmpty(pv.getImage1())) { images.add(pv.getImage1()); }
        if (!TextUtils.isEmpty(pv.getImage2())) { images.add(pv.getImage2()); }
        if (!TextUtils.isEmpty(pv.getImage3())) { images.add(pv.getImage3()); }
        if (!TextUtils.isEmpty(pv.getImage4())) { images.add(pv.getImage4()); }
        if (!TextUtils.isEmpty(pv.getImage5())) { images.add(pv.getImage5()); }
        if (!TextUtils.isEmpty(pv.getImage6())) { images.add(pv.getImage6()); }
        if (!TextUtils.isEmpty(pv.getImage7())) { images.add(pv.getImage7()); }
        if (!TextUtils.isEmpty(pv.getImage8())) { images.add(pv.getImage8()); }
        if (!TextUtils.isEmpty(pv.getImage9())) { images.add(pv.getImage9()); }
        post.setPhotos(images);

        return post;
    }

    private Long id;    //帖子的服务端ID，便于将来查询作为帖子的标识
    private String authorHead;  //头像链接
    private String author;      //作者用户名
    private List<String> photos;//本帖照片链接列表
    private boolean like;       //是否点赞
    private int numLike;        //本帖点赞数
    private String text;        //本帖正文
    private int numComment;     //本帖回复数
    private String location;       //发帖位置
    private long timestamp;     //发帖时间

    public Post() {}

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
