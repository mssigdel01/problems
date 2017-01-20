package com.bettercloud.challenges._2017._01Jan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * If you want to test the perfomance of another solution:
 *
 *      1. Implement a `public static testMyTechnique()` method
 *      2. Add a new enum value that
 *
 * Created by davidesposito on 1/13/17.
 */
public class _011317_RandomValuesPerformance {

    private static ObjectMapper mapper = new ObjectMapper();

    public static Results test(int count, Consumer<Integer> r) {
        long startTime = System.currentTimeMillis();

        r.accept(count);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        return Results.builder()
                .count(count)
                .duration(duration)
                .throughput((double)count / duration)
                .build();
    }

    public static Consumer<Integer> testFor() {
        return (count) -> {
            List<String> vals = Lists.newArrayList();
            for (int i=0;i<count;i++) {
                vals.add(UUID.randomUUID().toString());
            }
        };
    }

    public static Consumer<Integer> testRays() {
        return (count) -> {
            List<String> vals = Lists.newArrayList();
            IntStream.range(0, count).forEach(i -> {
                vals.add(UUID.randomUUID().toString());
            });
        };
    }

    public static Consumer<Integer> testDriggers() {
        return (count) -> {
            List<String> vals = IntStream.range(0, count)
                    .boxed()
                    .map(i -> UUID.randomUUID().toString())
                    .collect(Collectors.toList());
        };
    }


    public static Consumer<Integer> testDriggersParallel() {
        return (count) -> {
            ForkJoinPool forkJoinPool = new ForkJoinPool(5);
            try {
                forkJoinPool.submit(() -> {
                        List<String> vals = IntStream.range(0, count)
                                .boxed()
                                .parallel()
                                .map(i -> UUID.randomUUID().toString())
                                .collect(Collectors.toList());
                }).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        };
    }

    public static void aggregate(Aggregates agg) {
        Results res = test(agg.count, agg.strat.work);
        agg.duration.add(res.duration);
        agg.throughput.add(res.throughput);
    }

    public static void aggregate(int iters, int count) {
        Arrays.stream(Strategy.values())
                .forEach(strat -> {
                    Aggregates agg = new Aggregates(strat, count);
                    for (int i=0;i<iters;i++) {
                        aggregate(agg);
                    }
                    try {
                        System.out.println(mapper.writeValueAsString(agg));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
    }

    public static void main(String[] args) {
        System.out.println("SMALL (Size 10)");
        aggregate(100, 10);

        System.out.println("\nMedium (Size 30)");
        aggregate(100, 30);

        System.out.println("\nLarge (Size 150)");
        aggregate(100, 150);

        System.out.println("\nX-Large (Size 1,000)");
        aggregate(20, 1_000);

        System.out.println("\nMega (Size 100,000)");
        aggregate(8, 100_000);

//        System.out.println("\nSupah (Size 2,000,000)");
//        aggragate(3, 2_000_000);
    }

    private enum Strategy {
        FOR(testFor()),
        RAY(testRays()),
        DRIGGERS(testDriggers()),
        DRIGGERES_PARALLEL(testDriggersParallel());

        private Consumer<Integer> work;

         Strategy(Consumer<Integer> work) {
            this.work = work;
        }
    }

    @Data
    @Builder
    private static class Results {
        private int count;
        private long duration;
        private double throughput;
    }

    @Data
    private static class Aggregates {
        private Strategy strat;
        private int count;
        private Stat duration = new Stat();
        private Stat throughput = new Stat();

        public Aggregates(Strategy strat, int count) {
            this.strat = strat;
            this.count = count;
        }
    }

    @Data
    private static class Stat {
        private int count;
        private double min = Double.MAX_VALUE;
        private double max = Double.MIN_VALUE;
        private double avg = 0;

        public void add(double val) {
            if (val < min) {
                this.min = val;
            }
            if (val > max) {
                this.max = val;
            }
            avg = ((avg * count) + val) / (count + 1);
            count++;
        }
    }
}
