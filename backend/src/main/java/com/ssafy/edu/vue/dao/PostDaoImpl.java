package com.ssafy.edu.vue.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.vue.dto.ClanPost;

@Repository
public class PostDaoImpl {

	String ns = "ssafy.post.";
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<ClanPost> getPosts() {
		return sqlSession.selectList(ns+"getPosts");
	}

	public ClanPost getPost(int id) {
		return sqlSession.selectOne(ns+"getPost",id);
	}

	public void addPost(ClanPost post) {
		sqlSession.insert(ns+"addPost",post);
	}

	public void updatePost(ClanPost post) {
		sqlSession.update(ns+"updatePost",post);
	}

	public void deletePost(int id) {
		sqlSession.delete(ns+"deletePost",id);
	}

}
