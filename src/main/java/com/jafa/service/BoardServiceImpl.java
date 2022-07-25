package com.jafa.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jafa.dao.BoardCatMapper;
import com.jafa.dao.BoardMapper;
import com.jafa.dto.Board;
import com.jafa.dto.BoardCatVO;
import com.jafa.dto.Criteria;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardMapper mapper;
	
	@Autowired
	private BoardCatMapper catMapper;
	
	@Override
	public List<Board> getList(Criteria criteria){
		return mapper.getList(criteria);
	}
	
	@Transactional
	@Override
	public Board get(Long bno, boolean isAddCount) {
		if(isAddCount)mapper.addViewCount(bno);
		return mapper.findByBno(bno);
	}

	@Transactional
	@Override
	public void remove(Long bno) {
		catMapper.deleteAll(bno);
		mapper.delete(bno);
		
	}

	@Override
	@Transactional
	public void register(Board board) {
		mapper.insert(board);
		if(board.getCatList()==null || board.getCatList().size()==0) return;
		board.getCatList().forEach(cat ->{
			cat.setBno(board.getBno());
			catMapper.insert(cat);
		});
	}
	
//	@Override
//	public void update(Board board) {
//		mapper.update(board);
//	}
	
	@Override
	public int totalCount(Criteria criteria) {
		return mapper.totalCount(criteria);
	}

	@Transactional
	@Override
	public void modify(Board board) {
		catMapper.deleteAll(board.getBno());
		mapper.update(board);
		if(board.getCatList()!= null) {
			
		board.getCatList().forEach(cat ->{
			cat.setBno(board.getBno());
			catMapper.insert(cat);
		});
		}
	}

	@Override
	public List<BoardCatVO> getCatList(Long bno) {
		return catMapper.findByBno(bno);
	}
}
