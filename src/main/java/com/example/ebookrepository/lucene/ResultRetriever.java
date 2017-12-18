package com.example.ebookrepository.lucene;

import com.example.ebookrepository.model.Ebook;
import com.example.ebookrepository.service.EbookService;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.ebookrepository.lucene.Constants.luceneDir;

public class ResultRetriever {

    private TopScoreDocCollector collector;
    private final EbookService ebookService;

    public ResultRetriever(EbookService ebookService) {
        collector = TopScoreDocCollector.create(10);
        this.ebookService = ebookService;
    }

    public List<Ebook> printSearchResults(Query query) {
        List<Ebook> ebooks = new ArrayList<>();

        try {
            Directory fsDir = new SimpleFSDirectory(luceneDir);
            DirectoryReader reader = DirectoryReader.open(fsDir);
            IndexSearcher is = new IndexSearcher(reader);
            is.search(query, collector);

            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            System.err.println("Found " + hits.length +
                    " document(s) that matched query '" + query + "':");
            for (int i = 0; i < collector.getTotalHits(); i++) {
                int docId = hits[i].doc;
                Document doc = is.doc(docId);
                ebooks.add(ebookService.getEbookById(Integer.valueOf(doc.get("id"))));
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        return ebooks;
    }

}
