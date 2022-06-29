package com.jafa.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jafa.config.AppTest;
import com.jafa.dto.Criteria;
import com.jafa.dto.ReplyVO;

public class ReplyMapperTest extends AppTest{

	@Autowired
	ReplyMapper mapper;
	
	@Test
	@Ignore
	public void test() {
		System.out.println((mapper.getListAll()));
	}
	
	@Test
	@Ignore
	public void insertTest() {
		ReplyVO vo = new ReplyVO();
		vo.setBno(1L);
		vo.setReply("땅뚱 땅뚱이");
		vo.setReplyer("땅뚜");
		int result = mapper.insert(vo);
		System.out.println("뭐가 찍히노 ?  : " + result);
	}
	
	@Test
	@Ignore
	public void findByRnoTest() {
		System.out.println(mapper.read(4L));
	}
	
	@Test
	@Ignore
	public void deleteTest() {
		mapper.delete(2L);
	}
	
	@Ignore
	@Test
	public void updateTest() {
		ReplyVO vo = new ReplyVO();
		vo.setRno(4L);
		vo.setReply("땅뚱 땅뚱2");
		mapper.update(vo);
	}
	
	@Test
	public void getListWithPagingTest() {
		List<ReplyVO> listWithPaging = mapper.getListWithPaging(new Criteria(), 1L);
		System.out.println(listWithPaging);
	}
	
	
	
}
