package com.example.ebookrepository.controller;

import com.example.ebookrepository.dto.EbookDto;
import com.example.ebookrepository.dto.SearchDto;
import com.example.ebookrepository.lucene.QueryBuilder;
import com.example.ebookrepository.lucene.ResultRetriever;
import com.example.ebookrepository.model.Ebook;
import com.example.ebookrepository.service.EbookService;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final EbookService ebookService;

    @Autowired
    public SearchController(EbookService ebookService) {
        this.ebookService = ebookService;
    }

    @PostMapping
    public ResponseEntity<?> search(@RequestBody SearchDto searchDto) {

        Query booleanQuery = QueryBuilder.buildQuery(searchDto);

        ResultRetriever rr = new ResultRetriever(ebookService);
        List<Ebook> ebooks = rr.printSearchResults(booleanQuery);

        return new ResponseEntity<>(ebooks.stream()
                .map(EbookDto::new)
                .collect(Collectors.toList())
                , HttpStatus.OK);
    }

}
