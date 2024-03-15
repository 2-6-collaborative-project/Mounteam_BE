package com.example.mountain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class MountainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MountainApplication.class, args);
	}

	@RequestMapping("/home")
	public @ResponseBody String home() {
		return "테스트 페이지입니다~!!";
	}
}
