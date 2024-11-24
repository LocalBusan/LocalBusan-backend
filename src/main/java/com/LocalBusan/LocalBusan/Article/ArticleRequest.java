package com.LocalBusan.LocalBusan.Article;
import com.fasterxml.jackson.annotation.JsonProperty;
public class ArticleRequest {
    private Integer user_id;
    private Integer region_id;
    private Integer category_id;
    private String title;
    private String subtitle;
    private String thumbnail_url;
    private String content;

    public ArticleRequest(Integer user_id,
                          Integer region_id,
                          Integer category_id,
                          String title,
                          String subtitle,
                          String thumbnail_url,
                          String content) {
        this.user_id = user_id;
        this.region_id = region_id;
        this.category_id = category_id;
        this.title = title;
        this.subtitle = subtitle;
        this.thumbnail_url = thumbnail_url;
        this.content = content;
    }

    // Getters and Setters
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Integer region_id) {
        this.region_id = region_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
