package com.ssafy.edu.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.edu.vue.dao.GameDaoImpl;
import com.ssafy.edu.vue.dto.Game;
import com.ssafy.edu.vue.dto.GameInfo;
import com.ssafy.edu.vue.dto.Solved;
import com.ssafy.edu.vue.dto.SolvedCount;

@Service
public class GameServiceImpl implements IGameService {

	@Autowired
	private GameDaoImpl gamedao;

	@Override
	@Transactional(readOnly = true)
	public Game getGame(GameInfo gameinfo) {
		return gamedao.getGame(gameinfo);
	}

	@Override
	@Transactional
	public void addGame(Game game) {
		gamedao.addGame(game);
	}

	@Override
	@Transactional
	public void updateGame(Game game) {
		gamedao.updateGame(game);
	}

	@Override
	@Transactional
	public void deleteGame(int id) {
		gamedao.deleteGame(id);
	}

	@Override
	@Transactional
	public void addSolved(Solved solved) {
		gamedao.addSolved(solved);
	}

	@Override
	@Transactional(readOnly = true)
	public int getSolvedCounts(Solved solved) {
		return gamedao.getSolvedCounts(solved);
	}

	@Override
	public int isSolve(Solved solved) {
		return gamedao.isSolve(solved);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SolvedCount> getSolvedCounts(int memberid) {
		return gamedao.getSolvedCounts(memberid);
	}
}
