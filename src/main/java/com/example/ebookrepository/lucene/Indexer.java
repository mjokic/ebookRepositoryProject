package com.example.ebookrepository.lucene;


import com.example.ebookrepository.model.Ebook;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

import static com.example.ebookrepository.lucene.Constants.luceneDir;

public class Indexer {

    public static void addFileToIndex(Ebook ebook) throws IOException {

        File file = new File("src/main/resources/files/" + ebook.getFileName());
        Directory directory = new SimpleFSDirectory(luceneDir);


        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        try (IndexWriter indexWriter = new IndexWriter(directory, config)) {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            PDFTextStripper textStripper = new PDFTextStripper();
            String content = textStripper.getText(parser.getPDDocument());

            Document document = new Document();
            document.add(new StringField("id", String.valueOf(ebook.getId()), Field.Store.YES));
            document.add(new TextField("title", ebook.getTitle(), Field.Store.YES));
            document.add(new TextField("author", ebook.getAuthor(), Field.Store.YES));
            document.add(new TextField("content", content, Field.Store.YES));
            document.add(new TextField("keywords", ebook.getKeywords(), Field.Store.YES));
            document.add(new TextField("languageId",
                    String.valueOf(ebook.getLanguage().getId()), Field.Store.YES));

            indexWriter.addDocument(document);
        }
    }

    public static void deleteFileFromIndex(int id) throws IOException {
        Directory directory = new SimpleFSDirectory(luceneDir);

        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        IndexWriter indexWriter = new IndexWriter(directory, config);
        Term term = new Term("id", String.valueOf(id));
        Query query = new TermQuery(term);
        indexWriter.deleteDocuments(query);
        indexWriter.close();
    }

}
