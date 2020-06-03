package com.ssafy.edu.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.edu.vue.dao.CodeDaoImpl;
import com.ssafy.edu.vue.dto.Code;
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
	public void addLikeCode(LikeCode likecode) {
		codedao.addLikeCode(likecode);
	}
	
	@Override
	public void deleteLikeCode(LikeCode likecode) {
		codedao.deleteLikeCode(likecode);
	}
	
	@Override
	@Transactional
	public int getLikeCounts(LikeCode likecode) {
		return codedao.getLikeCounts(likecode);
	}

}
