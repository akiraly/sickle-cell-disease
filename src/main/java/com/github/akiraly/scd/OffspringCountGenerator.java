package com.github.akiraly.scd;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.math3.random.RandomGenerator;

import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

class OffspringCountGenerator {
  private final Map<SCDLevel, Float> countMultiplier;
  private final DistributionBasedRandom<Integer> distribution;

  OffspringCountGenerator(RandomGenerator randomGenerator, float carrierCountMultiplier, float infectedCountMultiplier) {
    this(randomGenerator, newCountMuliplier(carrierCountMultiplier, infectedCountMultiplier));
  }

  private OffspringCountGenerator(RandomGenerator randomGenerator, Map<SCDLevel, Float> countMuliplier) {
    this(countMuliplier, new DistributionBasedRandom<>(randomGenerator,
        // Based on http://blogs.discovermagazine.com/gnxp/2012/08/what-is-the-distribution-of-offspring-per-individual/
        DistributionBasedRandom.newDistribution(0.12, 0.12, 0.29, 0.22, 0.12, 0.06, 0.03, 0.02, 0.02)));
  }

  private OffspringCountGenerator(Map<SCDLevel, Float> countMultiplier, DistributionBasedRandom<Integer> distribution) {
    countMultiplier.values().forEach(v -> checkState(v >= 0));
    this.countMultiplier = countMultiplier;
    this.distribution = distribution;
  }

  private static Map<SCDLevel, Float> newCountMuliplier(float carrierCountMultiplier, float infectedCountMultiplier) {
    return ImmutableMap.of(SCDLevel.Carrier, carrierCountMultiplier, SCDLevel.Infected, infectedCountMultiplier);
  }

  Integer nextOffspringCount(SCDLevel scdLevel) {
    return (int) (countMultiplier.getOrDefault(scdLevel, 1f) * distribution.next());
  }
}
