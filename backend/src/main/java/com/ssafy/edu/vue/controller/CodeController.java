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
import com.ssafy.edu.vue.dto.CodeComment;
import com.ssafy.edu.vue.dto.CommentInfo;
import com.ssafy.edu.vue.dto.LikeCode;
import com.ssafy.edu.vue.dto.Member;
import com.ssafy.edu.vue.help.BoolResult;
import com.ssafy.edu.vue.service.ICodeService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000, exposedHeaders = "access-token", allowedHeaders = "*")
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
			likecode.setCodeid(id);
			likecode.setUserid(memberid);
			int flag = codeservice.isLike(likecode);
			result.put("flag", flag);
		}
		
		Code code = codeservice.getCode(id);
		result.put("code", code);

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
	@RequestMapping(value = "/likecode/{id}", method = RequestMethod.POST)
	public ResponseEntity<BoolResult> addLikeCode(@PathVariable int id, HttpServletRequest rs) throws Exception {
		logger.info("1----------------addLikeCode------------------" + new Date());
		LikeCode likecode = new LikeCode();
		int memberid = 0;
		if(rs.getAttribute("loginMember")!=null ) {
			Member member = (Member) rs.getAttribute("loginMember");
			memberid = member.getId();
			likecode.setCodeid(id);
			likecode.setUserid(memberid);
			codeservice.addLikeCode(likecode);
		}
		BoolResult nr = new BoolResult();
		nr.setName("addLikeCode");
		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "code Like 삭제 (like count down)", response = List.class)
	@RequestMapping(value = "/likecode/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<BoolResult> deleteLikeCode(@PathVariable int id, HttpServletRequest rs) throws Exception {
		logger.info("1-----------deleteLikeCode------------" + new Date());
		LikeCode likecode = new LikeCode();
		int memberid = 0;
		if(rs.getAttribute("loginMember")!=null ) {
			Member member = (Member) rs.getAttribute("loginMember");
			memberid = member.getId();
			likecode.setCodeid(id);
			likecode.setUserid(memberid);
			codeservice.deleteLikeCode(likecode);
		}
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
	
	@ApiOperation(value = "내가 작성한 code 전체 보기", response = List.class)
	@RequestMapping(value = "/mycodes", method = RequestMethod.GET)
	public ResponseEntity<List<Code>> getMyCodes(HttpServletRequest rs) throws Exception {
		logger.info("1-------------getMyCodes-----------------------------" + new Date());
		int memberid = 0;
		if(rs.getAttribute("loginMember")!=null ) {
			Member member = (Member) rs.getAttribute("loginMember");
			memberid = member.getId();
		}
		List<Code> codes = codeservice.getMyCodes(memberid);
		return new ResponseEntity<List<Code>>(codes, HttpStatus.OK);
	}
	
	@ApiOperation(value = "내가 좋아요 누른 code 전체 보기", response = List.class)
	@RequestMapping(value = "/mylikes", method = RequestMethod.GET)
	public ResponseEntity<List<Code>> getMyLikes(HttpServletRequest rs) throws Exception {
		logger.info("1-------------getMyLikes-----------------------------" + new Date());
		int memberid = 0;
		if(rs.getAttribute("loginMember")!=null ) {
			Member member = (Member) rs.getAttribute("loginMember");
			memberid = member.getId();
		}
		List<Code> codes = codeservice.getMyLikes(memberid);
		return new ResponseEntity<List<Code>>(codes, HttpStatus.OK);
	}
	
	@ApiOperation(value = "code Comment 전체 보기", response = List.class)
	@RequestMapping(value = "/commentcode/{codeid}", method = RequestMethod.GET)
	public ResponseEntity<List<CodeComment>> getCommentPost(@PathVariable int codeid, HttpServletRequest rs) throws Exception {
		logger.info("1-------------getCommentPost-----------------------------" + new Date());
		int memberid = 0;
		if(rs.getAttribute("loginMember")!=null) {
			Member member = (Member) rs.getAttribute("loginMember");
			memberid = member.getId();
		}
		CommentInfo commentinfo = new CommentInfo(codeid,memberid);
		List<CodeComment> comments;
		comments = codeservice.getCommentPost(commentinfo);
		return new ResponseEntity<List<CodeComment>>(comments, HttpStatus.OK);
	}
	
	@ApiOperation(value = "code Comment 추가", response = List.class)
	@RequestMapping(value = "/commentcode", method = RequestMethod.POST)
	public ResponseEntity<List<CodeComment>> addCommentPost(@RequestBody CodeComment codecomment) throws Exception {
		logger.info("1-------------addCommentPost-----------------------------" + new Date());
		codeservice.addCommentPost(codecomment);
		CommentInfo commentinfo = new CommentInfo(codecomment.getCodeid(),codecomment.getUserid());
		List<CodeComment> comments;
		comments = codeservice.getCommentPost(commentinfo);
		return new ResponseEntity<List<CodeComment>>(comments, HttpStatus.OK);
	}
	
	@ApiOperation(value = "code Comment 수정", response = BoolResult.class)
	@RequestMapping(value = "/commentcode", method = RequestMethod.PUT)
	public ResponseEntity<List<CodeComment>> updateCommentPost(@RequestBody CodeComment codecomment, HttpServletRequest rs) throws Exception {
		logger.info("1-------------updateCommentPost-----------------------------" + new Date());
		int memberid = 0;
		if(rs.getAttribute("loginMember")!=null) {
			Member member = (Member) rs.getAttribute("loginMember");
			memberid = member.getId();
		}
		codeservice.updateCommentPost(codecomment);
		CommentInfo commentinfo = new CommentInfo(codecomment.getCodeid(),memberid);
		List<CodeComment> comments;
		comments = codeservice.getCommentPost(commentinfo);
	return new ResponseEntity<List<CodeComment>>(comments, HttpStatus.OK);
	}
	
	@ApiOperation(value = "code Comment 삭제", response = BoolResult.class)
	@RequestMapping(value = "/commentcode/{id}/{codeid}", method = RequestMethod.DELETE)
	public ResponseEntity<List<CodeComment>> deleteCommentPost(@PathVariable int id, @PathVariable int codeid, HttpServletRequest rs) throws Exception {
		logger.info("1-------------deleteCommentPost-----------------------------" + new Date());
		int memberid = 0;
		if(rs.getAttribute("loginMember")!=null) {
			Member member = (Member) rs.getAttribute("loginMember");
			memberid = member.getId();
		}
		codeservice.deleteCommentPost(id);
		CommentInfo commentinfo = new CommentInfo(codeid,memberid);
		List<CodeComment> comments;
		comments = codeservice.getCommentPost(commentinfo);
		return new ResponseEntity<List<CodeComment>>(comments, HttpStatus.OK);
	}
	
	@ApiOperation(value = "code 댓글 수 출력", response = BoolResult.class)
	@RequestMapping(value = "/commentcounts/{codeid}", method = RequestMethod.GET)
	public ResponseEntity<Integer> getCommentCounts(@PathVariable int codeid) throws Exception {
		logger.info("1-------------getCommentCounts-----------------------------" + new Date());
		int counts = codeservice.getCommentCounts(codeid);
		return new ResponseEntity<Integer>(counts, HttpStatus.OK);
	}

	@ApiOperation(value = "code 전체 검색", response = List.class)
	@RequestMapping(value = "/codes/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<List<Code>> getSearchCodes(@PathVariable String keyword) throws Exception {
		logger.info("1-------------getSearchCodes-----------------------------" + new Date());
		String kw = "%"+keyword+"%";
		List<Code> codes = codeservice.getSearchCodes(kw);
		return new ResponseEntity<List<Code>>(codes, HttpStatus.OK);
	}

}
