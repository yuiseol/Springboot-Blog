package com.seol.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seol.blog.model.User;

//DAO (JSP로 치면 Data Access Object 라고 생각하면 된다.)
//자동으로 bean등록이 된다. 
//@Repository //생략가능하다.
//해당 JpaRepository는 User테이블이 관리하는 Repository다. User테이블의 프라이머리키는 Integer이다. 
public interface UserRepository extends JpaRepository<User, Integer>{ //JpaRepository는 findAll()이라는 함수를 들고있다. User테이블이 들고 있는 모든 행을 다 리턴해 .
	//SELECT * FROM user WHERE username =1?;
	Optional<User> findByUsername(String username);
	
	
}


//로그인을 위한 함수
//JPA Naming 쿼리 전략
//findByUsernameAndPassword이 함수는 JPA함수 아님 실제로 없음, 내가 이름을 이렇게 하면 SELECT * FROM user WHERE username = ? AND password = ?; 쿼리가 자동으로 실행 
//SELECT * FROM user WHERE username = ? AND password = ?; //?에 첫번째 파라메터 , 두번째 파라메터 들어감
//User findByUsernameAndPassword(String username, String password);

//위에와 동일
//	@Query(value="SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery= true)
//	User login(String username, String password); 