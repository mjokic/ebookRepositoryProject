package com.example.ebookrepository.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 30, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Ebook> ebooks;

    @OneToMany(mappedBy = "category")
    private List<User> users;

    public Category() {
    }

    public Category(String name, List<Ebook> ebooks, List<User> users) {
        this.name = name;
        this.ebooks = ebooks;
        this.users = users;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
