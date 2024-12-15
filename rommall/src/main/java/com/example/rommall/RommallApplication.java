package com.example.rommall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.rommall.dao")
public class RommallApplication {

	public static void main(String[] args) {
		SpringApplication.run(RommallApplication.class, args);
	}

}
