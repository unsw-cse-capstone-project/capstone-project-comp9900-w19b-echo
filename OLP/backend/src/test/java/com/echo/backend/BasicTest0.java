package com.echo.backend;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
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

public class BasicTest0 {
    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer1 = new StopAnalyzer();

        String add = "85 Barker St,Kingsford 2032, New South Wales";

        TokenStream ts = analyzer.tokenStream("address", add);
        ts.reset();
        CharTermAttribute cta = ts.getAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {
            System.out.print(cta.toString() + "|");
        }
        System.out.println();
        ts.end();
        ts.close();

        TokenStream ts1 = analyzer1.tokenStream("address", add);
        ts1.reset();
        CharTermAttribute cta1 = ts1.getAttribute(CharTermAttribute.class);
        while (ts1.incrementToken()) {
            System.out.print(cta1.toString() + "|");
        }
        System.out.println();
        ts1.end();
        ts1.close();
    }
}
