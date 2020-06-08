package com.ssafy.edu.vue.service;

import java.util.List;

import com.ssafy.edu.vue.dto.AccessClan;
import com.ssafy.edu.vue.dto.Clan;

public interface IClanService {

	List<Clan> getClans();

	Clan getClan(int id);

	void addClan(Clan clan);

	void deleteClan(int id);

	void updateClan(Clan clan);

	void joinClan(AccessClan accessclan);
	
	void quitClan(AccessClan accessclan);

	int isAccess(AccessClan accessclan);

	int isLock(int clan_id);

	String collectPwd(AccessClan accessclan);

	int getLastClanId();

}
