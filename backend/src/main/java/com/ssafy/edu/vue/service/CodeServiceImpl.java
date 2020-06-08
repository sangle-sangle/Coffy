package com.ssafy.edu.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.edu.vue.dao.CodeDaoImpl;
import com.ssafy.edu.vue.dto.Code;
import com.ssafy.edu.vue.dto.CodeComment;
import com.ssafy.edu.vue.dto.CommentInfo;
import com.ssafy.edu.vue.dto.LikeCode;

@Service
public class CodeServiceImpl implements ICodeService {

	@Autowired
	private CodeDaoImpl codedao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Code> getCodes() {
		return codedao.getCodes();
	}

	@Override
	@Transactional(readOnly = true)
	public Code getCode(int id) {
		return codedao.getCode(id);
	}

	@Override
	@Transactional
	public void addCode(Code code) {
		codedao.addCode(code);
	}

	@Override
	@Transactional
	public void updateCode(Code code) {
		codedao.updateCode(code);
	}

	@Override
	@Transactional
	public void deleteCode(int id) {
		codedao.deleteCode(id);
	}
	
	@Override
	@Transactional
	public void addLikeCode(LikeCode likecode) {
		codedao.addLikeCode(likecode);
	}
	
	@Override
	@Transactional
	public void deleteLikeCode(LikeCode likecode) {
		codedao.deleteLikeCode(likecode);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int getLikeCounts(LikeCode likecode) {
		return codedao.getLikeCounts(likecode);
	}

	@Override
	@Transactional(readOnly = true)
	public int isLike(LikeCode likecode) {
		return codedao.isLike(likecode);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Code> getMyCodes(int memberid) {
		return codedao.getMyCodes(memberid);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Code> getMyLikes(int memberid) {
		return codedao.getMyLikes(memberid);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CodeComment> getCommentPost(CommentInfo commentinfo) {
		return codedao.getCommentPost(commentinfo);
	}

	@Override
	@Transactional
	public void addCommentPost(CodeComment codecomment) {
		codedao.addCommentPost(codecomment);
	}

	@Override
	@Transactional
	public void updateCommentPost(CodeComment codecomment) {
		codedao.updateCommentPost(codecomment);
	}

	@Override
	@Transactional
	public void deleteCommentPost(int id) {
		codedao.deleteCommentPost(id);
	}

	@Override
	@Transactional(readOnly = true)
	public int getCommentCounts(int codeid) {
		return codedao.getCommentCounts(codeid);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Code> getSearchCodes(String keyword) {
		return codedao.getSearchCodes(keyword);
	}

}
