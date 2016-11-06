package com.github.akiraly.scd;

import com.google.common.collect.Ordering;

/**
 * Sickle Cell Disease
 *
 * https://en.wikipedia.org/wiki/Sickle-cell_disease#Genetics
 */
enum SCDLevel {
  Healthy,
  Carrier, // SCT
  Infected; // SCD

  static final Ordering<SCDLevel> ORDERING = Ordering.explicit(Healthy, Carrier, Infected);
}
