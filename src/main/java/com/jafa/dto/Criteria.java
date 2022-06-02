package com.jafa.dto;


public class Criteria {

	private int page;
	private int perPageNum;
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}

	public int getPage() {
		System.out.println("페이지 번호 가져옴");
		return page;
	}

	public void setPage(int page) {
		if (page<=0)this.page=1; 
		else this.page = page;
	}
	

	public int getPerPageNum() {
		return perPageNum;
	}


	public int getPageStart() {
		return (this.page-1)*perPageNum;
	}
	
}