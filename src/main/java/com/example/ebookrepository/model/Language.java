package com.example.ebookrepository.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 30, nullable = false)
    private String name;

    @OneToMany(mappedBy = "language")
    private List<Ebook> ebooks;

    public Language(String name, List<Ebook> ebooks) {
        this.name = name;
        this.ebooks = ebooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ebook> getEbooks() {
        return ebooks;
    }

    public void setEbooks(List<Ebook> ebooks) {
        this.ebooks = ebooks;
    }
}
