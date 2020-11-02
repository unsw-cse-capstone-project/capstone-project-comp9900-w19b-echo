package com.echo.backend.controller;

import com.echo.backend.dto.FileUploadResponse;
import com.echo.backend.service.UserService;
import com.echo.backend.utils.JWTUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

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

        int uid = JWTUtil.getUid(request.getHeader("Authorization"), userService);

        String fileDir = "/home/ubuntu/tomcat/apache-tomcat/webapps/resources/user/"+uid;
        File path = new File(fileDir);
        if(!path.exists()){
            path.mkdirs();
        }

        File upFile = new File(fileDir + "/avatar");

        try {
            file.transferTo(upFile);
        }catch(IOException e){
            logger.error(e.getMessage());
            return new FileUploadResponse(500, e.getMessage(), null);
        }

        return new FileUploadResponse(200, "Upload success", null);
    }

    @RequestMapping(value = "/uploadPropertyPic", method = RequestMethod.POST)
    @RequiresAuthentication
    public FileUploadResponse uploadPropertyPic(MultipartFile file, HttpServletRequest request){

        if (null == file){
            return new FileUploadResponse(500, "File is empty", null);
        }

        int uid = JWTUtil.getUid(request.getHeader("Authorization"), userService);

        String fileDir = "/home/ubuntu/tomcat/apache-tomcat/webapps/resources/property/"+uid;
        File path = new File(fileDir);
        if(!path.exists()){
            path.mkdirs();
        }

        File upFile = new File(fileDir + "/propertyPic");

        try {
            file.transferTo(upFile);
        }catch(IOException e){
            logger.error(e.getMessage());
            return new FileUploadResponse(500, e.getMessage(), null);
        }

        return new FileUploadResponse(200, "Upload success", null);
    }

}
