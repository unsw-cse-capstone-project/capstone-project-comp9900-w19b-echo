package com.echo.backend.controller;

import com.echo.backend.service.UserService;
import com.echo.backend.utils.JWTUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class FileController {

    private final UserService userService;

    @Autowired
    public FileController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    @RequiresAuthentication
    public void test(MultipartFile file, HttpServletRequest request){

        String token = request.getHeader("Authorization");
        String userName = JWTUtil.getUsername(token);
        int uid;

        if (userName.contains("@")){
            uid = userService.getUserByEmail(userName).getUid();
        }
        else {
            uid = userService.getUserByName(userName).getUid();
        }

        String fileDir = "/home/ubuntu/tomcat/apache-tomcat/webapps/resources/user/"+uid;
        File path = new File(fileDir);
        if(!path.exists()){
            path.mkdirs();
        }

        File upFile = new File(path + "/avatar");

        try {
            file.transferTo(upFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
