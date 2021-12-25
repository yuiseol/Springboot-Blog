package com.seol.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴 !!
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;
	
	@Column(nullable= false, length=100)
	private String title;
	
	@Lob //대용량데이터 사용할 때
	private String content; //섬머노트 라이브러리사용할것 <html>태그가 섞여서 디자인이 됨.
	
	private int count; //조회수
	
	@ManyToOne(fetch=FetchType.EAGER) //userid라 하면 연관 관계가 없기 때문에,  Board= Many, user= One (한명의 user는 여러개의 게시글을 쓸 수 있다.)
	@JoinColumn(name="userid")
	private User user; //DB는 오브젝트를 저장할 수 없다(FK사용) , 자바는 오브젝트를 저장할 수 있다. 
	//원래는 자바가 DB에 맞히지만(int userid처럼) ORM은 FK키로 찾는게 아니라 오브젝트를 바로 저장할 수 있다.
	//userid라는 컬럼이 만들어지고 User 자바에서는 오브젝트지만 데이터베이스에서는 int값으로(FK) 만들어져야한다.
	
	@OneToMany(mappedBy = "board",fetch=FetchType.EAGER) //mappedBy연관관계 주인이 아니다( 난 FK가 아니에요) DB에 컬럼을 만들지 마세요.나는 보드를 셀렉트할때 조인문을 통해서 값을 얻기 위한 겁니다.
	//@JoinColumn(name="replyid") 실제 Board테이블에 replyid라는 FK가 필요없다
	private List<Reply> reply; 
	
	@CreationTimestamp
	private Timestamp createDate; //데이트 업데이트 or 인서트 될때 자동으로 현재시간이 들어간다.
}
