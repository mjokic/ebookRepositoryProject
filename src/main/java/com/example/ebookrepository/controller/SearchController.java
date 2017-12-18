package com.example.ebookrepository.controller;

import com.example.ebookrepository.dto.EbookDto;
import com.example.ebookrepository.dto.SearchDto;
import com.example.ebookrepository.lucene.ResultRetriever;
import com.example.ebookrepository.model.Ebook;
import com.example.ebookrepository.service.EbookService;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final EbookService ebookService;

    @Autowired
    public SearchController(EbookService ebookService){
        this.ebookService = ebookService;
    }

    @PostMapping
    public ResponseEntity<?> search1(@RequestBody SearchDto searchDto){
        Term termTitle = new Term("title", searchDto.getTitle().value);
        Query queryTitle = new TermQuery(termTitle);

        Term termAuthor = new Term("author", searchDto.getAuthor().value);
        Query queryAuthor = new TermQuery(termAuthor);

        Term termKeywords = new Term("keywords", searchDto.getKeywords().value);
        Query queryKeywords = new TermQuery(termKeywords);

        Term termContent = new Term("content", searchDto.getContent().value);
        Query queryContent = new TermQuery(termContent);

        Term termLanguage = new Term("language", searchDto.getLanguage().value);
        Query queryLanguage = new TermQuery(termLanguage);

        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(new BooleanClause(queryTitle, searchDto.getTitle().occur))
                .add(new BooleanClause(queryAuthor, searchDto.getAuthor().occur))
                .add(new BooleanClause(queryKeywords, searchDto.getKeywords().occur))
                .add(new BooleanClause(queryContent, searchDto.getContent().occur))
                .add(new BooleanClause(queryLanguage, searchDto.getLanguage().occur))
                .build();

        ResultRetriever rr = new ResultRetriever(ebookService);
        List<Ebook> ebooks = rr.printSearchResults(booleanQuery);

        return new ResponseEntity<>(ebooks.stream()
                .map(EbookDto::new)
                .collect(Collectors.toList())
                , HttpStatus.OK);
    }

}
