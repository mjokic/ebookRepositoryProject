package com.example.ebookrepository.dto;

import com.example.ebookrepository.model.Category;
import com.example.ebookrepository.model.Ebook;
import com.example.ebookrepository.model.Language;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class EbookDto {

    private int id;
    private String title;
    private String author;
    private String keywords;
    private int publicationYear;
    private String mimeType;
    private Language language;
    private Category category;
    private int userId;
    private String fileName;
    private boolean downloadable;

    public EbookDto() {
    }

    public EbookDto(int id, String title, String author, int publicationYear, Category category){
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.category = category;
    }

    public EbookDto(Ebook ebook) {
        this.id = ebook.getId();
        this.title = ebook.getTitle();
        this.author = ebook.getAuthor();
        this.keywords = ebook.getKeywords();
        this.publicationYear = ebook.getPublicationYear();
        this.mimeType = ebook.getMimeType();
        this.language = ebook.getLanguage();
        this.category = ebook.getCategory();
        this.userId = ebook.getUser().getId();
        this.fileName = ebook.getFileName();
        this.downloadable = true;
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @JsonIgnore
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isDownloadable() {
        return downloadable;
    }

    public void setDownloadable(boolean downloadable) {
        this.downloadable = downloadable;
    }
}
