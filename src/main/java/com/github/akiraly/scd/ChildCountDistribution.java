package com.github.akiraly.scd;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.RangeMap;

import org.apache.commons.lang3.RandomUtils;

import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Range.closed;
import static com.google.common.collect.Range.closedOpen;
import static java.util.Objects.requireNonNull;

/**
 * Based on http://blogs.discovermagazine.com/gnxp/2012/08/what-is-the-distribution-of-offspring-per-individual/
 */
public class ChildCountDistribution {
  private final RangeMap<Double, Integer> distribution;
  private final Supplier<Double> doubles;

  public ChildCountDistribution() {
    this(() -> RandomUtils.nextDouble(0, 100));
  }

  ChildCountDistribution(Supplier<Double> doubles) {
    this(distribution(12, 24, 53, 75, 87, 93, 96, 98, 100), doubles);
  }

  ChildCountDistribution(RangeMap<Double, Integer> distribution, Supplier<Double> doubles) {
    this.distribution = requireNonNull(distribution);
    this.doubles = requireNonNull(doubles);
  }

  public int nextNumOfChildren() {
    double nextRandom = doubles.get();
    checkState(nextRandom >= 0 && nextRandom <= 100);
    return requireNonNull(distribution.get(nextRandom));
  }

  private static RangeMap<Double, Integer> distribution(double... intervalEnds) {
    ImmutableRangeMap.Builder<Double, Integer> result = ImmutableRangeMap.builder();
    double start = 0;
    for (int fi = 0; fi < intervalEnds.length - 1; fi++) {
      double end = intervalEnds[fi];
      result.put(closedOpen(start, end), fi);
      start = end;
    }
    // the last one is inclusive from both ends
    result.put(closed(start, intervalEnds[intervalEnds.length - 1]), intervalEnds.length - 1);
    return result.build();
  }
}
