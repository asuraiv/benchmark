package com.asuraiv.benchmark.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
	java option:
	-Xms2048m -Xmx2048m -XX:+PrintCompilation -XX:+PrintCodeCache -verbose:gc
 */
public class SortBenchMarkWithoutJMH {

	private static final int N = 1000_000;
	private static final int I = 5;

	private static List<Integer> testData = new ArrayList<>();

	static {

		Random random = new Random();

		for(int i = 0; i < N; i++) {
			testData.add(random.nextInt(Integer.MAX_VALUE));
		}
	}

	public static void main(String[] args) {

		System.out.println("정렬 알고리즘 테스트");

		double startTime = System.nanoTime();

		for (int i = 0; i < I; i++) {
			List<Integer> copiedData = new ArrayList<>(testData);
			Collections.sort(copiedData);
		}

		double endTime = System.nanoTime();
		double timePerOperation = ((endTime - startTime) / (1_000_000_000L * I));

		System.out.println("결과: " + (1 / timePerOperation) + " op/s");
	}
}
