package com.echo.backend.controller;


import com.echo.backend.domain.*;
import com.echo.backend.dto.*;
import com.echo.backend.exception.UnauthorizedException;
import com.echo.backend.service.AuctionService;
import com.echo.backend.service.UserService;
import com.echo.backend.utils.JWTUtil;
import com.echo.backend.utils.MailUtil;
import com.echo.backend.utils.PagingUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = "User management apis")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private AuctionService auctionService;
    private MailUtil mailUtil;
    private Map<String, String> mailTemplate;
    private Map<Integer, Integer> verifyCode;

    @Autowired
    public void setService(UserService userService, AuctionService auctionService, MailUtil mailUtil, Map<String, String> mailTemplate, Map<Integer, Integer> verifyCode) {
        this.userService = userService;
        this.auctionService = auctionService;
        this.mailUtil = mailUtil;
        this.mailTemplate = mailTemplate;
        this.verifyCode = verifyCode;
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
            return new SignInResponse(200, "Login success", JWTUtil.sign(user.getEmail(), user.getUserName(), request.getPassword(), user.getUid()));
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
        user = userService.getUserByEmail(request.getEmail());
        return new SignUpResponse(200, "Login success", JWTUtil.sign(user.getEmail(), user.getUserName(), user.getPassword(), user.getUid()));
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public UserInfo updateUser(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        return getUserInfo(user);
    }

    private UserInfo getUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(user.getEmail());
        userInfo.setFullName(user.getUserName());
        userInfo.setPhone(user.getPhone());
        userInfo.setUid(user.getUid());
        return userInfo;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public UserInfo updateUser(@RequestBody UserInfo request) {
        User user = userService.getUserByEmail(request.getEmail());
        user.setUserName(request.getFullName());
        user.setPhone(request.getPhone());
        userService.updateUserInfo(user);
        return getUserInfo(user);
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
    public UpdateInfoResponse updateInfo(@RequestBody UpdateInfoRequest request, HttpServletRequest hRequest){

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

    @RequestMapping(value = "/request-verify-code", method = RequestMethod.POST)
    public UpdatePasswordResponse getVerifyCode(@RequestBody UpdatePasswordRequest request, HttpServletRequest hRequest){

        String email = request.getUser().getEmail();
        Integer uid = userService.getUserByEmail(email).getUid();
        Random rand = new Random();
        int code = (rand.nextInt(9)+1)*1000 + rand.nextInt(1000);
        verifyCode.put(uid, code);

        String template = mailTemplate.get("ResetPassword").replace("${code}", String.valueOf(code));
        mailUtil.sendSimpleMail(email, "ResetPassword", template);
        return new UpdatePasswordResponse(200, "Request verify code success", null);
    }

    @RequestMapping(value = "/update-password", method = RequestMethod.POST)
    public UpdatePasswordResponse updatePassword(@RequestBody UpdatePasswordRequest request, HttpServletRequest hRequest){

        String email = request.getUser().getEmail();
        Integer uid = userService.getUserByEmail(email).getUid();

        if (!verifyCode.containsKey(uid))
            return new UpdatePasswordResponse(501, "Please send verify code first", null);

        if (verifyCode.get(uid) != request.getCode()){
            return new UpdatePasswordResponse(501, "Your verify code is not right", null);
        }

        User user = request.getUser();
        User exist = userService.getUserByName(email);

        if (null != user.getPassword() ){
            exist.setPassword(user.getPassword());
        }

        userService.updateUserPassword(exist);
        return new UpdatePasswordResponse(200, "Update password success", null);
    }

    @RequestMapping(value = "/add-payment", method = RequestMethod.POST)
    @RequiresAuthentication
    public AddPaymentDetailResponse addPayment(@RequestBody AddPaymentDetailRequest request, HttpServletRequest hRequest){

        userService.addPayment(request.getPaymentDetail());
        return new AddPaymentDetailResponse(200, "Add payment success", null);
    }

    @RequestMapping(value = "/view-payment", method = RequestMethod.POST)
    @RequiresAuthentication
    public List<PaymentDetail> addPayment(@RequestBody SearchPaymentDetailRequest request, HttpServletRequest hRequest){

        return PagingUtil.afterPaging(userService.getPaymentByUid(request.getUid()), request.getPage(), request.getDataNum());
    }

    @RequestMapping(value = "/update-payment", method = RequestMethod.POST)
    @RequiresAuthentication
    public UpdatePaymentResponse updatePayment(@RequestBody AddPaymentDetailRequest request, HttpServletRequest hRequest){

        userService.updatePaymentBySerial(request.getPaymentDetail());
        return new UpdatePaymentResponse(200, "update payment success", null);
    }

    @RequestMapping(value = "/update-user-address", method = RequestMethod.POST)
    @RequiresAuthentication
    public UpdatePaymentResponse updateUserAddress(@RequestBody AddPaymentDetailRequest request, HttpServletRequest hRequest){

        userService.updateUserAddressBySerial(request.getPaymentDetail());
        return new UpdatePaymentResponse(200, "update payment success", null);
    }

    @RequestMapping(value = "/delete-payment", method = RequestMethod.POST)
    @RequiresAuthentication
    public UpdatePaymentResponse deletePayment(@RequestBody AddPaymentDetailRequest request, HttpServletRequest hRequest){

        userService.deletePaymentBySerial(request.getPaymentDetail());
        return new UpdatePaymentResponse(200, "update payment success", null);
    }

    @RequestMapping(value = "/add-favorite", method = RequestMethod.POST)
    @RequiresAuthentication
    public AddUserFavoriteResponse addFavorite(@RequestBody AddUserFavoriteRequest request, HttpServletRequest hRequest){

        userService.addFavorite(request.getUid(), request.getPid());
        userService.collectHabitFromFavorite(request.getUid(), request.getPid());

        return new AddUserFavoriteResponse(200, "Add favorite success", null);
    }

    @RequestMapping(value = "/cancel-favorite", method = RequestMethod.POST)
    @RequiresAuthentication
    public CancelUserFavoriteResponse cancelFavorite(@RequestBody AddUserFavoriteRequest request, HttpServletRequest hRequest){

        userService.cancelFavorite(request.getUid(), request.getPid());

        return new CancelUserFavoriteResponse(200, "Cancel favorite success", null);
    }

    @RequestMapping(value = "/get-recommendation", method = RequestMethod.POST)
    @RequiresAuthentication
    public List<Property> recommendation(@RequestBody SearchPropertyRequest request, HttpServletRequest hRequest) throws IOException, ParseException {

        int uid = JWTUtil.getUid(hRequest.getHeader("Authorization"), userService);
        return userService.getRecommandProperty(uid);
    }

    @RequestMapping(value = "/view-favorite", method = RequestMethod.POST)
    @RequiresAuthentication
    public List<PropertyAuction> viewMyFavorite(@RequestBody SearchFavoriteRequest request, HttpServletRequest hRequest){
        List<Property> properties = PagingUtil.afterPaging(userService.getMyFavorite(request.getUid()), request.getPage(), request.getDataNum());
        List<PropertyAuction> propertyAuctions = new ArrayList<>();
        for(Property p : properties){
            PropertyAuction propertyAuction = new PropertyAuction();
            propertyAuction.setProperty(p);
            List<Auction> auctions = auctionService.getAuctionByPid(p.getPid());
            if(auctions != null && auctions.size() > 0) {
                propertyAuction.setAuction(auctions.get(0));
            }
            propertyAuctions.add(propertyAuction);
        }
        return propertyAuctions;
    }

    @RequestMapping(value = "/send-email", method = RequestMethod.POST)
    public SendEmailResponse sendTestEmail(@RequestBody SendEmailRequest request, HttpServletRequest hRequest){
        mailUtil.sendSimpleMail(request.getTo(), request.getSubject(), request.getContent());
        return new SendEmailResponse(200, "send email success", null);
    }

    @RequestMapping(value = "/delete-message", method = RequestMethod.POST)
    @RequiresAuthentication
    public MessageResponse deleteMessage(@RequestBody MessageRequest request, HttpServletRequest hRequest){

        userService.deleteMessage(request.getSerial());
        return new MessageResponse(200, "delete message success", null);
    }

    @RequestMapping(value = "/view-message", method = RequestMethod.POST)
    @RequiresAuthentication
    public List<UserMessage> viewMessage(@RequestBody MessageRequest request, HttpServletRequest hRequest){

        int uid = JWTUtil.getUid(hRequest.getHeader("Authorization"), userService);
        return userService.viewMyMessage(uid);
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