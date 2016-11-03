package com.github.akiraly.scd;

public class Individual {
  private final SCDLevel scdLevel;

  public Individual() {
    this(SCDLevel.Healthy);
  }

  public Individual(SCDLevel scdLevel) {
    this.scdLevel = scdLevel;
  }

  public SCDLevel getScdLevel() {
    return scdLevel;
  }
}
