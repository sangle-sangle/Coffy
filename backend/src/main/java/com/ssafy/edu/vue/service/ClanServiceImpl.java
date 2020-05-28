package com.ssafy.edu.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.edu.vue.dao.ClanDaoImpl;
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

}
