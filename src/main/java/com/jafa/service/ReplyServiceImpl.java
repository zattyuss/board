package com.jafa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jafa.dao.BoardMapper;
import com.jafa.dao.ReplyMapper;
import com.jafa.dto.Criteria;
import com.jafa.dto.ReplyVO;

import lombok.Setter;


@Service
public class ReplyServiceImpl implements ReplyService {

	private final static int REPLY_ADD = 1;
	private final static int REPLY_DEL = -1;
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Autowired
	private BoardMapper boardMapper;
	

	public ReplyServiceImpl(ReplyMapper mapper) {
		this.mapper = mapper;
	}
	
	@Override
	public List<ReplyVO> getListWithPaging(Criteria criteria, Long bno){
		return mapper.getListWithPaging(criteria, bno);
	}

	@Override
	public int register(ReplyVO vo) {
		boardMapper.updateReplyCnt(vo.getBno(), REPLY_ADD);
		return mapper.insert(vo);
	}

	@Override
	public ReplyVO get(Long rno) {
		return mapper.read(rno);
	}

	@Override
	public int remove(Long bno) {
		boardMapper.updateReplyCnt(mapper.read(bno).getBno(), REPLY_DEL);
		return mapper.delete(bno);
	}

	@Override
	public int modify(ReplyVO vo) {
		return mapper.update(vo);
	}



	

}
