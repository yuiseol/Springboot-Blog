package com.seol.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder // 빌더 패턴 !!
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Reply {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id; //PK
	
	@Column(nullable=false, length=200)
	private String content;
	
	
	//어느 게시글에 있는 답변인가.
	@ManyToOne //여러개의 답변은 하나의 게시글에 존재할 수 있다.
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne //연관관계
	@JoinColumn(name="userId")
	private User user; //답변을 누가 적었는지 알아야하니까 
	
	@CreationTimestamp
	private Timestamp createDate;
}
