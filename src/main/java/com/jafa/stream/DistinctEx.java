package com.jafa.stream;

import java.util.Arrays;

public class DistinctEx {
	public static void main(String[] args) {
		String[] test = {"1", "1", "1", "2", "2"};
		Arrays.stream(test).distinct().forEach(System.out::println);
	}
}
