package com.ssafy.edu.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.edu.vue.dao.ClanDaoImpl;
import com.ssafy.edu.vue.dto.AccessClan;
import com.ssafy.edu.vue.dto.Clan;

@Service
public class ClanServiceImpl implements IClanService {
	
	@Autowired
	private ClanDaoImpl clandao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Clan> getClans() {
		return clandao.getClans();
	}

	@Override
	@Transactional(readOnly = true)
	public Clan getClan(int id) {
		return clandao.getClan(id);
	}

	@Override
	@Transactional
	public void addClan(Clan clan) {
		clandao.addClan(clan);
	}

	@Override
	@Transactional
	public void deleteClan(int id) {
		clandao.deleteClan(id);
	}

	@Override
	@Transactional
	public void updateClan(Clan clan) {
		clandao.updateClan(clan);
	}

	@Override
	@Transactional
	public void joinClan(AccessClan accessclan) {
		clandao.joinClan(accessclan);
	}
	
	@Override
	@Transactional
	public void quitClan(AccessClan accessclan) {
		clandao.quitClan(accessclan);
	}

	@Override
	@Transactional(readOnly = true)
	public int isAccess(AccessClan accessclan) {
		return clandao.isAccess(accessclan);
	}

	@Override
	@Transactional(readOnly = true)
	public int isLock(int clan_id) {
		return clandao.isLock(clan_id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public String collectPwd(AccessClan accessclan) {
		return clandao.collectPwd(accessclan);
	}

	@Override
	@Transactional(readOnly = true)
	public int getLastClanId() {
		return clandao.getLastClanId();
	}
}
