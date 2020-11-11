package com.echo.backend;

import com.echo.backend.utils.GoogleMapUtil;
import com.echo.backend.utils.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    MailUtil mailUtil;

    @Test
    void sendMail() {
        mailUtil.sendSimpleMail("450015317@qq.com", "Test email2 - subject", "Test email- content");
        mailUtil.sendSimpleMail("cxy_nuaa2012@hotmail.com", "Test email2 - subject", "Test email- content");
    }

}
