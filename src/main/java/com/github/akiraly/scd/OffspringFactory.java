package com.github.akiraly.scd;

import org.apache.commons.math3.random.RandomGenerator;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

class OffspringFactory {
  private final RandomGenerator randomGenerator;

  OffspringFactory(RandomGenerator randomGenerator) {
    this.randomGenerator = requireNonNull(randomGenerator);
  }

  Individual newOffspring(Individual a, Individual b) {
    return new Individual(newOffspringSCDLevel(a.getScdLevel(), b.getScdLevel()));
  }

  private SCDLevel newOffspringSCDLevel(SCDLevel a, SCDLevel b) {
    if (SCDLevel.ORDERING.compare(a, b) > 0) {
      return newOffspringSCDLevel(b, a);
    }
    switch (a) {
      case Healthy:
        switch (b) {
          case Healthy:
            return SCDLevel.Healthy;
          case Carrier:
            return randomGenerator.nextBoolean() ? SCDLevel.Healthy : SCDLevel.Carrier;
          case Infected:
            return SCDLevel.Carrier;
          default:
            throw new UnsupportedOperationException(a + " " + b);
        }
      case Carrier:
        switch (b) {
          case Carrier:
            double rnd = randomGenerator.nextDouble();
            if (rnd < 0.25) {
              return SCDLevel.Healthy;
            }
            if (rnd < 0.75) {
              return SCDLevel.Carrier;
            }
            return SCDLevel.Infected;
          case Infected:
            return randomGenerator.nextBoolean() ? SCDLevel.Carrier : SCDLevel.Infected;
          default:
            throw new UnsupportedOperationException(a + " " + b);
        }
      case Infected:
        checkState(b == SCDLevel.Infected);
        return SCDLevel.Infected;
      default:
        throw new UnsupportedOperationException(a.toString());
    }
  }
}
