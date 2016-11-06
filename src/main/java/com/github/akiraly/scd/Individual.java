package com.github.akiraly.scd;

class Individual {
  private final SCDLevel scdLevel;

  Individual(SCDLevel scdLevel) {
    this.scdLevel = scdLevel;
  }

  SCDLevel getScdLevel() {
    return scdLevel;
  }

  @Override
  public String toString() {
    return "Individual{" +
        "scdLevel=" + scdLevel +
        '}';
  }
}
