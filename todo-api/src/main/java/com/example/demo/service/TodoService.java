package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.TodoDAO;
import com.example.demo.vo.TodoVO;

@Service
@Transactional
public class TodoService {
	@Autowired
	private TodoDAO todoDAO;
	
	public int insert(TodoVO todoVO) { //삽입
		return todoDAO.insert(todoVO);
	}
	
	public int bulkInsert() { // 대량 삽입
		TodoVO todoVO = new TodoVO();
		for (int i=0;i<2500;i++) {
			todoVO.setTitle("'리엑트의 기초 알아보기 할일 " + i);
			todoVO.setChecked(i % 3 == 0 ? 'T' : 'F');
			todoDAO.insert(todoVO);
		}
		return todoDAO.insert(todoVO);
	}

	public List<TodoVO> todoList() { //조회
		System.out.println("service read 진입");
		return todoDAO.todoList();
	}
	
	public int update(TodoVO todovo) { //수정
		return todoDAO.modifyList(todovo); 
	}
	
	public int delete(TodoVO todovo) { //삭제
		return todoDAO.removeList(todovo);
	}
	
}
