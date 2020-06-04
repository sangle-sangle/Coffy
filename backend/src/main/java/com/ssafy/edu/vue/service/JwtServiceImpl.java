package com.ssafy.edu.vue.service;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ssafy.edu.vue.controller.MemberController;
import com.ssafy.edu.vue.controller.UnauthorizedException;
import com.ssafy.edu.vue.dto.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Service("jwtService")
public class JwtServiceImpl implements IJwtService{

	private static final String SALT =  "A5isTheBest";
	public static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);
	
	
	@Override
	public String signin(Member data){
		String jwt = Jwts.builder()
						 .setHeaderParam("typ", "JWT")
						 .setHeaderParam("regDate", System.currentTimeMillis())
						// .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
						 .setSubject("로그인토큰")
						 .claim("access-Token", data)
						 .signWith(SignatureAlgorithm.HS256, this.generateKey())
						 .compact();
		return jwt;
	}	
	
	@Override
	public String create(String key, Member data, String subject){
		String jwt = Jwts.builder()
						 .setHeaderParam("typ", "JWT")
						 .setHeaderParam("regDate", System.currentTimeMillis())
						 .setSubject(subject)
						 .claim(key, data)
						 .signWith(SignatureAlgorithm.HS256, this.generateKey())
						 .compact();
		return jwt;
	}	

	private byte[] generateKey(){
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if(logger.isInfoEnabled()){
				e.printStackTrace();
			}else{
				logger.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		
		return key;
	}
	
	@Override
	public boolean isUsable(String jwt) {
		try{
			Jws<Claims> claims = Jwts.parser()
					  .setSigningKey(this.generateKey())
					  .parseClaimsJws(jwt);
			return true;
			
		}catch (Exception e) {
			throw new UnauthorizedException();
		}
	}
	
	@Override
	public Member get(HttpServletRequest req) {
		String jwt = req.getHeader("access-token");
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
						 .setSigningKey(this.generateKey())
						 .parseClaimsJws(jwt);
		} catch (Exception e) {
			System.out.println(e);
			return null;
			//throw new UnauthorizedException();
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get("access-Token");

		String github = null;
		String img = "";
		if(value.get("githubid")!=null) {
			github = value.get("githubid").toString();
		}
		if(value.get("img")!=null) {
			img = value.get("img").toString();
		}
		Member member = new Member(
				Integer.parseInt(value.get("id").toString()),
				value.get("email").toString(),
				value.get("username").toString(),
				null,
				github,
				Integer.parseInt(value.get("auth").toString()),
				value.get("signupdate").toString(),
				img);
		
		return member;
	}
}