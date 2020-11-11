package com.echo.backend;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

public class BasicTest {
    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        File path = new File("C:\\index");
        Directory dir = new SimpleFSDirectory(path);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
        IndexWriter writer = new IndexWriter(dir, config);
        writer.deleteAll();

        String add = "85 Barker St,Kingsford 2032, New South Wales";
        Document doc = new Document();
        doc.add(new Field("id", "1", TextField.TYPE_STORED));
        doc.add(new Field("address", add, TextField.TYPE_STORED));

        writer.addDocument(doc);
        writer.commit();
        writer.close();


    }
}
