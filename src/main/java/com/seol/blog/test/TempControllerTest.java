package com.seol.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

		//http://localhost:8000/blog/temp/home
		@GetMapping("/temp/home")
		public String tempHome() {
			System.out.println("tempHome()");
			//파일리턴 기본경로 : src/main/resources/static
			//리턴명을 : /home.html
			//풀경로 : src/main/resources/static/home.html
			return  "home.html";
		}
		@GetMapping("temp/img")
		public String templmg() {
			return "/a.png"; //a.png 저장해놓을걸 리턴. 브라우저가 인식할수 있는 파일 찾아줌
		}
		@GetMapping("temp/jsp")
		public String tempJsp() {
			return "/test.jsp"; //못찾는다. jsp는 정적이 아닌 동적 자바파일이기 때문에 못찾는다. 컴파일이 일어나는 파일 
}
}