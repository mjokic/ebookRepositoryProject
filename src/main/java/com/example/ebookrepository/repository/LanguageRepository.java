package com.example.ebookrepository.repository;

import com.example.ebookrepository.model.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Integer> {
}
