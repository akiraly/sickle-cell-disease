package com.github.akiraly.scd;

import com.google.common.collect.FluentIterable;

import org.apache.commons.math3.random.AbstractRandomGenerator;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class OffspringCountDistributionTest {
  @Test
  public void testNextNumOfChildren() throws Exception {
    Iterator<Double> randoms = FluentIterable.
        of(0.0, 0.1, 0.2, 0.3, 0.4, 0.2, 0.1, 0.8, 0.999).iterator();
    OffspringCountDistribution d = new OffspringCountDistribution(new AbstractRandomGenerator() {
      @Override
      public void setSeed(long seed) {
        throw new UnsupportedOperationException();
      }

      @Override
      public double nextDouble() {
        return randoms.next();
      }
    });

    assertEquals(0, d.next().longValue());
    assertEquals(0, d.next().longValue());
    assertEquals(1, d.next().longValue());
    assertEquals(2, d.next().longValue());
    assertEquals(2, d.next().longValue());
    assertEquals(1, d.next().longValue());
    assertEquals(0, d.next().longValue());
    assertEquals(4, d.next().longValue());
    assertEquals(8, d.next().longValue());
  }

}