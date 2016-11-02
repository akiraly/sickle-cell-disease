package com.github.akiraly.scd;

import org.apache.commons.math3.random.RandomGenerator;

/**
 * Based on http://blogs.discovermagazine.com/gnxp/2012/08/what-is-the-distribution-of-offspring-per-individual/
 */
public class OffspringCountDistribution extends DistributionBasedRandom<Integer> {

  public OffspringCountDistribution(RandomGenerator randomGenerator) {
    this(randomGenerator, 0.12, 0.12, 0.29, 0.22, 0.12, 0.06, 0.03, 0.02, 0.02);
  }

  public OffspringCountDistribution(RandomGenerator randomGenerator, double... ratios) {
    super(randomGenerator, newDistribution(ratios));
  }
}
