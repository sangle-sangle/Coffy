package com.ssafy.edu;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

@Api(value = "Test of API")
@RequestMapping("/api")
@RestController
public class TestController {
	
	@GetMapping("/")
	public String root() {
		return "Spring Boot Test";
	}
}
