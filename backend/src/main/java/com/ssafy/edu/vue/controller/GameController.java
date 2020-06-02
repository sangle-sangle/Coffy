package com.ssafy.edu.vue.controller;

import java.util.Date;
import java.util.List;
	
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.vue.dto.Game;
import com.ssafy.edu.vue.help.BoolResult;
import com.ssafy.edu.vue.service.IGameService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api")
public class GameController {
	public static final Logger logger = LoggerFactory.getLogger(GameController.class);

	@Autowired
	private IGameService gameservice;
	
	@ApiOperation(value = "game 상세 보기", response = List.class)
	@RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
	public ResponseEntity<Game> getGame(@PathVariable int id) throws Exception {
		logger.info("1-------------getGame-----------------------------" + new Date());
		Game game = gameservice.getGame(id);
		return new ResponseEntity<Game>(game, HttpStatus.OK);
	}
	
	@ApiOperation(value = "game 추가", response = List.class)
	@RequestMapping(value = "/game", method = RequestMethod.POST)
	public ResponseEntity<BoolResult> addGame(@RequestBody Game game) throws Exception {
		logger.info("1-------------addGame-----------------------------" + new Date());
		gameservice.addGame(game);
		BoolResult nr=new BoolResult();
   		nr.setName("addGame");
   		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "game 수정", response = BoolResult.class)
	@RequestMapping(value = "/game", method = RequestMethod.PUT)
	public ResponseEntity<BoolResult> updateGame(@RequestBody Game game) throws Exception {
		logger.info("1-------------updateGame-----------------------------" + new Date());
		gameservice.updateGame(game);
		BoolResult nr=new BoolResult();
   		nr.setName("updateGame");
   		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "game 삭제", response = BoolResult.class)
	@RequestMapping(value = "/game/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<BoolResult> deleteGame(@PathVariable int id) throws Exception {
		logger.info("1-------------deleteGame-----------------------------" + new Date());
		gameservice.deleteGame(id);
		BoolResult nr=new BoolResult();
   		nr.setName("deleteGame");
   		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
}
