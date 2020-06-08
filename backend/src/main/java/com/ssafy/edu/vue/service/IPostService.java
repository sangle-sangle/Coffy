package com.ssafy.edu.vue.service;

import java.util.List;

import com.ssafy.edu.vue.dto.ClanPost;

public interface IPostService {

	List<ClanPost> getPosts();

	ClanPost getPost(int id);

	void addPost(ClanPost post);

	void updatePost(ClanPost post);

	void deletePost(int id);

}
