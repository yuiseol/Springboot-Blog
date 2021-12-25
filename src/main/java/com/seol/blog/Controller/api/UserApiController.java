package com.seol.blog.Controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.seol.blog.config.auth.PrincipalDetail;
import com.seol.blog.dto.ResponseDto;
import com.seol.blog.model.User;
import com.seol.blog.service.UserService;


@RestController
public class UserApiController {

	@Autowired
	private UserService userService; 

	@Autowired
	private AuthenticationManager authenticationManager;
	
	//회원가입 로직이 실행되는 부분
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { //username, password, email 들어가고 role 빼고는 자동으로 들어감 role만 강제로 넣어주기
		System.out.println("UserApiController : save 호출됨");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK .value(), 1); //자바오브젝트를 JSON으로 변환해서 리턴(Jackson 라이브러리가 실행)
	}	
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){//@RequestBody가 없으면 key=value, x-www-form-urlencoded
		userService.회원수정(user);
		//여기서는 츠랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음.
		//하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해줄 것임.
		
		//세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK .value(), 1);
	}
}
