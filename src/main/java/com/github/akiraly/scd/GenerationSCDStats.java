package com.github.akiraly.scd;

import java.util.Map;

public class GenerationSCDStats {
  private Map<SCDLevel, Integer> bySCDLevel;

  public GenerationSCDStats(Map<SCDLevel, Integer> bySCDLevel) {
    this.bySCDLevel = bySCDLevel;
  }

  @Override
  public String toString() {
    return "GenerationSCDStats{" +
        "bySCDLevel=" + bySCDLevel +
        '}';
  }
}
