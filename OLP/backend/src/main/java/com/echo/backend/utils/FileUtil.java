package com.echo.backend.utils;

import com.echo.backend.domain.Property;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FileUtil {

    static public List<Property> generatePropertyPic(List<Property> properties, String uploadPath, String accessPath) {
        for (Property pro : properties) {
            try {
                File file = new File(uploadPath + "/property/" + pro.getPid() + "/photo");
                File[] files = file.listFiles();
                Arrays.sort(files);
                List<String> picUrl = new ArrayList<>();
                for (File f : files) {
                    picUrl.add(accessPath + pro.getPid() + "/photo/" + f.getName());
                }
                pro.setPicUrl(picUrl);
            } catch (Exception ignored) {
            }

        }

        return properties;
    }
}
