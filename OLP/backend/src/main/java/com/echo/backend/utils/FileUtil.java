package com.echo.backend.utils;

import com.echo.backend.domain.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    static public List<Property> generatePropertyPic(List<Property> properties) {

        String pathPrefic = "/home/ubuntu/tomcat/apache-tomcat/webapps/resources/property/";
        String retPrefix = "/resources/property/";
        for (Property pro : properties) {
            try {
                File file = new File(pathPrefic + pro.getPid());
                File[] files = file.listFiles();
                List<String> picUrl = new ArrayList<>();
                for (File f : files) {
                    picUrl.add(retPrefix + pro.getPid() + "/" + f.getName());
                }
                pro.setPicUrl(picUrl);
            } catch (Exception ignored) {
            }

        }

        return properties;
    }
}
