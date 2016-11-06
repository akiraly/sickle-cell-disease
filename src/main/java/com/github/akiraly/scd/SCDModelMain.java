package com.github.akiraly.scd;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;

import java.util.Random;

public class SCDModelMain {
  private static final RandomGenerator RANDOM_GENERATOR = RandomGeneratorFactory.createRandomGenerator(new Random());

  public static void main(String[] args) {
    new SCDModel(RANDOM_GENERATOR, 10_000, 20, 0.0f).run();
  }
}
