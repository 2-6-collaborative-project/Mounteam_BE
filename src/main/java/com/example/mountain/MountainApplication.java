package com.example.mountain;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

@OpenAPIDefinition(
		servers = {
				@Server(url="/", description = "Default Server URL")
		}
)
@EnableJpaAuditing
@EntityScan
@Controller
@SpringBootApplication
@Tag(name = "테스트, 상태체크 API")
public class MountainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MountainApplication.class, args);
	}

	@GetMapping("/home")
	public @ResponseBody String home() {
		return "테스트 페이지입니다~!!";
	}
}
