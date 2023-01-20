package com.attornatus.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HomeController {

	@Value("${home.message}")
	private String message = "home";
	private Map<String, String> json = new ConcurrentHashMap<>();

	public HomeController() {
		this.json.put("message", this.message);
	}

	@GetMapping("/")
	public Map<String, String> home() {
		return this.json;
	}

}
