package com.example.ebookrepository.dto;

import com.example.ebookrepository.model.Ebook;

public class EbookDto {

    private int id;
    private String title;
    private String author;
    private String keywords;
    private int publicationYear;
    private String mimeType;
//    private int languageId;
//    private int categoryId;
//    private int userId;
    private String languageName;
    private String categoryName;
    private String userName;

    public EbookDto(){}

    public EbookDto(Ebook ebook){
        this.id = ebook.getId();
        this.title = ebook.getTitle();
        this.author = ebook.getAuthor();
        this.keywords = ebook.getKeywords();
        this.publicationYear = ebook.getPublicationYear();
        this.mimeType = ebook.getMimeType();
//        this.languageId = ebook.getLanguage().getId();
//        this.categoryId = ebook.getCategory().getId();
//        this.userId = ebook.getUser().getId();
        this.languageName = ebook.getLanguage().getName();
        this.categoryName = ebook.getCategory().getName();
        this.userName = ebook.getUser().getUsername();
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

//    public int getLanguageId() {
//        return languageId;
//    }
//
//    public void setLanguageId(int languageId) {
//        this.languageId = languageId;
//    }
//
//    public int getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(int categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }


    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
