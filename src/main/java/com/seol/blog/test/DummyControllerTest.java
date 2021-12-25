package com.seol.blog.test;


import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.seol.blog.model.RoleType;
import com.seol.blog.model.User;
import com.seol.blog.repository.UserRepository;

//html 파일이 아니라 data를 리턴해주는 controller = RestController
@RestController //페이지로 이동하는게 아니라 회원가입이 잘됬다 안됬다 데이터만 리턴 해주는. 응답만해줄수있게.
public class DummyControllerTest {
	
	@Autowired //의존성 주입. UserRepository 타입으로 스프링이 관리하고 있는 객체가 있다면은 userRepository에 쏙 넣어줘라는 뜻! UserRepository가 떠있다. UserRepository.java의 UserRepository 인터페이스가 메모리에 띄워줘서. 사용하면된다.
	private UserRepository userRepository; //스프링이 @RestController 읽어서 DummyControllerTest를 메모리에 띄어줄때 userRepository는 null이지만 @Autowired를 해주면 메모리에 같이 뜬다. 
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제 되었습니다. id: " + id;
	}
	
	//email, password 수정 
	@Transactional //더티 체킹 
	@PutMapping("/dummy/user/{id}")
	private User updateUser(@PathVariable int id, @RequestBody User requestUser) { //json 데이터 받으려면 @RequestBody 필요//json데이터를 요청 =>스프링이 Java Object로 변환해서 받아줘요(MessageConverter의 Jackson라이브러리가 변환해서 받아줘요)
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		}); //id를 찾는데 못찾으면 어떤함수를 수행해라.
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user); //save함수는 id를 전달하지 않으면 insert를 해주고 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고 id를 전달하면 해당 id에 대한 데잍터가 없으면 insert를 해요.
		return user;
	}
	
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list (){
		return userRepository.findAll(); //findAll List 타입
	}
	
	//한페이지당 2건에 데이터를 리턴받아 볼 예정
	//http://localhost:8000/blog/dummy/user // ?page=0 한페이지씩 볼때  ?page=1 그다음페이지 
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent(); // 밑에세부내용 필요없다면 getContent() ,,if절로 isFirst, isLast() 사용도가능
		return pagingUser;
	}
	
	//{id} 주소로 파마레터를 전달 받을 수 있음.
	//http://localhost:8000/blog/dummy/user/5
	@GetMapping("/dummy/user/{id}")
	 public User detail(@PathVariable int id) {
		//user/4을 찾으면 내가 데어터베이스에서 못 찾아오게 되면 user가 null이 될 것 아냐?
		//그럼 return null이 리턴이 되자나.. 그럼 프로그램에 문제가 있지 않겠니?
		//optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해!!
		 //findById타입이 Optional , null이 리턴될리 없으면 .get() 붙이면 됨, 너가 객체를 만들어서 넣어줘 그럼 null이 아니잖아 .orElseGet() new supplier<User>타입넣을수 있다, 
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id:" + id);
			}
		});
		//요청 : 웹브라우저(자바스크립트나 html만 이해할 수 있다.)
		//user 객체 = 자바 오브젝트
		//변환 (웹브라우저가 이해할 수 있는 데이터) -> json(Gson 라이브러리 사용)
		//스트링부트는 = MessageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면  MessageConverter가 Jackson 라이브러리를 호출해서 
		//user 오브젝트를 json으로 변환해서 브라우저에게  던져줍니다.
		return user;
	 }
//람다식
//	User user = userRepository.findById(id).orElseThrow(()-> {
//			return new IllegalArgumentException("해당 유저는 없습니다.");
//	});
	
	
	//http://localhost:8000/blog/dummy/join (요청)
	//http의  body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) { //key=value형태의 데이터를 받아준다(약속된규칙) //String username, String password, String email
		System.out.println("id:" + user.getId()); 
		System.out.println("username:" + user.getUsername());
		System.out.println("password:" + user.getPassword());
		System.out.println("email:" + user.getEmail());
		System.out.println("role:" + user.getRole()); 
		System.out.println("email:" + user.getCreateDate());
		
		user.setRole(RoleType.USER); //@ColumnDefault("'user'") 이걸 주석처리했기때문에. 사용
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}

