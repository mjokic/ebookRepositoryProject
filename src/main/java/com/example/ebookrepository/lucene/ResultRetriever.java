package com.example.ebookrepository.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.IOException;

import static com.example.ebookrepository.lucene.Constants.luceneDir;

public class ResultRetriever {

    private TopScoreDocCollector collector;

    public ResultRetriever() {
        collector = TopScoreDocCollector.create(10);
    }

    public void printSearchResults(Query query) {
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
                System.out.println("\t" + doc.get("title"));
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

}
