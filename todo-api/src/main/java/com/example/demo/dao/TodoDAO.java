package com.example.demo.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.example.demo.vo.TodoVO;

@Mapper
public interface TodoDAO {
	int insert(TodoVO todoVO);
	List<TodoVO> todoList();
	int modifyList(TodoVO todoVO);
	int removeList(TodoVO todoVO);
}
