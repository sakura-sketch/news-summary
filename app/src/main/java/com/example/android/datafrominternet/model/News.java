package com.example.android.datafrominternet.model;

import java.util.List;

public class News {
    private String title;
    private String author;
    private String description;
    private String date;
    private String imageLink;
    private String source;
    private String url;

    public News(){

    }

    public News(String title, String author, String description, String date, String imageLink, String source, String url){
        this.title = title;
        this.author = author;
        this.date = date;
        this.description = description;
        this.imageLink = imageLink;
        this.source = source;
        this.url = url;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
