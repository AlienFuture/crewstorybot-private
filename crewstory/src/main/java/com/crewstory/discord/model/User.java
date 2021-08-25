package com.crewstory.discord.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "header_img")
    private String headerImg;
    private String title;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;
    private LocalDateTime date;
    private String author;

    public User() {
    }

    public User(Long id, String headerImg, String title, String content, LocalDateTime date, String author) {
        this.id = id;
        this.headerImg = headerImg;
        this.title = title;
        this.content = content;
        this.date = date;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", headerImg='" + headerImg + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", author='" + author + '\'' +
                '}';
    }
}
