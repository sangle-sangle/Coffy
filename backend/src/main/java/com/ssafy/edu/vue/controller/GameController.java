package com.ssafy.edu.vue.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.ssafy.edu.vue.dto.GameCategory;
import com.ssafy.edu.vue.dto.GameInfo;
import com.ssafy.edu.vue.dto.Member;
import com.ssafy.edu.vue.dto.Solved;
import com.ssafy.edu.vue.dto.SolvedCount;
import com.ssafy.edu.vue.help.BoolResult;
import com.ssafy.edu.vue.service.IGameCategoryService;
import com.ssafy.edu.vue.service.IGameService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000, exposedHeaders = "access-token", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class GameController {
	public static final Logger logger = LoggerFactory.getLogger(GameController.class);
	@Autowired
	private IGameService gameservice;
	@Autowired
	private IGameCategoryService gamecategoryservice;
	
	@ApiOperation(value = "category list 보기", response = List.class)
	@RequestMapping(value = "/categorylist", method = RequestMethod.GET)
	public ResponseEntity<List<GameCategory>> getCategoryList() throws Exception {
		logger.info("1-------------getGame-----------------------------" + new Date());
		List<GameCategory> gamecategory = gamecategoryservice.getCategoryList();
		return new ResponseEntity<List<GameCategory>>(gamecategory, HttpStatus.OK);
	}
	
	@ApiOperation(value = "game 상세 보기", response = List.class)
	@RequestMapping(value = "/game/{category}/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getGame(@PathVariable int category,@PathVariable int id, HttpServletRequest rs) throws Exception {
		logger.info("1-------------getGame-----------------------------" + new Date());
		Map<String,Object> result = new HashMap<>();
		Solved solved = new Solved();
		int memberid = 0;
		if(rs.getAttribute("loginMember")!=null) {
			Member member = (Member) rs.getAttribute("loginMember");
			memberid = member.getId();
		}
		solved.setUser_id(memberid);
		solved.setCategory_id(category);
		solved.setGame_id(id);
		int counts = gameservice.isSolve(solved);
		result.put("count", counts);
		
		Game game = gameservice.getGame(new GameInfo(category,id));
		result.put("game", game);
		
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
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
	
	@ApiOperation(value = "solved 추가", response = List.class)
	@RequestMapping(value = "/solve", method = RequestMethod.POST)
	public ResponseEntity<BoolResult> addSolved(@RequestBody Solved solved, HttpServletRequest rs) throws Exception {
		logger.info("1-------------addSolved-----------------------------" + new Date());
		int memberid = 0;
		if(rs.getAttribute("loginMember")!=null) {
			System.out.println("rs");
			Member member = (Member) rs.getAttribute("loginMember");
			memberid = member.getId();
			solved.setUser_id(memberid);
		}
		gameservice.addSolved(solved);
		BoolResult nr=new BoolResult();
   		nr.setName("addSolved");
   		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "category당 푼 문제수", response = BoolResult.class)
	@RequestMapping(value = "/solvedcounts", method = RequestMethod.GET)
	public ResponseEntity<List<SolvedCount>> getSolvedCounts(HttpServletRequest rs) throws Exception {
		logger.info("1-------------getSolvedCounts-----------------------------" + new Date());
		Solved solved = new Solved();
		int memberid = 0;
		if(rs.getAttribute("loginMember")!=null) {
			Member member = (Member) rs.getAttribute("loginMember");
			memberid = member.getId();
		}
		List<SolvedCount> counts = gameservice.getSolvedCounts(memberid);
		return new ResponseEntity<List<SolvedCount>>(counts, HttpStatus.OK);
	}
	
	
	
}
