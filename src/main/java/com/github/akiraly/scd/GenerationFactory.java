package com.github.akiraly.scd;

import com.google.common.collect.ImmutableSet;

import org.apache.commons.math3.random.RandomGenerator;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class GenerationFactory {
  private final SCDLevelDistribution scdLevelDistribution;
  private final int generationSize;

  GenerationFactory(RandomGenerator randomGenerator, int generationSize) {
    this(new SCDLevelDistribution(randomGenerator), generationSize);
  }

  private GenerationFactory(SCDLevelDistribution scdLevelDistribution, int generationSize) {
    this.scdLevelDistribution = scdLevelDistribution;
    this.generationSize = generationSize;
  }

  Generation newGeneration() {
    return new Generation(ImmutableSet.copyOf(IntStream.range(0, generationSize)
        .mapToObj(i -> new Individual(scdLevelDistribution.next()))
        .collect(Collectors.toSet())));
  }
}
