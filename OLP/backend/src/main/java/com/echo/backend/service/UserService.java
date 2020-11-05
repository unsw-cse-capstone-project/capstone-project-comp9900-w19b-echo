package com.echo.backend.service;

import com.echo.backend.dao.PaymentDetailMapper;
import com.echo.backend.dao.PropertyMapper;
import com.echo.backend.dao.UserFavoriteMapper;
import com.echo.backend.dao.UserMapper;
import com.echo.backend.domain.PaymentDetail;
import com.echo.backend.domain.Property;
import com.echo.backend.domain.User;
import com.echo.backend.domain.UserFavorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    private final PaymentDetailMapper paymentDetailMapper;

    private final UserFavoriteMapper userFavoriteMapper;

    private final PropertyMapper propertyMapper;

    @Autowired
    public UserService(UserMapper userMapper, PaymentDetailMapper paymentDetailMapper, UserFavoriteMapper userFavoriteMapper, PropertyMapper propertyMapper) {
        this.userMapper = userMapper;
        this.paymentDetailMapper = paymentDetailMapper;
        this.userFavoriteMapper = userFavoriteMapper;
        this.propertyMapper = propertyMapper;
    }

    public User getUserByEmail(String email){
        List<User> retList = userMapper.selectUserByEmail(email);
        if (retList != null && retList.size()>0){
            return retList.get(0);
        }
        return null;
    }

    public User getUserByName(String userName){
        List<User> retList = userMapper.selectUserByName(userName);
        if (retList != null && retList.size()>0){
            return retList.get(0);
        }
        return null;
    }

    public void addNewUser(User user){
        userMapper.createUser(user);
    }

    public void updateUserInfo(User user){
        userMapper.updateUser(user);
    }

    public void updateUserPassword(User user){
        userMapper.updateUserPassword(user);
    }

    public void verifyUser(User user){
        userMapper.verifyUser(user);
    }

    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    public void addPayment(PaymentDetail paymentDetail) {
        paymentDetail.setAddTime(new Date());
        paymentDetailMapper.addPaymentDetail(paymentDetail);
    }

    public List<PaymentDetail> getPaymentByUid(int uid) {
        return paymentDetailMapper.getPaymentDetailByUid(uid);
    }

    public void updatePaymentBySerial(PaymentDetail paymentDetail) {
        paymentDetailMapper.updatePaymentDetail(paymentDetail);
    }

    public void deletePaymentBySerial(PaymentDetail paymentDetail) {
        paymentDetailMapper.deletePaymentDetail(paymentDetail.getSerial());
    }

    public void updateUserAddressBySerial(PaymentDetail paymentDetail) {
        paymentDetailMapper.updateUserAddress(paymentDetail);
    }

    public void addFavorite(int uid, int pid) {
        UserFavorite favorite = new UserFavorite();
        favorite.setUid(uid);
        favorite.setPid(pid);
        favorite.setAddTime(new Date());
        userFavoriteMapper.addFavorite(favorite);
    }

    public void cancelFavorite(int uid, int pid) {
        UserFavorite favorite = new UserFavorite();
        favorite.setUid(uid);
        favorite.setPid(pid);
        userFavoriteMapper.removeFavorite(favorite);
    }

    public List<Property> getMyFavorite(int uid) {

        return propertyMapper.getMyFavorite(uid);
    }
}
