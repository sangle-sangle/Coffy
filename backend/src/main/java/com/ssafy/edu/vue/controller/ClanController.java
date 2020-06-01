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

import com.ssafy.edu.vue.dto.Clan;
import com.ssafy.edu.vue.help.BoolResult;
import com.ssafy.edu.vue.service.IClanService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api") // api 요청 주소, 이후에  @RequestMapping(value = "/")로 추가된다
public class ClanController {
	public static final Logger logger = LoggerFactory.getLogger(ClanController.class);

	@Autowired
	private IClanService clanservice;
	
	@ApiOperation(value = "clan 전체 보기", response = List.class)
	@RequestMapping(value = "/clans", method = RequestMethod.GET)
	public ResponseEntity<List<Clan>> getClans() throws Exception {
		logger.info("1-------------getClans-----------------------------" + new Date());
		List<Clan> clans = clanservice.getClans();
		return new ResponseEntity<List<Clan>>(clans, HttpStatus.OK);
	}
	
	@ApiOperation(value = "clan 상세 보기", response = List.class)
	@RequestMapping(value = "clan/{id}", method = RequestMethod.GET)
	public ResponseEntity<Clan> getClan(@PathVariable int id) throws Exception {
		logger.info("1---------------getClan-----------------------" + new Date());
		Clan clan = clanservice.getClan(id);
		return new ResponseEntity<Clan>(clan, HttpStatus.OK);
	}
	
	@ApiOperation(value = "clan 추가", response = List.class)
	@RequestMapping(value = "/clan", method = RequestMethod.PUT)
	public ResponseEntity<BoolResult> addClan(@RequestBody Clan clan) throws Exception {
		logger.info("1----------------addClan----------------" + new Date());
		clanservice.addClan(clan);
		BoolResult nr = new BoolResult();
		nr.setName("updateClan");
		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	public ResponseEntity<BoolResult> updateClan(@RequestBody Clan clan) throws Exception {
		logger.info("1-----------------updateClan-------------------" + new Date());
		clanservice.updateClan(clan);
		BoolResult nr = new BoolResult();
		nr.setName("updateClan");
		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "clan 삭제", response = BoolResult.class)
	@RequestMapping(value = "/clan/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<BoolResult> deleteClan(@PathVariable int id) throws Exception {
		logger.info("1-------------deleteClan-----------------" + new Date());
		clanservice.deleteClan(id);
		BoolResult nr = new BoolResult();
		nr.setName("deleteClan");
		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	
	
}
