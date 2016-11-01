package com.github.akiraly.scd;

import com.sun.org.apache.bcel.internal.generic.POP;

import java.util.Set;

public class Population {
  private final Set<Individual> individuals;

  public Population(Set<Individual> individuals) {
    this.individuals = individuals;
  }

  public static Population newRandomPopulation(double carrierPercentage) {

  }
}
