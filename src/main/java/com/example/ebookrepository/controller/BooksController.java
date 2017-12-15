package com.example.ebookrepository.controller;

import com.example.ebookrepository.dto.EbookDto;
import com.example.ebookrepository.dto.EbookDto2;
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

    // TODO: 12/14/17 Fix adding ebook
    @PutMapping
    public ResponseEntity<?> addEbook(@RequestBody EbookDto2 ebookDto, Principal principal) {
        Ebook ebook = new Ebook();
        ebook.setTitle(ebookDto.getTitle());
        ebook.setAuthor(ebookDto.getAuthor());
        ebook.setKeywords(ebookDto.getKeywords());
        ebook.setPublicationYear(ebookDto.getPublicationYear());
        ebook.setFileName(ebookDto.getFileName());
        ebook.setMimeType(ebookDto.getMimeType());

        Category category = categoryService.getCategoryById(ebookDto.getCategoryId());
        Language language = languageService.getLanguageById(ebookDto.getLanguageId());
        User user = userService.getUserByUsername(principal.getName());

        ebook.setCategory(category);
        ebook.setLanguage(language);
        ebook.setUser(user);

        ebookService.addEditEbook(ebook);
        return new ResponseEntity<>(
                new Status(true, "Ebook added successfully!"),
                HttpStatus.OK);
    }

    // TODO: 12/14/17 Edit ebook
    @PostMapping
    public ResponseEntity<?> editEbook(@RequestBody EbookDto ebook){
        Ebook e = new Ebook();
        e.setId(ebook.getId());
        e.setTitle(ebook.getTitle());
        e.setAuthor(ebook.getAuthor());
        e.setKeywords(ebook.getKeywords());
        e.setPublicationYear(ebook.getPublicationYear());

        ebookService.addEditEbook(e);
        return new ResponseEntity<>(
                new Status(true, "Ebook successfully edited!"),
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
