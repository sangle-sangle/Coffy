package com.ssafy.edu.vue.service;

import java.util.List;

import com.ssafy.edu.vue.dto.Member;

public interface IMemberService {

	List<Member> getMemberList();

	Member getMember(int memberid);
	
	void addMember(Member member);

	void updateMember(Member member);

	void deleteMember(int memberid);

	int checkDelflag(String email);

	Member checkLogin(Member member);

	int checkEmail(String email);

	int checkUsername(String username);

	void updatePassword(Member member);

	void updateMemberAuth(Member member);

	int checkUsers();

	List<Member> getClanMembers(int clanid);

	void deleteClanMember(int id);

}
