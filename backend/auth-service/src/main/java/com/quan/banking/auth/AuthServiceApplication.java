package com.quan.banking.auth;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class AuthServiceApplication {

    @PostConstruct
    public void init(){
        // Thiết lập múi giờ mặc định cho ứng dụng thành Asia/Ho_Chi_Minh hoặc GMT+7
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
    }
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
