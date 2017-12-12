package com.example.ebookrepository.dto;

import com.example.ebookrepository.model.Ebook;

public class EbookDto {

    private int id;
    private String title;
    private String author;
    private String keywords;
    private int publicationYear;
    private String mimeType;
    private int languageId;
    private int categoryId;
    private int userId;


    public EbookDto(Ebook ebook){
        this.id = ebook.getId();
        this.title = ebook.getTitle();
        this.author = ebook.getAuthor();
        this.keywords = ebook.getKeywords();
        this.publicationYear = ebook.getPublicationYear();
        this.mimeType = ebook.getMimeType();
        this.languageId = ebook.getLanguage().getId();
        this.categoryId = ebook.getCategory().getId();
        this.userId = ebook.getUser().getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
