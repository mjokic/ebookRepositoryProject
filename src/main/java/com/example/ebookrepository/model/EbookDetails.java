package com.example.ebookrepository.model;

public class EbookDetails {

    private String fileName;
    private String author;
    private String title;
    private String keywords;

    public EbookDetails(String fileName, String author, String title, String keywords) {
        this.fileName = fileName;
        this.author = author;
        this.title = title;
        this.keywords = keywords;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
