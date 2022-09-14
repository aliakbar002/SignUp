package com.signup.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.Date;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SignUpApplication {

    public static void main(String[] args) {
      //  System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(SignUpApplication.class, args);

    }

}
