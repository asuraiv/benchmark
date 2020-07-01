package com.asuraiv.benchmark.sort;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.profile.HotspotCompilationProfiler;
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
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class SortBenchMark {

	private static final int N = 1000_000;
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

	public static void main(String[] args) throws RunnerException {

		Options opt = new OptionsBuilder()
			.include(SortBenchMark.class.getSimpleName())
			.jvmArgs("-server", "-Xms2048m", "-Xmx2048m")
			.addProfiler(HotspotCompilationProfiler.class)
			.build();

		new Runner(opt).run();
	}
}
