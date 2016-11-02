package com.github.akiraly.scd;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Generation {
  private final Set<Individual> individuals;

  public Generation(Set<Individual> individuals) {
    this.individuals = individuals;
  }

  public GenerationSCDStats calcStats() {
    Map<SCDLevel, Integer> bySCDLevel = new HashMap<>();
    individuals.forEach(i -> bySCDLevel.compute(i.getScdLevel(),
        (l, count) -> count != null ? ++count : 1));
    return new GenerationSCDStats(Collections.unmodifiableMap(bySCDLevel));
  }
}
