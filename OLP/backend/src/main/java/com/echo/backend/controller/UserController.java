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
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
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
        User user;
        if (request.getUserName().contains("@")){
            user = userService.getUserByEmail(request.getUserName());
        }
        else {
            user = userService.getUserByName(request.getUserName());
        }
        if(null == user){
            throw new UnauthorizedException();
        }

        if (user.getPassword().equals(request.getPassword())) {
            return new SignInResponse(200, "Login success", JWTUtil.sign(request.getUserName(), request.getPassword()));
        } else {
            throw new UnauthorizedException();
        }
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public SignUpResponse register(@RequestBody SignUpRequest request) {

        User user = request.getUser();

        User temp = userService.getUserByName(user.getUserName());
        if (temp != null){
            return new SignUpResponse(401, "Username exist.", null);
        }

        temp = userService.getUserByEmail(user.getEmail());
        if (temp != null){
            return new SignUpResponse(401, "Email exist.", null);
        }

        user.setRole("user");
        user.setRegisterTime(new Date());
        userService.addNewUser(user);
        return new SignUpResponse(200, "Login success", JWTUtil.sign(user.getUserName(), user.getPassword()));
    }

    @RequestMapping(value = "/update-info", method = RequestMethod.POST)
    @RequiresAuthentication
    public UpdateInfoResponse update(UpdateInfoRequest request, HttpServletRequest hRequest){

        String userName = JWTUtil.getUsername(hRequest.getHeader("Authorization"));
        User user = request.getUser();
        User exist = userService.getUserByName(userName);

        if (null != user.getUserName()){
            exist.setUserName(user.getUserName());
        }

        if (null != user.getEmail()){
            exist.setEmail(user.getEmail());
        }

        if (null != user.getPhone()){
            exist.setPhone(user.getPhone());
        }

        userService.updateUserInfo(exist);
        return new UpdateInfoResponse(200, "Update success", null);
    }

    @RequestMapping(value = "/update-password", method = RequestMethod.POST)
    @RequiresAuthentication
    public UpdatePasswordResponse update(UpdatePasswordRequest request, HttpServletRequest hRequest){

        String userName = JWTUtil.getUsername(hRequest.getHeader("Authorization"));
        User user = request.getUser();
        User exist = userService.getUserByName(userName);

        if (null != user.getPassword() ){
            exist.setPassword(user.getPassword());
        }

        userService.updateUserPassword(exist);
        return new UpdatePasswordResponse(200, "Update password success", null);
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