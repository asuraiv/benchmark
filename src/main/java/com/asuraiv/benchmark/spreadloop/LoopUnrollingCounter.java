package com.asuraiv.benchmark.spreadloop;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Fork(1)
public class LoopUnrollingCounter {

	private static final int MAX = 1_000_000;
	private long[] data = new long[MAX];

	@Setup
	public void createData() {
		Random random = new Random();
		for (int i = 0; i < MAX; i++) {
			data[i] = random.nextLong();
		}
	}

	@Benchmark
	public long intStride1() {
		long sum = 0;
		for (int i = 0; i < MAX; i++) {
			sum += data[i];
		}
		return sum;
	}

	@Benchmark
	public long longStride1() {
		long sum = 0;
		for (long i = 0; i < MAX; i++) {
			sum += data[(int)i];
		}
		return sum;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
			.include(LoopUnrollingCounter.class.getSimpleName())
			.jvmArgs("-server", "-Xms2048m", "-Xmx2048m")
//			.addProfiler(StackProfiler.class)
			.build();
		new Runner(opt).run();
	}
}
