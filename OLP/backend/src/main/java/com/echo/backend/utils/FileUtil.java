package com.echo.backend.utils;

import com.echo.backend.domain.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    static public List<Property> generatePropertyPic(List<Property> properties) {

        String prefix = "/resources/property/";
        for (Property pro : properties) {
            try {
                File file = new File(prefix + pro.getPid());
                File[] files = file.listFiles();
                List<String> picUrl = new ArrayList<>();
                for (File f : files) {
                    picUrl.add(prefix + f.getName());
                }
                pro.setPicUrl(picUrl);
            } catch (Exception ignored) {
            }

        }

        return properties;
    }
}
