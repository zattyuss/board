package com.jafa.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Board {
	private long bno; // 게시물 번호 
	private String title; 
	private String content;
	private String writer; 
	private int replyCnt;
	private long viewCount;
	
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
	
	private List<BoardCatVO> catList;
	// CatList[0].uuid
	// CatList[1]
}