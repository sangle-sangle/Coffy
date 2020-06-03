package com.ssafy.edu.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.edu.vue.dao.GameCategoryDaoImpl;
import com.ssafy.edu.vue.dao.GameDaoImpl;
import com.ssafy.edu.vue.dto.Game;
import com.ssafy.edu.vue.dto.GameCategory;

@Service
public class GameCategoryServiceImpl implements IGameCategoryService {

	@Autowired
	private GameCategoryDaoImpl gamecategorydao;

	@Override
	@Transactional(readOnly = true)
	public List<GameCategory> getCategoryList() {
		return gamecategorydao.getCategoryList();
	}

	

}
