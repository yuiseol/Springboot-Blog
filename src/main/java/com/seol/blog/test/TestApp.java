
package com.seol.blog.test;

public class TestApp {
	
	public static void main(String[] args) {
		People 홍길동 = new People();
		홍길동.eat();  
	}
}
//객체지향에서는 변수를 private으로 만든다. 그리고 변수는 메서드의 의해서 변경이 되게 설계되어야한다.그래서 모델(클래스)을 만들때는 
//변수는 private,  이 private 변수를 수정할 수 있게 getter, setter를 만들어야한다. 함수를 통해서 접근 할 수있도록.