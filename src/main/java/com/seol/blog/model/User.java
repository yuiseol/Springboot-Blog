package com.seol.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴 !!
@Entity  // User클래스가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert //@DynamicInsert insert할때 null 인 필드 제외
public class User {
	
	@Id //Primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. 오라클이면 시퀀스,mysql이면 auto_increment사용한다는것.
	private int id; //시퀀스(오라클) , auto_increment(mysql)
	
	@Column(nullable= false, length=100, unique=true) //null이되면 안되니까 nullable= false (null이될수없다)//unique=true ,yml ddl=create 후 update바꾸면 적용된다.
	private String username; //아이디
	
	@Column(nullable= false, length=100) //123456 => 해쉬로 변경해서 비밀번호 암호화
	private String password;
	
	@Column(nullable= false, length=50)
	private String email;
	
	//@ColumnDefault("'user'")// 이걸쓰면 @DynamicInsert써야함. 어노테이션을 많이 늘어나는건 좋은게 아니라서 없애자.
	//DB는 RoleType이라는게 없다.
	@Enumerated(EnumType.STRING) 
	private RoleType role; //Enum을 쓰는게 좋다 //admin, user, manager 도메인이 정해졌다는건 범위가 정해졌다는 것 //RoleType으로하면 USER,ADMIN만 넣게 지정
	
	@CreationTimestamp //시간이 자동 입력 
	private Timestamp createDate; //회원가입한 시간, 회원정보 수정하는 건 updateDate
}
