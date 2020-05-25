package com.ssafy.edu.vue.service;

import java.util.List;

import com.ssafy.edu.vue.dto.Code;

public interface ICodeService {

	List<Code> getCodes();

	Code getCode(int id);

	void addCode(Code code);

	void updateCode(Code code);

	void deleteCode(int id);

}
