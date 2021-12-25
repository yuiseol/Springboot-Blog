package com.seol.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seol.blog.model.Board;


public interface BoardRepository extends JpaRepository<Board, Integer>{ 

}
