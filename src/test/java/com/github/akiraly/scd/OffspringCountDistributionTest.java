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
    OffspringCountGenerator d = new OffspringCountGenerator(new AbstractRandomGenerator() {
      @Override
      public void setSeed(long seed) {
        throw new UnsupportedOperationException();
      }

      @Override
      public double nextDouble() {
        return randoms.next();
      }
    }, 1f, 1f);

    assertEquals(0, d.nextOffspringCount(SCDLevel.Healthy).longValue());
    assertEquals(0, d.nextOffspringCount(SCDLevel.Healthy).longValue());
    assertEquals(1, d.nextOffspringCount(SCDLevel.Healthy).longValue());
    assertEquals(2, d.nextOffspringCount(SCDLevel.Healthy).longValue());
    assertEquals(2, d.nextOffspringCount(SCDLevel.Healthy).longValue());
    assertEquals(1, d.nextOffspringCount(SCDLevel.Healthy).longValue());
    assertEquals(0, d.nextOffspringCount(SCDLevel.Healthy).longValue());
    assertEquals(4, d.nextOffspringCount(SCDLevel.Healthy).longValue());
    assertEquals(8, d.nextOffspringCount(SCDLevel.Healthy).longValue());
  }

}