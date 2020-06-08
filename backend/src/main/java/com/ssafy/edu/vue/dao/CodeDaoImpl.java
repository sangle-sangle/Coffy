package com.ssafy.edu.vue.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.vue.dto.Code;
import com.ssafy.edu.vue.dto.CodeComment;
import com.ssafy.edu.vue.dto.CommentInfo;
import com.ssafy.edu.vue.dto.LikeCode;

@Repository
public class CodeDaoImpl {

	String ns = "ssafy.code.";
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<Code> getCodes() {
		return sqlSession.selectList(ns+"getCodes");
	}

	public Code getCode(int id) {
		return sqlSession.selectOne(ns+"getCode",id);
	}

	public void addCode(Code code) {
		sqlSession.insert(ns+"addCode",code);
	}

	public void updateCode(Code code) {
		sqlSession.update(ns+"updateCode",code);
	}

	public void deleteCode(int id) {
		sqlSession.delete(ns+"deleteCode",id);
	}

	public void addLikeCode(LikeCode likecode) {
		sqlSession.insert(ns+"addLikeCode", likecode);
	}

	public void deleteLikeCode(LikeCode likecode) {
		sqlSession.delete(ns+"deleteLikeCode", likecode);
	}

	public int getLikeCounts(LikeCode likecode) {
		return sqlSession.selectOne(ns+"getLikeCounts", likecode);
	}

	public int isLike(LikeCode likecode) {
		return sqlSession.selectOne(ns+"isLike", likecode);
	}

	public List<Code> getMyCodes(int memberid) {
		return sqlSession.selectList(ns+"getMyCodes",memberid);
	}

	public List<Code> getMyLikes(int memberid) {
		return sqlSession.selectList(ns+"getMyLikes",memberid);
	}

	public List<CodeComment> getCommentPost(CommentInfo commentinfo) {
		return sqlSession.selectList(ns+"getCommentPost",commentinfo);
	}

	public void addCommentPost(CodeComment codecomment) {
		sqlSession.insert(ns+"addCommentPost", codecomment);
	}

	public void updateCommentPost(CodeComment codecomment) {
		sqlSession.update(ns+"updateCommentPost",codecomment);
	}

	public void deleteCommentPost(int id) {
		sqlSession.delete(ns+"deleteCommentPost", id);
	}

	public int findCodeId(int id) {
		return sqlSession.selectOne(ns+"findCodeId", id);
	}

	public int getCommentCounts(int codeid) {
		return sqlSession.selectOne(ns+"getCommentCounts", codeid);
	}

 	public List<Code> getSearchCodes(String keyword) {
		return sqlSession.selectList(ns+"getSearchCodes", keyword);
	}


}
