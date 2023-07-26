package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TodoService;
import com.example.demo.vo.TodoVO;

@RestController
public class TestController {
	@Autowired
	private TodoService todoService; 
	
	@GetMapping("/hello")
	public String helloGet(@RequestParam(name = "title") String title) {
		System.out.println("TestController.helloGet()");
		System.out.println("title -> " + title);
		return "Get Hello World ";
	}
	
	@PostMapping("/hello")
	public String helloPost(@RequestParam(name = "title") String title) {
		
		System.out.println("TestController.helloPost()");
		System.out.println("title -> " + title);
		
		return "Post Hello World";
	}
	
	@GetMapping("/todoSampleDataGen")
	public String todoSampleDataGen() {
		System.out.println("TestController.todoSampleDataGen()");
		todoService.bulkInsert();
		return "OK";
	}

	@GetMapping("/todoList")
	public ResponseEntity<List<TodoVO>> todoList() {
		System.out.println("TestController.todoList()");
		List<TodoVO> todoList = todoService.todoList(); 
		return ResponseEntity.status(HttpStatus.OK).body(todoList);
	}
	
	@GetMapping("/insert")
	public ResponseEntity<Integer> insert(@RequestBody TodoVO todoVO) {
		System.out.println("insert controller 진입");
		int result = todoService.insert(todoVO);
		if(result > 0) {
			 System.out.println("삽입성공");
			 return new ResponseEntity<Integer>(result,HttpStatus.OK);
		}
		return new ResponseEntity<Integer>(result,HttpStatus.NOT_FOUND);
		
	}
	
	
	@GetMapping("/read")
	public ResponseEntity<List<TodoVO>> read() {
		System.out.println("read controller 진입");
		List<TodoVO> listtodovo = todoService.todoList();
		if(listtodovo != null) {
			 System.out.println("조회성공");
			 return new ResponseEntity<List<TodoVO>>(listtodovo,HttpStatus.OK);
		}
		return new ResponseEntity<List<TodoVO>>(listtodovo,HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/update")
	public ResponseEntity<Integer> update(TodoVO todoVO) {
		System.out.println("update controller 진입");
		int result = todoService.update(todoVO);
		
		if(result > 0 ) {
			 System.out.println("수정 성공");
			 return new ResponseEntity<Integer>(result,HttpStatus.OK);
		}
		return new ResponseEntity<Integer>(result,HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/delete")
	public ResponseEntity<Integer> delete(TodoVO todoVO) {
		System.out.println("delete controller 진입");
		int result = todoService.delete(todoVO);
		if(result > 0 ) {
			 System.out.println("삭제 성공");
			 return new ResponseEntity<Integer>(result,HttpStatus.OK);
		}
		return new ResponseEntity<Integer>(result,HttpStatus.NOT_FOUND);
		
	}
	
	
	
}











