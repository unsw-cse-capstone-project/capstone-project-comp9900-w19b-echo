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

    private static final long EXPIRE_TIME = 5*60*1000;

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

    public static String getEmail(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("email").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static int getUid(String token, UserService userService){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("uid").asInt();
        } catch (JWTDecodeException e) {
            return 0;
        }
    }

    public static String sign(String email, String fullName, String secret, int uid) {
        try {
            //Date date = new Date(2099,1,1);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // attach username
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