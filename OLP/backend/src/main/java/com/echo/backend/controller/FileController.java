package com.echo.backend.controller;

import com.echo.backend.domain.Property;
import com.echo.backend.dto.FileUploadResponse;
import com.echo.backend.dto.UpdatePropertyRequest;
import com.echo.backend.service.UserService;
import com.echo.backend.utils.JWTUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    private final UserService userService;

    @Autowired
    public FileController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    @RequiresAuthentication
    public FileUploadResponse uploadAvatar(MultipartFile file, HttpServletRequest request){

        if (null == file){
            return new FileUploadResponse(500, "File is empty", null);
        }

        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名

        int uid = JWTUtil.getUid(request.getHeader("Authorization"), userService);

        String fileDir = "/home/ubuntu/tomcat/apache-tomcat/webapps/resources/user/"+uid;
        File path = new File(fileDir);
        if(!path.exists()){
            path.mkdirs();
        }

        String retFilePath = "/resources/user/" + uid + "/avatar" + suffixName;
        File upFile = new File(fileDir + "/avatar" + suffixName);

        try {
            file.transferTo(upFile);
        }catch(IOException e){
            logger.error(e.getMessage());
            return new FileUploadResponse(500, e.getMessage(), null);
        }

        return new FileUploadResponse(200, "Upload success", retFilePath);
    }

    @RequestMapping(value = "/uploadPropertyPic", method = RequestMethod.POST)
    @RequiresAuthentication
    public FileUploadResponse uploadPropertyPic(MultipartFile file, HttpServletRequest request, @RequestBody UpdatePropertyRequest propertyRequest){

        if (null == file){
            return new FileUploadResponse(500, "File is empty", null);
        }

        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名

        Property property = propertyRequest.getProperty();
        int pid = property.getPid();


        String fileDir = "/home/ubuntu/tomcat/apache-tomcat/webapps/resources/property/"+pid;
        File path = new File(fileDir);
        if(!path.exists()){
            path.mkdirs();
        }

        Random ra =new Random();
        int ran = ra.nextInt(1000);

        File upFile = new File(fileDir + "/property_" + ran + suffixName);

        String retFilePath = "/resources/property/" + pid + "/property_" + ran + suffixName;

        try {
            file.transferTo(upFile);
        }catch(IOException e){
            logger.error(e.getMessage());
            return new FileUploadResponse(500, e.getMessage(), null);
        }

        return new FileUploadResponse(200, "Upload success", retFilePath);
    }

}
