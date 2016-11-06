package com.github.akiraly.scd;

import com.google.common.collect.ImmutableSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Generation {
  private final Set<Individual> individuals;

  Generation(ImmutableSet<Individual> individuals) {
    this.individuals = individuals;
  }

  GenerationSCDStats calcStats() {
    Map<SCDLevel, Integer> bySCDLevel = new HashMap<>();
    individuals.forEach(i -> bySCDLevel.compute(i.getScdLevel(),
        (l, count) -> count != null ? ++count : 1));
    return new GenerationSCDStats(Collections.unmodifiableMap(bySCDLevel));
  }

  Set<Individual> getIndividuals() {
    return individuals;
  }
}
