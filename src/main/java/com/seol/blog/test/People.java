package com.seol.blog.test;

public class People {
	private int hungryState = 50; //100 private -> 변수에 다이렉트로 접근 못하도록.
	
	public void eat() { //함수를 통해서 접근할 수 있도록.
		hungryState += 10;
	}
}
