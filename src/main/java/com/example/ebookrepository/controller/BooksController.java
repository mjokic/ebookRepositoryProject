package com.example.ebookrepository.controller;

import com.example.ebookrepository.dto.EbookDto;
import com.example.ebookrepository.model.*;
import com.example.ebookrepository.service.CategoryService;
import com.example.ebookrepository.service.EbookService;
import com.example.ebookrepository.service.LanguageService;
import com.example.ebookrepository.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/ebook")
public class BooksController {

    private final EbookService ebookService;
    private final CategoryService categoryService;
    private final LanguageService languageService;
    private final UserService userService;

    public BooksController(EbookService service, CategoryService categoryService,
                           LanguageService languageService, UserService userService) {
        this.ebookService = service;
        this.categoryService = categoryService;
        this.languageService = languageService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        List<Ebook> ebooks = ebookService.getAllEbooks();
        if (ebooks == null || ebooks.size() == 0) {
            Status status = new Status(false, "There aren't any ebooks!");
            return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ebooks.stream().map(EbookDto::new).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEbookById(@PathVariable int id) {
        Ebook ebook = ebookService.getEbookById(id);
        if (ebook == null) {
            Status status = new Status(false, "Ebook doesn't exist!");
            return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new EbookDto(ebook), HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<?> addEbook(@RequestBody EbookDto ebook) {
        Ebook e = new Ebook();
        e.setTitle(ebook.getTitle());
        e.setAuthor(ebook.getAuthor());
        e.setKeywords(ebook.getKeywords());
        e.setPublicationYear(ebook.getPublicationYear());
        e.setFileName("fajil.pdf");
        e.setMimeType(ebook.getMimeType());

        Category category = categoryService.getCategoryById(2);
        Language language = languageService.getLanguageById(1);
        User user = userService.getUserById(1);

        e.setCategory(category);
        e.setLanguage(language);
        e.setUser(user);

        ebookService.addEditEbook(e);
        return new ResponseEntity<>(
                new Status(true, "Ebook added successfully!"),
                HttpStatus.OK);
    }


    @PutMapping("/upload")
    public ResponseEntity<Status> upload() {
        Status status = new Status(true, "yey");
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<Status> download() {
        Status status = new Status(true, "yey");
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
