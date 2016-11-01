package com.github.akiraly.scd;

import com.google.common.collect.FluentIterable;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class ChildCountDistributionTest {
  @Test
  public void testNextNumOfChildren() throws Exception {
    Iterator<Double> randoms = FluentIterable.
        of(0.0, 10.0, 20.0, 30.0, 40.0, 20.0, 10.0, 80.0, 100.0).iterator();
    ChildCountDistribution d = new ChildCountDistribution(randoms::next);

    assertEquals(0, d.nextNumOfChildren());
    assertEquals(0, d.nextNumOfChildren());
    assertEquals(1, d.nextNumOfChildren());
    assertEquals(2, d.nextNumOfChildren());
    assertEquals(2, d.nextNumOfChildren());
    assertEquals(1, d.nextNumOfChildren());
    assertEquals(0, d.nextNumOfChildren());
    assertEquals(4, d.nextNumOfChildren());
    assertEquals(8, d.nextNumOfChildren());
  }

}