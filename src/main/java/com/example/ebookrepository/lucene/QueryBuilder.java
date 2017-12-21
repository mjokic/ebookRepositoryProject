package com.example.ebookrepository.lucene;

import com.example.ebookrepository.dto.SearchDto;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;

public class QueryBuilder {

    private static final int FUZZY = 1;
    private static final int PHRAZE = 2;

    public static Query buildQuery(SearchDto searchDto) {
        switch (searchDto.getType()) {
            case FUZZY:
                return buildFuzzyQuery(searchDto);
            case PHRAZE:
                return buildPhraseQuery(searchDto);
            default:
                return buildDefaultQuery(searchDto);
        }
    }

    private static BooleanQuery buildFuzzyQuery(SearchDto searchDto) {
        Term termTitle = new Term("title", searchDto.getTitle().value);
        Query queryTitle = new FuzzyQuery(termTitle);

        Term termAuthor = new Term("author", searchDto.getAuthor().value);
        Query queryAuthor = new FuzzyQuery(termAuthor);

        Term termKeywords = new Term("keywords", searchDto.getKeywords().value);
        Query queryKeywords = new FuzzyQuery(termKeywords);

        Term termContent = new Term("content", searchDto.getContent().value);
        Query queryContent = new FuzzyQuery(termContent);

        Term termLanguage = new Term("language", searchDto.getLanguage().value);
        Query queryLanguage = new FuzzyQuery(termLanguage);

        return buildBooleanQuery(queryTitle, queryAuthor, queryKeywords,
                queryContent, queryLanguage, searchDto);
    }

    private static BooleanQuery buildPhraseQuery(SearchDto searchDto) {
        Term termTitle = new Term("title", searchDto.getTitle().value);
        Query queryTitle = new PhraseQuery.Builder().add(termTitle).build();

        Term termAuthor = new Term("author", searchDto.getAuthor().value);
        Query queryAuthor = new PhraseQuery.Builder().add(termAuthor).build();

        Term termKeywords = new Term("keywords", searchDto.getKeywords().value);
        Query queryKeywords = new PhraseQuery.Builder().add(termKeywords).build();

        Term termContent = new Term("content", searchDto.getContent().value);
        Query queryContent = new PhraseQuery.Builder().add(termContent).build();

        Term termLanguage = new Term("language", searchDto.getLanguage().value);
        Query queryLanguage = new PhraseQuery.Builder().add(termLanguage).build();

        return buildBooleanQuery(queryTitle, queryAuthor, queryKeywords,
                queryContent, queryLanguage, searchDto);
    }

    private static BooleanQuery buildDefaultQuery(SearchDto searchDto) {
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

        return buildBooleanQuery(queryTitle, queryAuthor, queryKeywords,
                queryContent, queryLanguage, searchDto);
    }


    private static BooleanQuery buildBooleanQuery(Query queryTitle,
                                                  Query queryAuthor,
                                                  Query queryKeywords,
                                                  Query queryContent,
                                                  Query queryLanguage,
                                                  SearchDto searchDto) {
        return new BooleanQuery.Builder()
                .add(new BooleanClause(queryTitle, searchDto.getTitle().occur))
                .add(new BooleanClause(queryAuthor, searchDto.getAuthor().occur))
                .add(new BooleanClause(queryKeywords, searchDto.getKeywords().occur))
                .add(new BooleanClause(queryContent, searchDto.getContent().occur))
                .add(new BooleanClause(queryLanguage, searchDto.getLanguage().occur))
                .build();
    }
}
