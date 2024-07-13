package org.koreait;

import java.util.Map;

public class Article {
    private int id;
    private String RegDate;
    private String UpdateDate;
    private String title;
    private String body;

    public Article(int id, String regDate, String updateDate, String title, String body) {
        this.id = id;
        RegDate = regDate;
        UpdateDate = updateDate;
        this.title = title;
        this.body = body;
    }

    public Article(Map<String, Object> articleMap){
        this.id = (int) articleMap.get("id");
        this.RegDate = (String)articleMap.get("RegDate");
        this.UpdateDate = (String) articleMap.get("UpdateDate");
        this.title = (String) articleMap.get("title");
        this.body = (String) articleMap.get("body");
    }
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", RegDate='" + RegDate + '\'' +
                ", UpdateDate='" + UpdateDate + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public String getRegDate() {
        return RegDate;
    }

    public void setRegDate(String regDate) {
        RegDate = regDate;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}