package com.example.ebookrepository.controller;

import com.example.ebookrepository.dto.EbookDto;
import com.example.ebookrepository.model.*;
import com.example.ebookrepository.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/ebook")
public class BooksController {

    private final EbookService ebookService;
    private final CategoryService categoryService;
    private final LanguageService languageService;
    private final UserService userService;
    private final StorageService storageService;

    @Autowired
    public BooksController(EbookService service, CategoryService categoryService,
                           LanguageService languageService, UserService userService,
                           StorageService storageService) {
        this.ebookService = service;
        this.categoryService = categoryService;
        this.languageService = languageService;
        this.userService = userService;
        this.storageService = storageService;
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
    public ResponseEntity<?> getEbookById(@PathVariable int id, Principal principal) {

        Ebook ebook = ebookService.getEbookById(id);
        if (ebook == null) {
            Status status = new Status(false, "Ebook doesn't exist!");
            return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
        }

        boolean downloadable = isEbookDownloadable(principal, ebook);

        EbookDto ebookDto = new EbookDto(ebook);
        ebookDto.setDownloadable(downloadable);

        return new ResponseEntity<>(ebookDto, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getEbooksByCategoryId(@PathVariable int id) {
        List<Ebook> ebooks = ebookService.getAllEbooksByCategory(id);
        return new ResponseEntity<>(
                ebooks.stream().map(EbookDto::new).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    // fix this
    @PutMapping
    public ResponseEntity<?> addEbook(@RequestBody EbookDto ebook) {
        Ebook e = new Ebook();
        e.setTitle(ebook.getTitle());
        e.setAuthor(ebook.getAuthor());
        e.setKeywords(ebook.getKeywords());
        e.setPublicationYear(ebook.getPublicationYear());
        e.setFileName(ebook.getFileName());
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
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        return storageService.store(file);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<?> download(@PathVariable("id") int id) {
        Ebook ebook = ebookService.getEbookById(id);
        try {
            return storageService.getFile(ebook);
        } catch (IOException e) {
            return new ResponseEntity<>(
                    new Status(false, "Error!"),
                    HttpStatus.BAD_REQUEST);
        }
    }


    private boolean isEbookDownloadable(Principal principal, Ebook ebook){
        if (principal == null){
            return false;
        }

        User user = userService.getUserByUsername(principal.getName());
        if (user == null){
            return false;
        }

        if (user.getCategory() != null &&
                user.getCategory().getId() != ebook.getCategory().getId()){
            return false;
        }

        return true;
    }

}
