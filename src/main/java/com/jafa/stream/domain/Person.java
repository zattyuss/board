package com.jafa.stream.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Person {
	
	private Long id;
	private String name;
	private int age;
}
