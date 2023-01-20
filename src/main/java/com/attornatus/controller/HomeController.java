package com.attornatus.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HomeController {

	@Value("${home.message}")
	private String message = "home";

	@GetMapping("/")
	public Map<String, String> home() {
		return Map.of("message", this.message);
	}

}
