package com.example.ebookrepository.service;

import com.example.ebookrepository.model.Language;
import com.example.ebookrepository.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> getAllLanguages() {
        return (List<Language>) languageRepository.findAll();
    }

    public Language getLanguageById(int languageId) {
        return languageRepository.findOne(languageId);
    }

    public void addEditLanguage(Language language) {
        languageRepository.save(language);
    }

    public void deleteLanguage(int languageId) {
        languageRepository.delete(languageId);
    }
}
