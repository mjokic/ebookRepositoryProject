package com.example.ebookrepository.controller;

import com.example.ebookrepository.dto.EbookDto;
import com.example.ebookrepository.model.Ebook;
import com.example.ebookrepository.model.Status;
import com.example.ebookrepository.service.EbookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/ebook")
public class BooksController {

    private final EbookService ebookService;

    public BooksController(EbookService service) {
        this.ebookService = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        List<Ebook> ebooks = ebookService.getAllEbooks();
        if (ebooks == null || ebooks.size() == 0){
            Status status = new Status(false, "There aren't any ebooks!");
            return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ebooks.stream().map(EbookDto::new).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEbookById(@PathVariable int id){
        Ebook ebook = ebookService.getEbookById(id);
        if (ebook == null){
            Status status = new Status(false, "Ebook doesn't exist!");
            return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new EbookDto(ebook), HttpStatus.OK);
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
