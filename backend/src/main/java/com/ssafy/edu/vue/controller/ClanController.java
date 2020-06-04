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

import com.ssafy.edu.vue.dto.AccessClan;
import com.ssafy.edu.vue.dto.Clan;
import com.ssafy.edu.vue.dto.Member;
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
	
	//	이미 가입한 클랜이 있는지 판별, 자신의 클랜인지 판별 
	@ApiOperation(value = "clan 상세 보기", response = List.class)
	@RequestMapping(value = "clan/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getClan(@PathVariable int id, HttpServletRequest rs) throws Exception {
		logger.info("1---------------getClan-----------------------" + new Date());
		Map<String, Object> result = new HashMap();
		AccessClan accessclan = new AccessClan();
		int memberid = 0;
		// 인자로 받은 id : 코드 id / loginMember는 JwtInterceptor.java에서 지정
		// 로그인했는지 먼저 판단
		if(rs.getAttribute("loginMember")!=null ) {
			Member member = (Member) rs.getAttribute("loginMember");
			memberid = member.getId();
			accessclan.setUser_id(memberid);
		}
		accessclan.setClan_id(id);
		Clan clan = clanservice.getClan(id);
		result.put("clan", clan);
		
		int myclan = clanservice.isAccess(accessclan);
		result.put("myclan", myclan);
		
		if (myclan == 0) {
			// 가입한 클랜이 없음			
			result.put("clan_status", 0);
		}
		else if (myclan == id) {
			// 내가 가입한 클랜			
			result.put("clan_status", 1);
		}
		else {
			// 다른 클랜			
			result.put("clan_status", 2);
		}
		
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "clan 추가", response = List.class)
	@RequestMapping(value = "/clan", method = RequestMethod.POST)
	public ResponseEntity<BoolResult> addClan(@RequestBody Clan clan) throws Exception {
		// 클랜 만들 때 locked 여부에 따라 password 입력할 수 있도록		
		logger.info("1----------------addClan----------------" + new Date());	
		clanservice.addClan(clan);
		BoolResult nr = new BoolResult();
		nr.setName("addClan");
		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "clan 수정", response = List.class)
	@RequestMapping(value = "/clan", method = RequestMethod.PUT)
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
	
	@ApiOperation(value = "clan 가입", response = BoolResult.class)
	@RequestMapping(value = "/joinclan", method = RequestMethod.POST)
	public ResponseEntity<BoolResult> joinClan(@RequestBody AccessClan accessclan) throws Exception {
		// accessclan에 담은 password가 clanservice에 있는 password와 같은지 판별
		// locked: 1이던 0이던 같은지만 판별하면 됨
		BoolResult nr = new BoolResult();
		nr.setName("joinClan");
		if (accessclan.getPwd().equals(clanservice.collectPwd(accessclan))) {
			clanservice.joinClan(accessclan);
			nr.setState("succ");
		}else {
			nr.setState("fail");
		}
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "clan 탈퇴", response = List.class)
	@RequestMapping(value = "/joinclan", method = RequestMethod.DELETE)
	public ResponseEntity<BoolResult> quitClan(@RequestBody AccessClan accessclan) throws Exception {
		//	quitClan하면 clanid에 0이 들어감	
		clanservice.quitClan(accessclan);
		BoolResult nr = new BoolResult();
		nr.setName("quitClan");
		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	
	
}
