package com.echo.backend.controller;

import com.echo.backend.domain.Property;
import com.echo.backend.dto.FileDto;
import com.echo.backend.dto.FileUploadResponse;
import com.echo.backend.dto.UpdatePropertyRequest;
import com.echo.backend.service.UserService;
import com.echo.backend.utils.JWTUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    private final UserService userService;

    @Value( "${server.file.upload.path}" )
    private String uploadPath;

    @Autowired
    public FileController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/upload-document", method = RequestMethod.POST)
    @RequiresAuthentication
    public FileUploadResponse uploadSupportingDocument(MultipartFile file, HttpServletRequest request){
        if (file == null){
            return new FileUploadResponse(500, "File is empty", null);
        }

        int uid = JWTUtil.getUid(request.getHeader("Authorization"), userService);

        String fileDir = uploadPath + "/user/"+uid;
        File path = new File(fileDir);
        if(!path.exists()){
            path.mkdirs();
        }

        File upFile = new File(fileDir + "/" + file.getOriginalFilename());

        try {
            file.transferTo(upFile);
        }catch(IOException e){
            logger.error(e.getMessage());
            return new FileUploadResponse(500, e.getMessage(), null);
        }

        return new FileUploadResponse(200, "Upload success", file.getOriginalFilename());
    }

    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    @RequiresAuthentication
    public List<FileDto> getSupportingDocument(HttpServletRequest request) throws IOException {
        int uid = JWTUtil.getUid(request.getHeader("Authorization"), userService);
        String fileDir = uploadPath + "/user/"+uid;
        List<FileDto> fileDtos = Files.walk(Paths.get(fileDir)).filter(Files::isRegularFile).map(f -> new FileDto(f.getFileName().toString(), String.valueOf(uid))).collect(Collectors.toList());
        return fileDtos;
    }

    @RequestMapping(value = "/document", method = RequestMethod.POST)
    @RequiresAuthentication
    public ResponseEntity<Resource> getSupportingDocument(@RequestBody FileDto fileDto) throws IOException {
        String fileDir = uploadPath + "/user/" + fileDto.getFolder() + "/" + fileDto.getFileName();
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get(fileDir)));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileDto.getFileName());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @RequestMapping(value = "/delete-document", method = RequestMethod.POST)
    @RequiresAuthentication
    public void deleteSupportingDocument(@RequestBody FileDto fileDto) throws IOException {
        String fileDir = uploadPath + "/user/" + fileDto.getFolder() + "/" + fileDto.getFileName();
        Files.delete(Paths.get(fileDir));
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

        String fileDir = uploadPath + "/user/"+uid;
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
        int ran = ra.nextInt(10000);

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
