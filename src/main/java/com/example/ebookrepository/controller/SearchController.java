package com.example.ebookrepository.controller;

import com.example.ebookrepository.lucene.ResultRetriever;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @GetMapping
    public ResponseEntity<?> search(@RequestParam("term") String term) {
        Term t = new Term("title", term);
        Query query = new TermQuery(t);
        Query query1 = new FuzzyQuery(t);
        ResultRetriever rr = new ResultRetriever();
        rr.printSearchResults(query);
        return null;
    }

}
