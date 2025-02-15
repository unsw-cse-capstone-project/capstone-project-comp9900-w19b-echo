package com.echo.backend;

import com.echo.backend.utils.GoogleMapUtil;
import com.echo.backend.utils.MailUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    MailUtil mailUtil;

    @Autowired
    Directory ramDirectory;

    @Test
    void sendMail() {
        mailUtil.sendSimpleMail("450015317@qq.com", "Test email2 - subject", "Test email- content");
        mailUtil.sendSimpleMail("cxy_nuaa2012@hotmail.com", "Test email2 - subject", "Test email- content");
    }

    @Test
    void queryLucene() throws IOException, ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        DirectoryReader reader = DirectoryReader.open(ramDirectory);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser parser = new QueryParser("address", analyzer);
        Query query = parser.parse("George NSW");
        TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);
        searcher.search(query, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("pid") + "\t" + d.get("address") );
        }
        // reader can only be closed when there
        // is no need to access the documents any more.
        reader.close();
    }

}
