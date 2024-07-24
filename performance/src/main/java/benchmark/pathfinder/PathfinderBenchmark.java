package benchmark.pathfinder;


import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import profilers.GcProfiler;
import utils.pathfinder.Position;


@State(Scope.Thread)
public class PathfinderBenchmark {

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .warmupIterations(3)
                .measurementIterations(5)
                .forks(1)
                .shouldDoGC(true)  // Enable GC profiling
                .addProfiler(GcProfiler.class)  // Use your custom GcProfiler
                .build();

        new Runner(options).run();
    }

    @Benchmark
    public void measureTracePathBenchmark(org.openjdk.jmh.infra.Blackhole bh) {

        bh.consume(new Pathfinder().tracePath(new Position(4, 5), new Position(6, 6)));
    }
}
