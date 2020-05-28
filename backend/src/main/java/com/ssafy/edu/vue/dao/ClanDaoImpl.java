package com.ssafy.edu.vue.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.vue.dto.Clan;

@Repository
public class ClanDaoImpl {

	String ns = "ssafy.clan.";
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<Clan> getClans() {
		return sqlSession.selectList(ns+"getClans");
	}

	public Clan getClan(int id) {
		return sqlSession.selectOne(ns+"getClan", id);
	}

	public void addClan(Clan clan) {
		sqlSession.insert(ns+"addClan", clan);
	}

	public void deleteClan(int id) {
		sqlSession.delete(ns+"deleteClan", id);
	}

	public void updateClan(Clan clan) {
		sqlSession.delete(ns+"updateClan", clan);
	}

}
