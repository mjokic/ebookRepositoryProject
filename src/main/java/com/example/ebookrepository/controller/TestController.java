package com.example.ebookrepository.controller;

import com.example.ebookrepository.model.Ebook;
import com.example.ebookrepository.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    private final EbookService ebookService;

    @Autowired
    public TestController(EbookService ebookService) {
        this.ebookService = ebookService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public Ebook test() {
        return ebookService.getEbookById(1);
    }

}
