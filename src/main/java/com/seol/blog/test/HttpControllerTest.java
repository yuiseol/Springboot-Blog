package com.seol.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML 파일)
//@Controller

@RestController //사용자가 요청 -> 응답(Data)
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest";
	
	@GetMapping("/http/lombok") //http://localhost:8080/http/lombok
	public String lombokTest() {
		Member m = Member.builder().username("ssar").password("1234").email("cos@nate.com").build();
		System.out.println(TAG+"gettet:"+m.getUsername());
		m.setUsername("cos");
		System.out.println(TAG+"settet:"+m.getUsername());
		return "lombok test 완료";
	}
	
	//인터넷 브라우저 요청은 무조건 get요청 밖에 할 수 없다.
	//http://localhost:8080/http/get (select)
	@GetMapping("/http/get")
	public String getTest(Member m) {  // MessageConverter(스프링부트)
		return "get 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	//post는 데이터를 ?주소에 붙여서 보내는게 아니라 Body라는 곳에 담아보낸다. 방식이 많지만 2가지만 알아보자.
	//http://localhost:8080/http/post (insert)
	@PostMapping("/http/post")
//	public String postTest(Member m) { // 1. xx-www-form-urlencoded(=<form><input type='''></form>태그방식과같다)키밸류적기. (postman - post)
//		return "post 요청" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
//	public String postTest(@RequestBody String text) { //raw -text(raw타입으로 보냈다는건 text/plain 가장기본타입이라는것) (postman - post) 
//		return "post 요청 : " +  text;
		//apllication/json
		public String postTest(@RequestBody Member m) {
			return "post요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail(); 
		//raw-text일경우 Member m이 맵핑이 잘안됨. apllication/json은 자동으로 파싱해서 object로 넣어준다. 이일을 MessageConverter(스프링부트)라는애가 한다.
	}
	//http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) { //@RequestBody를 통해서 오브젝트를 매핑해서 받을 수 있다.
		return "put 요청 :"  + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
		//Body - raw - json ({"id":1, "password":5555} id가1인애 비밀번호 5555로 수정해줘)
	}
	//http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
