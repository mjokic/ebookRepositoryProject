package com.example.ebookrepository.controller;

import com.example.ebookrepository.model.Language;
import com.example.ebookrepository.model.Status;
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

    @GetMapping("/{id}")
    public Language getLanguageById(@PathVariable("id") int id){
        return languageService.getLanguageById(id);
    }

    @PutMapping
    public ResponseEntity<?> addLanguage(@RequestBody Language language){
        Language newLanguage = new Language();
        newLanguage.setName(language.getName());

        languageService.addEditLanguage(newLanguage);
        return new ResponseEntity<>(
                new Status(true, "Language successfully added!"),
                HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> editLanguage(@RequestBody Language language){
        languageService.addEditLanguage(language);
        return new ResponseEntity<>(
                new Status(true, "Language successfully edited!"),
                HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLanguage(@PathVariable("id") int id){
        languageService.deleteLanguage(id);
        return new ResponseEntity<>(
                new Status(true, "Language successfully deleted!"),
                HttpStatus.OK);
    }

}
