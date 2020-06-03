package com.ssafy.edu.vue.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.vue.dto.GameCategory;

@Repository
public class GameCategoryDaoImpl {
	
String ns = "ssafy.gamecategory.";
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GameCategory> getCategoryList() {
		return sqlSession.selectList(ns+"getCategoryList");
	}

}
