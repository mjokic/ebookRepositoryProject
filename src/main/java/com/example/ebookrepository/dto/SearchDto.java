package com.example.ebookrepository.dto;

import org.apache.lucene.search.BooleanClause;

public class SearchDto {

    private Title title;
    private Author author;
    private Keywords keywords;
    private Content content;
    private Language language;

    public SearchDto(){}

    public Title getTitle() {
        if (title.searchType){
            title.occur = BooleanClause.Occur.MUST;
        }else {
            title.occur = BooleanClause.Occur.SHOULD;
        }
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Author getAuthor() {
        if (author.searchType){
            author.occur = BooleanClause.Occur.MUST;
        }else {
            author.occur = BooleanClause.Occur.SHOULD;
        }
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Keywords getKeywords() {
        if (keywords.searchType){
            keywords.occur = BooleanClause.Occur.MUST;
        }else {
            keywords.occur = BooleanClause.Occur.SHOULD;
        }
        return keywords;
    }

    public void setKeywords(Keywords keywords) {
        this.keywords = keywords;
    }

    public Content getContent() {
        if (content.searchType){
            content.occur = BooleanClause.Occur.MUST;
        }else {
            content.occur = BooleanClause.Occur.SHOULD;
        }
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Language getLanguage() {
        if (language.searchType){
            language.occur = BooleanClause.Occur.MUST;
        }else {
            language.occur = BooleanClause.Occur.SHOULD;
        }
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public class Title {
        public String value;
        public boolean searchType;
        public BooleanClause.Occur occur;
    }

    public class Author {
        public String value;
        public boolean searchType;
        public BooleanClause.Occur occur;
    }

    public class Keywords {
        public String value;
        public boolean searchType;
        public BooleanClause.Occur occur;
    }

    public class Content {
        public String value;
        public boolean searchType;
        public BooleanClause.Occur occur;
    }

    public class Language {
        public String value;
        public boolean searchType;
        public BooleanClause.Occur occur;
    }

}
