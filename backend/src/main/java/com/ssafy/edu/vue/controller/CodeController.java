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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.vue.dto.Code;
import com.ssafy.edu.vue.dto.LikeCode;
import com.ssafy.edu.vue.dto.Member;
import com.ssafy.edu.vue.help.BoolResult;
import com.ssafy.edu.vue.service.ICodeService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api")
public class CodeController {
	public static final Logger logger = LoggerFactory.getLogger(CodeController.class);

	@Autowired
	private ICodeService codeservice;
	
	@ApiOperation(value = "code 전체 보기", response = List.class)
	@RequestMapping(value = "/codes", method = RequestMethod.GET)
	public ResponseEntity<List<Code>> getCodes() throws Exception {
		logger.info("1-------------getCodes-----------------------------" + new Date());
		List<Code> codes = codeservice.getCodes();
		return new ResponseEntity<List<Code>>(codes, HttpStatus.OK);
	}
	
	@ApiOperation(value = "code 상세 보기", response = List.class)
	@RequestMapping(value = "/code/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getCode(@PathVariable int id, HttpServletRequest rs) throws Exception {
		logger.info("1-------------getCode-----------------------------" + new Date());
		Map<String, Object> result = new HashMap();
		LikeCode likecode = new LikeCode();
		int memberid = 0;
		//	인자로 받은 id : 코드 id 
		if(rs.getAttribute("loginMember")!=null ) {
			Member member = (Member) rs.getAttribute("loginMember");
			memberid = member.getId();
			likecode.setUserid(memberid);
		}
		
		likecode.setCodeid(id);
		
		Code code = codeservice.getCode(id);
		result.put("code", code);
		
		//	좋아요 눌렀는지 안눌렀는지 확인 flag(유저 아이디가 들어감)
		int flag = 0;
		if(memberid!=0) {
			likecode.setUserid(memberid);
			flag=codeservice.isLike(likecode);	
		}
		result.put("flag", flag);
		
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "code 추가", response = List.class)
	@RequestMapping(value = "/code", method = RequestMethod.POST)
	public ResponseEntity<BoolResult> addCode(@RequestBody Code code) throws Exception {
		logger.info("1-------------addCode-----------------------------" + new Date());
		codeservice.addCode(code);
		BoolResult nr=new BoolResult();
   		nr.setName("addCode");
   		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "code 수정", response = BoolResult.class)
	@RequestMapping(value = "/code", method = RequestMethod.PUT)
	public ResponseEntity<BoolResult> updateCode(@RequestBody Code code) throws Exception {
		logger.info("1-------------updateCode-----------------------------" + new Date());
		codeservice.updateCode(code);
		BoolResult nr=new BoolResult();
   		nr.setName("updateCode");
   		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "code 삭제", response = BoolResult.class)
	@RequestMapping(value = "/code/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<BoolResult> deleteCode(@PathVariable int id) throws Exception {
		logger.info("1-------------deleteCode-----------------------------" + new Date());
		codeservice.deleteCode(id);
		BoolResult nr=new BoolResult();
   		nr.setName("deleteCode");
   		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Code Like 추가 (like count up)", response = List.class)
	@RequestMapping(value = "/likecode", method = RequestMethod.POST)
	public ResponseEntity<BoolResult> addLikeCode(@RequestBody LikeCode likecode) throws Exception {
		logger.info("1----------------addLikeCode------------------" + new Date());
		codeservice.addLikeCode(likecode);
		BoolResult nr = new BoolResult();
		nr.setName("addLikeCode");
		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "code Like 삭제 (like count down)", response = List.class)
	@RequestMapping(value = "/likecode", method = RequestMethod.DELETE)
	public ResponseEntity<BoolResult> deleteLikeCode(@RequestBody LikeCode likecode) throws Exception {
		logger.info("1-----------deleteLikeCode------------" + new Date());
		codeservice.deleteLikeCode(likecode);
		BoolResult nr = new BoolResult();
		nr.setName("deleteLikeCode");
		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "code 좋아요 수 출력", response = BoolResult.class)
	@RequestMapping(value = "/likecounts", method = RequestMethod.GET)
	public ResponseEntity<Integer> getLikeCounts(@ModelAttribute LikeCode likecode) throws Exception {
		logger.info("1---------getLikeCounts----------" + new Date());
		int counts = codeservice.getLikeCounts(likecode);
		return new ResponseEntity<Integer>(counts, HttpStatus.OK);
	}
	
	@ApiOperation(value= "좋아요 클릭 여부 확인", response = List.class)
	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public ResponseEntity<Integer> isLike(@ModelAttribute("data") LikeCode likecode) throws Exception {
		logger.info("1--------------isLike-------------------");
		int result = codeservice.isLike(likecode);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
}
