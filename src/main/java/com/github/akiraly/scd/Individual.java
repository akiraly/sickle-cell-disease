package com.github.akiraly.scd;

public class Individual {
  private final SCDLevel scdLevel;

  public Individual() {
    this(SCDLevel.NotInfected);
  }

  public Individual(SCDLevel scdLevel) {
    this.scdLevel = scdLevel;
  }
}
