package com.jafa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Criteria {

	private int page;
	private int perPageNum;
	
	private String type;
	private String keyword;
	
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}


	public void setPage(int page) {
		if (page<=0)this.page=1; 
		else this.page = page;
	}
	
	// TC => {T,C}
	// WC => {W,C}
	// type.split("#") LEE#WCC#BB => {LEE,WCC,BB}
	public String[] getTypeCollection() {
		return this.type != null ? type.split("") : new String[]{};
	}
	
	public int getPageStart() {
		return (this.page-1)*perPageNum;
	}
	
}