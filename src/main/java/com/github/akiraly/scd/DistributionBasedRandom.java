package com.github.akiraly.scd;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Maps;
import com.google.common.collect.RangeMap;

import org.apache.commons.math3.random.RandomGenerator;

import java.util.Map;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Range.closedOpen;
import static java.util.Objects.requireNonNull;

class DistributionBasedRandom<T> {
  private final RangeMap<Double, T> distribution;
  private final RandomGenerator randomGenerator;

  DistributionBasedRandom(RandomGenerator randomGenerator, RangeMap<Double, T> distribution) {
    this.randomGenerator = requireNonNull(randomGenerator);
    this.distribution = requireNonNull(distribution);
  }

  static RangeMap<Double, Integer> newDistribution(double... ratios) {
    ImmutableList.Builder<Map.Entry<Double, Integer>> ratiosToValues = ImmutableList.builder();
    for (int fi = 0; fi < requireNonNull(ratios).length; fi++) {
      ratiosToValues = ratiosToValues.add(Maps.immutableEntry(ratios[fi], fi));
    }
    return newDistribution(ratiosToValues.build());
  }

  static <T> RangeMap<Double, T> newDistribution(Map<Double, T> ratiosToValues) {
    return newDistribution(ratiosToValues.entrySet());
  }

  private static <T> RangeMap<Double, T> newDistribution(Iterable<Map.Entry<Double, T>> ratiosToValues) {
    ImmutableRangeMap.Builder<Double, T> result = ImmutableRangeMap.builder();
    double total = 0;
    for (Map.Entry<Double, T> r : ratiosToValues) {
      double ratio = r.getKey();
      checkState(ratio > 0 && ratio <= 1, "%s", ratio);
      double start = total;
      total += ratio;
      checkState(total <= 1, "%s", total);
      result = result.put(closedOpen(start, total), r.getValue());
    }
    return result.build();
  }

  T next() {
    double nextDouble = randomGenerator.nextDouble();
    checkState(nextDouble >= 0 && nextDouble <= 1);
    return requireNonNull(distribution.get(nextDouble));
  }
}
