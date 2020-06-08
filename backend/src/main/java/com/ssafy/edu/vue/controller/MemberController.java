package com.ssafy.edu.vue.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.vue.dto.CheckSignUp;
import com.ssafy.edu.vue.dto.Member;
import com.ssafy.edu.vue.help.BoolResult;
import com.ssafy.edu.vue.service.IJwtService;
import com.ssafy.edu.vue.service.IMemberService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000, exposedHeaders = "access-token", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class MemberController {
	public static final Logger logger = LoggerFactory.getLogger(CodeController.class);
	
	@Autowired
	private IMemberService memberservice;
	@Autowired
	private IJwtService jwtService;
	
	@ApiOperation(value = "member 전체 목록 보기", response = List.class)
	@RequestMapping(value = "/memberlist", method = RequestMethod.GET)
	public ResponseEntity<List<Member>> getMemberList() throws Exception {
		logger.info("1-------------getMemberList-----------------------------" + new Date());
		List<Member> members = memberservice.getMemberList();

		return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
	}

	@ApiOperation(value = "member 내 정보", response = List.class)
	@RequestMapping(value = "/member/{memberid}", method = RequestMethod.GET)
	public ResponseEntity<Member> getMember(@PathVariable int memberid) throws Exception {
		logger.info("1-------------getMember-----------------------------" + new Date());
		Member member = memberservice.getMember(memberid);
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}
	
	@ApiOperation(value = "clan별 member 전체보기", response = List.class)
	@RequestMapping(value = "/clanmember/{clanid}", method = RequestMethod.GET)
	public ResponseEntity<List<Member>> getClanMembers(@PathVariable int clanid) throws Exception {
		List<Member> members = memberservice.getClanMembers(clanid);
		return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
	}
	

	@ApiOperation(value = "member 로그인", response = List.class)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(@RequestBody Member member) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		logger.info("1-------------login-----------------------------" + new Date());
		int delflag = memberservice.checkDelflag(member.getEmail());
		HttpHeaders headers = new HttpHeaders();
		if(delflag==0) {
			Member login = memberservice.checkLogin(member);
			if (login == null) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			
			String token = jwtService.signin(login);
			
			headers.set("access-token", token);
			System.out.println(token);
			resultMap.put("status", true);
			resultMap.put("data", login);
		}else {
			resultMap.put("status", true);
			resultMap.put("delflag", delflag);
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, headers, HttpStatus.OK);
	}

	@ApiOperation(value = "member 회원가입 (eamil or username 중복 시 false)", response = List.class)
	@RequestMapping(value = "/member", method = RequestMethod.POST)
	public ResponseEntity<CheckSignUp> addMember(@RequestBody Member member) throws Exception {
		logger.info("1-------------addMember-----------------------------" + new Date());
		System.out.println(member);
		int email = memberservice.checkEmail(member.getEmail());
		int username = memberservice.checkUsername(member.getUsername());
		CheckSignUp result = new CheckSignUp();
		if (email == 0 && username == 0) {
			result.setSignup(true);
			result.setMessage("사용 가능");
			memberservice.addMember(member);
		} else if (email >= 1 && username == 0) {
			result.setSignup(false);
			result.setMessage("이미 존재하는 email 입니다.");
		} else if (email == 0 && username >= 1) {
			result.setSignup(false);
			result.setMessage("이미 존재하는 username 입니다.");
		} else {
			result.setSignup(false);
			result.setMessage("이미 존재하는 email, username 입니다.");
		}
		return new ResponseEntity<CheckSignUp>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "member 회원정보 수정", response = BoolResult.class)
	@RequestMapping(value = "/member", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateMember(@RequestBody Member member, HttpServletRequest rs) throws Exception {
		logger.info("1-------------updateMember-----------------------------" + new Date());
		Map<String, Object> resultMap = new HashMap<>();
		logger.info("2------"+member);
		Member origin = (Member) rs.getAttribute("loginMember");

		System.out.println("origin-id " + origin.getId());
		System.out.println("origin-username " + origin.getUsername());

		if(member.getUsername().equals("") || member.getUsername()==null) {
			member.setUsername(origin.getUsername());
		}else if(memberservice.checkUsername(member.getUsername())>0) {
			resultMap.put("state", "이미 존재하는 username 입니다.");
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK); 
		}
		if(member.getImg()==null || member.getImg().equals("") ) {
			member.setImg(origin.getImg());
		}
		member.setId(origin.getId());
		memberservice.updateMember(member);
		Member login = memberservice.getMember(member.getId());		
		String token = jwtService.signin(login);
		HttpHeaders headers = new HttpHeaders();
		headers.set("access-token", token);
		resultMap.put("state", "사용 가능");
		return new ResponseEntity<Map<String, Object>>(resultMap, headers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "member 비밀번호 수정", response = BoolResult.class)
	@RequestMapping(value = "/member/password", method = RequestMethod.PUT)
	public ResponseEntity<BoolResult> updatePassword(@RequestBody Member member, HttpServletRequest rs) throws Exception {
		logger.info("1-------------updatePassword-----------------------------" + new Date());
		logger.info("2------"+member);

		memberservice.updatePassword(member);
		BoolResult nr = new BoolResult();
		nr.setName("updatePassword");
		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
		
	}

	@ApiOperation(value = "member 회원 탈퇴", response = BoolResult.class)
	@RequestMapping(value = "/member/{memberid}", method = RequestMethod.DELETE)
	public ResponseEntity<BoolResult> deleteMember(@PathVariable int memberid, HttpServletRequest rs) throws Exception {
		logger.info("1-------------deleteMember-----------------------------" + new Date());
		System.out.println("1"+rs.getHeader("access-token"));
		System.out.println("2"+rs.getAttribute("loginMember"));
		int loginid = 0;
		if (rs.getAttribute("loginMember") != null) {
			Member member = (Member) rs.getAttribute("loginMember");
			loginid = member.getId();
		}

		logger.info("2-----"+loginid+"////"+memberid);
		System.out.println(loginid+" "+memberid);
		if (loginid == memberid) {
			memberservice.deleteMember(memberid);
			BoolResult nr = new BoolResult();
			nr.setName("deleteMember");
			nr.setState("succ");
			return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "가입 회원 수 출력", response = BoolResult.class)
	@RequestMapping(value = "/member", method = RequestMethod.GET)
	public ResponseEntity<Integer> checkUsers() throws Exception {
		logger.info("1-------------checkUsers-----------------------------" + new Date());
		int cnt = memberservice.checkUsers();
		return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
	}

	@ApiOperation(value = "member 회원 권한 수정", response = BoolResult.class)
	@RequestMapping(value = "/memberauth", method = RequestMethod.PUT)
	public ResponseEntity<BoolResult> updateMemberAuth(@RequestBody Member member) throws Exception {
		logger.info("1-------------updateMemberAuth-----------------------------" + new Date());
		memberservice.updateMemberAuth(member);
		BoolResult nr = new BoolResult();
		nr.setName("updateMemberAuth");
		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}

	@ApiOperation(value = "member 회원가입 email 중복 검사", response = BoolResult.class)
	@RequestMapping(value = "/member/email", method = RequestMethod.POST)
	public ResponseEntity<Integer> emailCheck(@RequestBody String email) throws Exception {
		logger.info("1-------------emailCheck-----------------------------" + new Date());
		int cnt = memberservice.checkEmail(email);

		return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
	}

	@ApiOperation(value = "member 회원가입 username 중복 검사", response = BoolResult.class)
	@RequestMapping(value = "/member/username", method = RequestMethod.POST)
	public ResponseEntity<Integer> usernameCheck(@RequestBody String username) throws Exception {
		logger.info("1-------------emailCheck-----------------------------" + new Date());
		int cnt = memberservice.checkUsername(username);

		return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
	}
}
