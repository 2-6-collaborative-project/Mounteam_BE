package com.example.mountain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

@EnableJpaAuditing
@EntityScan
@Controller
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MountainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MountainApplication.class, args);
	}

	@RequestMapping("/home")
	public @ResponseBody String home() {
		return "테스트 페이지입니다~!!";
	}
}
