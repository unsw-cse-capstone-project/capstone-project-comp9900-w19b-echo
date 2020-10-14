package com.echo.backend.controller;


import com.echo.backend.domain.User;
import com.echo.backend.dto.*;
import com.echo.backend.exception.UnauthorizedException;
import com.echo.backend.service.UserService;
import com.echo.backend.utils.JWTUtil;
import org.apache.shiro.authz.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public SignInResponse login(@RequestBody SignInRequest request) {
        //Login by email only
        User user = userService.getUserByEmail(request.getEmail());
        if(null == user){
            throw new UnauthorizedException();
        }

        if (user.getPassword().equals(request.getPassword())) {
            return new SignInResponse(200, "Login success", JWTUtil.sign(user.getEmail(), user.getUserName(), request.getPassword()));
        } else {
            throw new UnauthorizedException();
        }
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public SignUpResponse register(@RequestBody SignUpRequest request) {
        User temp = userService.getUserByName(request.getFullName());
        if (temp != null){
            return new SignUpResponse(401, "Username exist.", null);
        }

        temp = userService.getUserByEmail(request.getEmail());
        if (temp != null){
            return new SignUpResponse(401, "Email exist.", null);
        }

        User user = new User();
        user.setRole("user");
        user.setRegisterTime(new Date());
        user.setUserName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        userService.addNewUser(user);
        return new SignUpResponse(200, "Login success", JWTUtil.sign(user.getEmail(), user.getUserName(), user.getPassword()));
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void updateUser(@RequestBody ProfileRequest request) {
        User user = userService.getUserByEmail(request.getEmail());
        user.setUserName(request.getFullName());
        user.setPhone(request.getPhone());
        userService.updateUserInfo(user);
    }

    @RequestMapping(value = "/sign-out", method = RequestMethod.POST)
    public void logout() {

    }

    @RequestMapping(value = "/listAllUser", method = RequestMethod.POST)
    @RequiresAuthentication
    public List<User> listAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public SignInResponse requireAuth() {
        return new SignInResponse(200, "You are authenticated", null);
    }
    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public SignInResponse requireRole() {
        return new SignInResponse(200, "You are visiting require_role", null);
    }
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SignInResponse unauthorized() {
        return new SignInResponse(401, "Unauthorized", null);
    }

}