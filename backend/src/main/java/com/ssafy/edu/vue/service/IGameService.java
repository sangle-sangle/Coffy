package com.ssafy.edu.vue.service;

import java.util.List;

import com.ssafy.edu.vue.dto.Game;
import com.ssafy.edu.vue.dto.GameInfo;
import com.ssafy.edu.vue.dto.Solved;
import com.ssafy.edu.vue.dto.SolvedCount;

public interface IGameService {

	Game getGame(GameInfo gameinfo);

	void addGame(Game code);

	void updateGame(Game code);

	void deleteGame(int id);

	void addSolved(Solved solved);

	int getSolvedCounts(Solved solved);

	int isSolve(Solved solved);

	List<SolvedCount> getSolvedCounts(int memberid);

}
