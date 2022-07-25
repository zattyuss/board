package com.jafa.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.jafa.dto.Board;
import com.jafa.dto.BoardCatVO;
import com.jafa.dto.Criteria;

@Service
public interface BoardService {
	List<Board> getList(Criteria criteria);
	Board get(Long bno, boolean isAddCount);
	void register(Board board);
	void modify(Board board);
	void remove(Long bno);
	int totalCount(Criteria criteria);
	List<BoardCatVO> getCatList(Long bno);
	
}
