package com.echo.backend.utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.echo.backend.service.UserService;
import org.apache.commons.lang.time.DateUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;


public class JWTUtil {

    // 过期时间5分钟
    private static final long EXPIRE_TIME = 5*60*1000;

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String email, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("email", email)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getEmail(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("email").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 根据token获取uid
     * @param token
     * @param userService
     * @return
     */
    public static int getUid(String token, UserService userService){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("uid").asInt();
        } catch (JWTDecodeException e) {
            return 0;
        }
    }

    /**
     * 生成签名,2小时后过期
     * @param email 用户名
     * @param secret 用户的密码
     * @param uid
     * @return 加密的token
     */
    public static String sign(String email, String fullName, String secret, int uid) {
        try {
            //Date date = new Date(2099,1,1);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withSubject(fullName)
                    .withClaim("email", email)
                    .withClaim("uid", uid)
                    .withExpiresAt(DateUtils.addHours(new Date(), 2))
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}