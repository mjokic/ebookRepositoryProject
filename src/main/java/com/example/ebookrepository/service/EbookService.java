package com.example.ebookrepository.service;

import com.example.ebookrepository.model.Ebook;
import com.example.ebookrepository.repository.EbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EbookService {

    private final EbookRepository ebookRepository;

    @Autowired
    public EbookService(EbookRepository ebookRepository) {
        this.ebookRepository = ebookRepository;
    }

    public List<Ebook> getAllEbooks() {
        return (List<Ebook>) ebookRepository.findAll();
    }

    public List<Ebook> getAllEbooksByCategory(int id){
        return ebookRepository.findAllByCategoryId(id);
    }

    public Ebook getEbookById(int ebookId) {
        return ebookRepository.findOne(ebookId);
    }

    public void addEditEbook(Ebook ebook) {
        ebookRepository.save(ebook);
    }

    public void deleteEbook(int ebookId) {
        ebookRepository.delete(ebookId);
    }
}
