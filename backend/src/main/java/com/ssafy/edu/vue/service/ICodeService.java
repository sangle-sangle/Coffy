package com.ssafy.edu.vue.service;

import java.util.List;

import com.ssafy.edu.vue.dto.Code;
import com.ssafy.edu.vue.dto.LikeCode;

public interface ICodeService {

	List<Code> getCodes();

	Code getCode(int id);

	void addCode(Code code);

	void updateCode(Code code);

	void deleteCode(int id);

	void addLikeCode(LikeCode likecode);

	void deleteLikeCode(LikeCode likecode);
	
	int getLikeCounts(LikeCode likecode);

}
