package com.github.akiraly.scd;

/**
 * Sickle Cell Disease
 *
 * https://en.wikipedia.org/wiki/Sickle-cell_disease#Genetics
 */
public enum SCDLevel {
  Healthy,
  Carrier, // SCT
  Infected { // SCD

    @Override
    public int numOfOffsprings(int def) {
      return 0;
    }
  };

  public int numOfOffsprings(int def) {
    return def;
  }
}
