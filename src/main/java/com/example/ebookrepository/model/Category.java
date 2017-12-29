package com.example.ebookrepository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 30, nullable = false, unique = true)
    private String name;

    @Transient
    @OneToMany(mappedBy = "category")
    private List<Ebook> ebooks = new ArrayList<>();

    @Transient
    @OneToMany(mappedBy = "category")
    private List<User> users = new ArrayList<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
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

    @JsonIgnore
    public List<Ebook> getEbooks() {
        return ebooks;
    }

    public void setEbooks(List<Ebook> ebooks) {
        this.ebooks = ebooks;
    }

    @JsonIgnore
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addEbook(Ebook ebook) {
        this.ebooks.add(ebook);
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
