package com.ssafy.edu.vue.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.vue.dto.AccessClan;
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
	
	public void joinClan(AccessClan accessclan) {
		sqlSession.update(ns+"joinClan", accessclan);
	}
	
	public void quitClan(AccessClan accessclan) {
		sqlSession.update(ns+"quitClan", accessclan);
	}

	public int isAccess(AccessClan accessclan) {
		return sqlSession.selectOne(ns+"isAccess", accessclan);
	}

	public int isLock(int clan_id) {
		return sqlSession.selectOne(ns+"isLock",clan_id);
	}

	public String collectPwd(AccessClan accessclan) {
		return sqlSession.selectOne(ns+"collectPwd",accessclan);
	}

	public int getLastClanId() {
		return sqlSession.selectOne(ns+"getLastClanId");
	}

}
