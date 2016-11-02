package com.github.akiraly.scd;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SCDModel implements Runnable {
  private static final Logger LOGGER = LoggerFactory.getLogger(SCDModel.class);
  private static final RandomGenerator RANDOM_GENERATOR = RandomGeneratorFactory.createRandomGenerator(new SecureRandom());

  private final OffspringCountDistribution offspringCountDistribution;
  private final SCDLevelDistribution scdLevelDistribution;
  private final int generationSize;
  private final int numberOfIterations;

  public SCDModel() {
    this(new OffspringCountDistribution(RANDOM_GENERATOR), new SCDLevelDistribution(RANDOM_GENERATOR), 100_000, 100);
  }

  public SCDModel(OffspringCountDistribution offspringCountDistribution, SCDLevelDistribution scdLevelDistribution, int generationSize, int numberOfIterations) {
    this.offspringCountDistribution = offspringCountDistribution;
    this.scdLevelDistribution = scdLevelDistribution;
    this.generationSize = generationSize;
    this.numberOfIterations = numberOfIterations;
  }

  @Override
  public void run() {
    Generation generation = newInitialGeneration();
    LOGGER.info("{}", generation.calcStats());
    for (int fi = 1; fi <= numberOfIterations; fi++) {

    }
  }

  private Generation newInitialGeneration() {
    return new Generation(IntStream.range(0, generationSize)
        .mapToObj(i -> new Individual(scdLevelDistribution.next()))
        .collect(Collectors.toSet()));
  }
}
