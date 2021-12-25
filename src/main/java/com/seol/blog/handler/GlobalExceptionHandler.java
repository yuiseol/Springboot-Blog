package com.seol.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.seol.blog.dto.ResponseDto;

@ControllerAdvice //어디서든 Exception이 발생해도 이쪽으로 오도록 1.
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=IllegalArgumentException.class) // 2. //IllegalArgumentException 말고 다른 Exception을 걸어도 된다.
	public ResponseDto<String> handleArgumentException(IllegalArgumentException e) {//Exception이 발생했을 때 이 함수가 발생하도록
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value() ,  e.getMessage());
	}
}
