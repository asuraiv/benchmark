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

private static final int N = 10_000_000;
private static final int I = 10;

private static List<Integer> testData = new ArrayList<>();

static {

	Random random = new Random();

	for(int i = 0; i < N; i++) {
		testData.add(random.nextInt(Integer.MAX_VALUE));
	}
}

public static void main(String[] args) {

	System.out.println("정렬 알고리즘 테스트");

	double startTime = System.currentTimeMillis();

	for (int i = 0; i < I; i++) {
		List<Integer> copiedData = new ArrayList<>(testData);
		Collections.sort(copiedData);
	}

	double endTime = System.currentTimeMillis();
	double timePerOperation = ((endTime - startTime) / I);

	System.out.println(String.format("결과: %.10f op/s", 1 / timePerOperation));
}
}
