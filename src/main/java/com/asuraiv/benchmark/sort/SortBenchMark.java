package com.asuraiv.benchmark.sort;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
public class SortBenchMark {

	private static final int N = 1_000;
	private static List<Integer> testData = new ArrayList<>();

	@Setup
	public static void setup() {
		Random randomGenerator = new Random();
		for (int i = 0; i < N; i++) {
			testData.add(randomGenerator.nextInt(Integer.MAX_VALUE));
		}
		System.out.println("Setup Complete");
	}

	@Benchmark
	public List<Integer> classicSort() {
		List<Integer> copy = new ArrayList<>(testData);
		Collections.sort(copy);
		return copy;
	}

	@Benchmark
	public List<Integer> standardSort() {
		return testData.stream().sorted().collect(Collectors.toList());
	}

	@Benchmark
	public List<Integer> parallelSort() {
		return testData.parallelStream().sorted().collect(Collectors.toList());
	}

	public static void main(String[] args) throws RunnerException {

		Options opt = new OptionsBuilder()
			.include(SortBenchMark.class.getSimpleName())
			.warmupIterations(100)
			.measurementIterations(5).forks(1)
			.jvmArgs("-server", "-Xms2048", "-Xmx2048")
			.addProfiler(GCProfiler.class)
			.addProfiler(StackProfiler.class)
			.build();

		new Runner(opt).run();
	}
}
