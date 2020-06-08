package com.ssafy.edu.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.edu.vue.dao.PostDaoImpl;
import com.ssafy.edu.vue.dto.ClanPost;

@Service
public class PostServiceImpl implements IPostService {

	@Autowired
	private PostDaoImpl postdao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ClanPost> getPosts() {
		return postdao.getPosts();
	}

	@Override
	@Transactional(readOnly = true)
	public ClanPost getPost(int id) {
		return postdao.getPost(id);
	}

	@Override
	@Transactional
	public void addPost(ClanPost post) {
		postdao.addPost(post);
	}

	@Override
	@Transactional
	public void updatePost(ClanPost post) {
		postdao.updatePost(post);
	}

	@Override
	@Transactional
	public void deletePost(int id) {
		postdao.deletePost(id);
	}

}
