package kr.or.kosa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Todoapi {

	@GetMapping("/hello")
	public String hello() {
		System.out.println("getTest");
		return "Hello World";
	}
	
	@PostMapping("/hello")
	public String helloPost(@RequestParam(name = "title") String title) {
		System.out.println("postTest");
		System.out.println(title);
		return title;
	}
	
	@GetMapping("/todoSampleDataGen")
	public String todoSampleDataGen() {
		System.out.println("OK");
		return "OK";
	}
	
}
