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

import com.ssafy.edu.vue.dto.ClanPost;
import com.ssafy.edu.vue.dto.CommentInfo;
import com.ssafy.edu.vue.dto.Member;
import com.ssafy.edu.vue.help.BoolResult;
import com.ssafy.edu.vue.service.IPostService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000, exposedHeaders = "access-token", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class PostController {
	public static final Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private IPostService postservice;
	
	@ApiOperation(value = "clan post 전체 보기", response = List.class)
	@RequestMapping(value = "/posts/{clanid}", method = RequestMethod.GET)
	public ResponseEntity<List<ClanPost>> getPosts() throws Exception {
		logger.info("1-------------getPosts-----------------------------" + new Date());
		// 유저 클랜아이디와 들어온 클랜아이디 같은지 확인 후 같다면 getPosts
		// 아니면 그냥 클랜페이지는 클랜멤버만 들어갈 수 있게
		List<ClanPost> posts = postservice.getPosts();
		return new ResponseEntity<List<ClanPost>>(posts, HttpStatus.OK);
	}
	
	@ApiOperation(value = "clan post 상세 보기", response = List.class)
	@RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClanPost> getPost(@PathVariable int id) throws Exception {
		logger.info("1-------------getPost-----------------------------" + new Date());
//		int memberid = 0;
//		if(rs.getAttribute("loginMember")!=null ) {
//			Member member = (Member) rs.getAttribute("loginMember");
//			memberid = member.getId();
//		}
		ClanPost post = postservice.getPost(id);
		return new ResponseEntity<ClanPost>(post, HttpStatus.OK);
	}
	
	@ApiOperation(value = "post 추가", response = List.class)
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public ResponseEntity<BoolResult> addPost(@RequestBody ClanPost post) throws Exception {
		logger.info("1-------------addPost-----------------------------" + new Date());
		postservice.addPost(post);
		BoolResult nr=new BoolResult();
   		nr.setName("addPost");
   		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "post 수정", response = BoolResult.class)
	@RequestMapping(value = "/post", method = RequestMethod.PUT)
	public ResponseEntity<BoolResult> updatePost(@RequestBody ClanPost post) throws Exception {
		logger.info("1-------------updatePost-----------------------------" + new Date());
		postservice.updatePost(post);
		BoolResult nr=new BoolResult();
   		nr.setName("updatePost");
   		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "post 삭제", response = BoolResult.class)
	@RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<BoolResult> deletePost(@PathVariable int id) throws Exception {
		logger.info("1-------------deletePost-----------------------------" + new Date());
		postservice.deletePost(id);
		BoolResult nr=new BoolResult();
   		nr.setName("deletePost");
   		nr.setState("succ");
		return new ResponseEntity<BoolResult>(nr, HttpStatus.OK);
	}
}
