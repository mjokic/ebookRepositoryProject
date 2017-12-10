package com.example.ebookrepository.repository;

import com.example.ebookrepository.model.Ebook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EbookRepository extends CrudRepository<Ebook, Integer> {
}
