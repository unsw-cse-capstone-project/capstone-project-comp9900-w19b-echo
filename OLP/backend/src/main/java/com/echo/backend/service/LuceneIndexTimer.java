package com.echo.backend.service;

import com.echo.backend.dao.*;
import com.echo.backend.domain.*;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class LuceneIndexTimer {

    private Logger logger = LoggerFactory.getLogger(LuceneIndexTimer.class);

    private final PropertyMapper propertyMapper;

    private final Directory ramDirectory;

    @Autowired
    public LuceneIndexTimer(PropertyMapper propertyMapper, Directory ramDirectory) {
        this.propertyMapper = propertyMapper;
        this.ramDirectory = ramDirectory;
    }


    // 每天凌晨3点，重新生成索引
    @Scheduled(cron = "0 0 3 ? * *")
    public void generateIndex() {
        try {

            List<Property> properties = propertyMapper.getAllProperty();

            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, new StopAnalyzer());

            IndexWriter writer = new IndexWriter(ramDirectory, config);
            writer.deleteAll();
            properties.forEach(t->{
                Document doc = new Document();
                doc.add(new Field("pid", String.valueOf(t.getPid()), TextField.TYPE_STORED));
                doc.add(new Field("address", t.getAddress(), TextField.TYPE_STORED));
                try {
                    writer.addDocument(doc);
                } catch (IOException ignored) {}
            });

            writer.commit();
            writer.close();

        }
        catch (IOException ignored){}
    }

}
