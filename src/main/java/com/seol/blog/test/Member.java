package com.seol.blog.test;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data //getter, setter 동시에 만들고 싶을 때
@NoArgsConstructor //빈생성자
public class Member {
	private  int id;
	private  String username;
	private  String password;
	private  String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
}
