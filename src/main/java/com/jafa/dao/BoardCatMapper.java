package com.jafa.dao;

import java.util.List;

import com.jafa.dto.BoardCatVO;

public interface BoardCatMapper {

	void insert(BoardCatVO vo);
	void delete(String uuid);
	List<BoardCatVO> findByBno(Long bno);
	void deleteAll(Long bno);
	
	List<BoardCatVO> getOldFiles();
}
