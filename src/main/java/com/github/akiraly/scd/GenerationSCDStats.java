package com.github.akiraly.scd;

import com.google.common.base.Joiner;

import java.util.Map;
import java.util.TreeMap;

import static com.google.common.collect.Maps.transformValues;

public class GenerationSCDStats {
  private Map<SCDLevel, Integer> countBySCDLevel;

  public GenerationSCDStats(Map<SCDLevel, Integer> countBySCDLevel) {
    this.countBySCDLevel = countBySCDLevel;
  }

  @Override
  public String toString() {
    int total = countBySCDLevel.values().stream().reduce(0, Integer::sum);
    Map<SCDLevel, Double> scdLevelToRatio = new TreeMap<>(transformValues(countBySCDLevel, count -> count.doubleValue() / total));
    return total + ";" + Joiner.on(';').join(scdLevelToRatio.values());
  }
}
