package com.github.akiraly.scd;

import com.google.common.base.Joiner;

import java.util.Map;
import java.util.TreeMap;

import static com.google.common.collect.Maps.transformValues;
import static java.util.Objects.requireNonNull;

class GenerationSCDStats {
  private Map<SCDLevel, Integer> countBySCDLevel;

  GenerationSCDStats(Map<SCDLevel, Integer> countBySCDLevel) {
    this.countBySCDLevel = new TreeMap<>(SCDLevel.ORDERING);
    this.countBySCDLevel.putAll(countBySCDLevel);
  }

  @Override
  public String toString() {
    int total = countBySCDLevel.values().stream().reduce(0, Integer::sum);
    Map<SCDLevel, Double> scdLevelToRatio = new TreeMap<>(SCDLevel.ORDERING);
    scdLevelToRatio.putAll(transformValues(countBySCDLevel, count -> requireNonNull(count).doubleValue() / total));
    return String.valueOf(total) + ';' + Joiner.on(';').join(countBySCDLevel.values()) + ';' + Joiner.on(';').join(scdLevelToRatio.values());
  }
}
