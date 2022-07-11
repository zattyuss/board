package com.jafa.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jafa.stream.domain.Person;

public class StreamMap {
	public static void main(String[] args) {
		List<Person> personList = new ArrayList<>();
		personList.add(new Person(1L, "홍길동", 10));
		personList.add(new Person(2L, "James", 15));
		personList.add(new Person(3L, "바보", 11));
		
		List<String> personName = personList.stream()
			.map( p ->  p.getName()+ "_2022/07/06")
			.collect(Collectors.toList());
			System.out.println(personName);
		
		List<Integer> personAge = personList.stream()
		.map(Person::getAge)
		.collect(Collectors.toList());
		System.out.println(personAge);
		
		long count = personList.stream()
			.map(Person::getAge) 
			.filter(age -> age>= 11) 
			.count();
		System.out.println(count);
	}
}
