package com.asuraiv.benchmark.fibonacci;

// -XX:+PrintCompilation 추가하여 컴파일 현황 체
public class FibonacciTest {

	private static final int N_LOOPS = 1000;

	public static void main(String[] args) {

		long then = System.currentTimeMillis();
		int l;
		for (int i = 0; i < N_LOOPS; i++) {
			l = fib(40);
		}
		long now = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (now - then));
	}

	private static int fib(int n) {

		if(n == 0) {
			return 0;
		}

		if(n == 1) {
			return 1;
		}

		return fib(n - 1) + fib(n - 2);
	}
}
