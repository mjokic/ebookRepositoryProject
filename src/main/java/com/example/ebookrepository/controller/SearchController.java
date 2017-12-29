package com.example.ebookrepository.controller;

import com.example.ebookrepository.dto.EbookDto;
import com.example.ebookrepository.dto.SearchDto;
import com.example.ebookrepository.lucene.QueryBuilder;
import com.example.ebookrepository.lucene.ResultRetriever;
import org.apache.lucene.search.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @PostMapping
    public ResponseEntity<?> search(@RequestBody SearchDto searchDto) {
        Query booleanQuery = QueryBuilder.buildQuery(searchDto);

        ResultRetriever rr = new ResultRetriever();
        List<EbookDto> ebooks = rr.printSearchResults(booleanQuery);

        return new ResponseEntity<>(ebooks, HttpStatus.OK);
    }

}
