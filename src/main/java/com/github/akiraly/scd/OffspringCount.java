package com.github.akiraly.scd;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

public class OffspringCount {
  private final Map<SCDLevel, Float> ratio;
  private final OffspringCountDistribution distribution;

  public OffspringCount(OffspringCountDistribution distribution, float carrierCountRatio, float infectedCountRatio) {
    this(distribution, ImmutableMap.of(SCDLevel.Carrier, carrierCountRatio, SCDLevel.Infected, infectedCountRatio));
  }

  private OffspringCount(OffspringCountDistribution distribution, Map<SCDLevel, Float> ratio) {
    this.distribution = distribution;
    this.ratio = ratio;
    ratio.values().forEach(v -> checkState(v >= 0));
  }

  public int offspringCount(SCDLevel scdLevel) {
    return (int) (ratio.getOrDefault(scdLevel, 1f) * distribution.next());
  }
}
