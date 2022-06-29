package com.jafa.service;

import java.util.List;


import com.jafa.dto.Criteria;
import com.jafa.dto.ReplyVO;

public interface ReplyService {

	List<ReplyVO> getListWithPaging(Criteria criteria, Long bno);
	
	int register(ReplyVO vo);
	ReplyVO get(Long rno);
	int remove(Long bno);
	int modify(ReplyVO vo);
}
