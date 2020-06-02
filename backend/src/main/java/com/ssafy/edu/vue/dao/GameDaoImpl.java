package com.ssafy.edu.vue.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.vue.dto.Game;

@Repository
public class GameDaoImpl {

	String ns = "ssafy.game.";
	
	@Autowired
	private SqlSession sqlSession;

	public Game getGame(int id) {
		return sqlSession.selectOne(ns+"getGame",id);
	}

	public void addGame(Game game) {
		sqlSession.insert(ns+"addGame",game);
	}

	public void updateGame(Game game) {
		sqlSession.update(ns+"updateGame",game);
	}

	public void deleteGame(int id) {
		sqlSession.delete(ns+"deleteGame",id);
	}

}
