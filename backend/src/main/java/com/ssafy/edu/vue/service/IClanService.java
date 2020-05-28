package com.ssafy.edu.vue.service;

import java.util.List;

import com.ssafy.edu.vue.dto.Clan;

public interface IClanService {

	List<Clan> getClans();

	Clan getClan(int id);

	void addClan(Clan clan);

	void deleteClan(int id);

	void updateClan(Clan clan);

}
