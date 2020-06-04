package com.ssafy.edu.vue.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.vue.dto.Game;
import com.ssafy.edu.vue.dto.GameInfo;
import com.ssafy.edu.vue.dto.Solved;
import com.ssafy.edu.vue.dto.SolvedCount;

@Repository
public class GameDaoImpl {

	String ns = "ssafy.game.";
	
	@Autowired
	private SqlSession sqlSession;
	
	public Game getGame(GameInfo gameinfo) {
		return sqlSession.selectOne(ns+"getGame",gameinfo);
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

	public void addSolved(Solved solved) {
		sqlSession.insert(ns+"addSolved",solved);
	}

	public int getSolvedCounts(Solved solved) {
		return sqlSession.selectOne(ns+"getSolvedCounts",solved);
	}

	public int isSolve(Solved solved) {
		return sqlSession.selectOne(ns+"isSolve",solved);
	}

	public List<SolvedCount> getSolvedCounts(int memberid) {
		return sqlSession.selectList(ns+"getSolvedCounts",memberid);
	}

}
