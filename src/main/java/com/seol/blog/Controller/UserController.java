package com.seol.blog.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/**허용
// 그냥 주소가 /이면 index.jsp허용
//static이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

	@GetMapping("/auth/joinForm") //회원가입을 하러 들어가는데 인증이되있을 필요는 없으니까 인증이 필요없는 곳엔 auth
	public String joinForm() {	
		return "user/joinForm";
	}	
	@GetMapping("/auth/loginForm")
	public String loginForm() {		
		return "user/loginForm";
	}
	@GetMapping("/user/updateForm")
	public String updateForm() {		
		return "user/updateForm";
	}
}
