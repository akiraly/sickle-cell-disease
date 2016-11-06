package com.github.akiraly.scd;

import org.apache.commons.math3.random.RandomGenerator;

class SCDModel implements Runnable {
  private final GenerationFactory generationFactory;
  private final GenerationIterator generationIterator;
  private final int numberOfIterations;

  SCDModel(RandomGenerator randomGenerator, int initialPopulationSize, int numberOfIterations, float infectedOffspringCountMultiplier) {
    this(new GenerationFactory(randomGenerator, initialPopulationSize), numberOfIterations,
        new GenerationIterator(randomGenerator, 1f, infectedOffspringCountMultiplier));
  }

  private SCDModel(GenerationFactory generationFactory, int numberOfIterations, GenerationIterator generationIterator) {
    this.generationFactory = generationFactory;
    this.numberOfIterations = numberOfIterations;
    this.generationIterator = generationIterator;
  }

  @Override
  public void run() {
    Generation generation = generationFactory.newGeneration();
    System.out.println("0;" + generation.calcStats());
    for (int fi = 1; fi <= numberOfIterations; fi++) {
      generation = generationIterator.nextGen(generation);
      System.out.println(fi + ";" + generation.calcStats());
    }
  }
}
