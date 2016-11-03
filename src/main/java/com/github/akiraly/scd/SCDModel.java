package com.github.akiraly.scd;

import com.google.common.collect.ImmutableSet;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SCDModel implements Runnable {
  private static final RandomGenerator RANDOM_GENERATOR = RandomGeneratorFactory.createRandomGenerator(new SecureRandom());

  private final OffspringCount offspringCount;
  private final SCDLevelDistribution scdLevelDistribution;
  private final int generationSize;
  private final int numberOfIterations;
  private final RandomGenerator randomGenerator;

  public SCDModel() {
    this(new OffspringCount(new OffspringCountDistribution(RANDOM_GENERATOR), 1f, 1f), new SCDLevelDistribution(RANDOM_GENERATOR), 10_000, 100, RANDOM_GENERATOR);
  }

  public SCDModel(OffspringCount offspringCount, SCDLevelDistribution scdLevelDistribution, int generationSize, int numberOfIterations, RandomGenerator randomGenerator) {
    this.offspringCount = offspringCount;
    this.scdLevelDistribution = scdLevelDistribution;
    this.generationSize = generationSize;
    this.numberOfIterations = numberOfIterations;
    this.randomGenerator = randomGenerator;
  }

  @Override
  public void run() {
    Generation generation = newInitialGeneration();
    System.out.println("0;" + generation.calcStats());
    for (int fi = 1; fi <= numberOfIterations; fi++) {
      generation = nextGen(generation);
      System.out.println(fi + ";" + generation.calcStats());
    }
  }

  private Generation newInitialGeneration() {
    return new Generation(IntStream.range(0, generationSize)
        .mapToObj(i -> new Individual(scdLevelDistribution.next()))
        .collect(Collectors.toSet()));
  }

  private Generation nextGen(Generation currentGen) {
    ImmutableSet.Builder<Individual> nextGen = ImmutableSet.builder();
    @Nullable Pair<Individual, Integer> parentWithChildCount = null;
    for (Individual i : currentGen.getIndividuals()) {
      int count = offspringCount.offspringCount(i.getScdLevel());
      if (count == 0) {
        continue;
      }
      if (parentWithChildCount == null) {
        parentWithChildCount = Pair.of(i, count);
        continue;
      }
      int minCount = Math.min(count, parentWithChildCount.getRight());
      int maxCount = Math.max(count, parentWithChildCount.getRight());
      if (maxCount > minCount) {
        count = randomGenerator.nextInt(maxCount - minCount) + minCount;
      }
      for (int fi = 0; fi < count; fi++) {
        nextGen.add(newOffspring(parentWithChildCount.getLeft(), i));
      }
      parentWithChildCount = null;
    }

    return new Generation(nextGen.build());
  }

  private Individual newOffspring(Individual a, Individual b) {
    return new Individual(newOffspringSCDLevel(a.getScdLevel(), b.getScdLevel()));
  }

  private SCDLevel newOffspringSCDLevel(SCDLevel a, SCDLevel b) {
    if (a.compareTo(b) > 0) {
      return newOffspringSCDLevel(b, a);
    }
    switch (a) {
      case Healthy:
        switch (b) {
          case Healthy:
            return SCDLevel.Healthy;
          case Carrier:
            return randomGenerator.nextBoolean() ? SCDLevel.Healthy : SCDLevel.Carrier;
          case Infected:
            return SCDLevel.Carrier;
          default:
            throw new UnsupportedOperationException(a + " " + b);
        }
      case Carrier:
        switch (b) {
          case Carrier:
            double rnd = randomGenerator.nextDouble();
            if (rnd < 0.25) {
              return SCDLevel.Healthy;
            }
            if (rnd < 0.75) {
              return SCDLevel.Carrier;
            }
            return SCDLevel.Infected;
          case Infected:
            return randomGenerator.nextBoolean() ? SCDLevel.Carrier : SCDLevel.Infected;
          default:
            throw new UnsupportedOperationException(a + " " + b);
        }
      case Infected:
        switch (b) {
          case Infected:
            return SCDLevel.Infected;
          default:
            throw new UnsupportedOperationException(a + " " + b);
        }
      default:
        throw new UnsupportedOperationException(a.toString());
    }
  }
}
