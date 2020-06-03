package com.ssafy.edu.vue.service;

import com.ssafy.edu.vue.dto.Game;

public interface IGameService {

	Game getGame(int id);

	void addGame(Game code);

	void updateGame(Game code);

	void deleteGame(int id);

}
