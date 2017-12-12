package com.example.ebookrepository.repository;

import com.example.ebookrepository.model.Ebook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EbookRepository extends CrudRepository<Ebook, Integer> {

    List<Ebook> findAllByCategoryId(int id);

}
