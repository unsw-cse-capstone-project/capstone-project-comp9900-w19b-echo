package com.echo.backend.controller;


import com.echo.backend.domain.User;
import com.echo.backend.dto.*;
import com.echo.backend.exception.UnauthorizedException;
import com.echo.backend.service.UserService;
import com.echo.backend.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = "User management apis")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value="User login", notes="Login with email")
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

    @ApiOperation(value="User register", notes="Sign up with email")
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public SignUpResponse register(@RequestBody SignUpRequest request) {
        User temp = userService.getUserByName(request.getFullName());
//        if (temp != null){
//            return new SignUpResponse(401, "Username exist.", null);
//        }

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
        user.setPhone(request.getPhoneNumber());

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
    @ApiIgnore
    public List<User> listAllUser(){
        return userService.getAllUser();
    }

    @RequestMapping(value = "/update-info", method = RequestMethod.POST)
    @RequiresAuthentication
    public UpdateInfoResponse update(@RequestBody UpdateInfoRequest request, HttpServletRequest hRequest){

        String email = JWTUtil.getEmail(hRequest.getHeader("Authorization"));
        User user = request.getUser();
        User exist = userService.getUserByName(email);

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
    public UpdatePasswordResponse update(@RequestBody UpdatePasswordRequest request, HttpServletRequest hRequest){

        String email = JWTUtil.getEmail(hRequest.getHeader("Authorization"));
        User user = request.getUser();
        User exist = userService.getUserByName(email);

        if (null != user.getPassword() ){
            exist.setPassword(user.getPassword());
        }

        userService.updateUserPassword(exist);
        return new UpdatePasswordResponse(200, "Update password success", null);
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    @ApiIgnore
    public SignInResponse requireAuth() {
        return new SignInResponse(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    @ApiIgnore
    public SignInResponse requireRole() {
        return new SignInResponse(200, "You are visiting require_role", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ApiIgnore
    public SignInResponse unauthorized() {
        return new SignInResponse(401, "Unauthorized", null);
    }

}