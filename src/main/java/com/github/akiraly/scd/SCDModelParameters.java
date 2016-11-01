package com.github.akiraly.scd;

public class SCDModelParameters {
  private final double carrierPercentage;
  private final int populationSize;
  private final int numberOfIterations;

  public SCDModelParameters(double carrierPercentage, int populationSize, int numberOfIterations) {
    this.carrierPercentage = carrierPercentage;
    this.populationSize = populationSize;
    this.numberOfIterations = numberOfIterations;
  }
}
