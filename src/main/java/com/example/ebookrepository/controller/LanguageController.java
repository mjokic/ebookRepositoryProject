package com.example.ebookrepository.controller;

import com.example.ebookrepository.model.Language;
import com.example.ebookrepository.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/language")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService service) {
        this.languageService = service;
    }


    @GetMapping
    public List<Language> getAllLanguages() {
        return languageService.getAllLanguages();
    }


    @PutMapping
    public ResponseEntity<?> addLanguage(@RequestBody Language language){
        languageService.addEditLanguage(language);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
