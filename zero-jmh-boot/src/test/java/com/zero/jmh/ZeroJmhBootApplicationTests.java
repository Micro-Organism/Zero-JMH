package com.zero.jmh;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@State(Scope.Thread)
class ZeroJmhBootApplicationTests {

    private String str1;
    private String str2;
    private List<String> list;

    @Setup
    public void setup1() {
        list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(UUID.randomUUID().toString());
        }
    }

    @TearDown
    public void tearDown1() {
        list = null;
    }

    @Benchmark
    public void testSort() {
        Collections.sort(list);
    }

    @Test
    public void testMyBenchmark() throws Exception {
        Options options = new OptionsBuilder()
                .include(ZeroJmhBootApplicationTests.class.getSimpleName())
                .forks(1)
                .threads(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .mode(Mode.Throughput)
                .build();

        new Runner(options).run();
    }

    @Setup
    public void setup2() {
        str1 = "Hello";
        str2 = "World";
    }

    @TearDown
    public void tearDown2() {
        str1 = null;
        str2 = null;
    }

    @Benchmark
    public String testStringConcat() {
        return str1 + " " + str2;
    }

    @Test
    public void testStringConcatBenchmark() throws Exception {
        Options options = new OptionsBuilder()
                .include(ZeroJmhBootApplicationTests.class.getSimpleName())
                .forks(1)
                .threads(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .mode(Mode.AverageTime)
                .build();

        new Runner(options).run();
    }

}
