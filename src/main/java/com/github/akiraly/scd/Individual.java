package com.github.akiraly.scd;

public class Individual {
  private final SCDLevel scdLevel;

  public Individual() {
    this(SCDLevel.Healthy);
  }

  public Individual(SCDLevel scdLevel) {
    this.scdLevel = scdLevel;
  }

  public int numOfOffsprings(int def) {
    return scdLevel.numOfOffsprings(def);
  }

  public SCDLevel getScdLevel() {
    return scdLevel;
  }
}
