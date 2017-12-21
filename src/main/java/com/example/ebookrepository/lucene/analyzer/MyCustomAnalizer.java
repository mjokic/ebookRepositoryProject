package com.example.ebookrepository.lucene.analyzer;

import com.example.ebookrepository.lucene.filters.CyrilicToLatinFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class MyCustomAnalizer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer tokenizer = new StandardTokenizer();
        TokenStream tokenStream = new CyrilicToLatinFilter(tokenizer);
        tokenStream = new LowerCaseFilter(tokenStream);

        return new TokenStreamComponents(tokenizer, tokenStream);
    }
}
