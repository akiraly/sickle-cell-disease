package com.github.akiraly.scd;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.random.RandomGenerator;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

class GenerationIterator {
  private final OffspringFactory offspringFactory;
  private final OffspringCountGenerator offspringCountDistribution;
  private final ListeningExecutorService executor;

  GenerationIterator(RandomGenerator randomGenerator,
                     float carrierOffspringCountMultiplier, float infectedOffspringCountMultiplier) {
    this(new OffspringFactory(randomGenerator),
        new OffspringCountGenerator(randomGenerator,
            carrierOffspringCountMultiplier, infectedOffspringCountMultiplier),
        MoreExecutors.listeningDecorator(
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
                new ThreadFactoryBuilder().setDaemon(true).setNameFormat("gen-iterator-%d").build())));
  }

  private GenerationIterator(OffspringFactory offspringFactory,
                             OffspringCountGenerator offspringCountDistribution,
                             ListeningExecutorService executor) {
    this.offspringFactory = offspringFactory;
    this.offspringCountDistribution = offspringCountDistribution;
    this.executor = executor;
  }

  Generation nextGen(Generation currentGen) {
    List<ListenableFuture<Set<Individual>>> nextGenFutures = new LinkedList<>();
    @Nullable Pair<Individual, Integer> parentWithChildCount = null;
    List<Individual> currentIndividuals = new ArrayList<>(currentGen.getIndividuals());
    Collections.shuffle(currentIndividuals);
    for (Individual p1 : currentIndividuals) {
      int count = offspringCountDistribution.nextOffspringCount(p1.getScdLevel());
      if (count == 0) {
        continue;
      }
      if (parentWithChildCount == null) {
        parentWithChildCount = Pair.of(p1, count);
        continue;
      }
      Individual p2 = parentWithChildCount.getLeft();
      int opCount = Math.min(count, parentWithChildCount.getRight());
      nextGenFutures.add(executor.submit(new OffspringCreator(p1, p2, opCount)));
      if (count > parentWithChildCount.getRight()) {
        parentWithChildCount = Pair.of(p1, count - parentWithChildCount.getRight());
      } else if (parentWithChildCount.getRight() > count) {
        parentWithChildCount = Pair.of(p2, parentWithChildCount.getRight() - count);
      } else {
        parentWithChildCount = null;
      }
    }
    return new Generation(ImmutableSet.copyOf(Iterables.concat(
        Futures.getUnchecked(Futures.allAsList(nextGenFutures)))));
  }

  private class OffspringCreator implements Callable<Set<Individual>> {
    final Individual a;
    final Individual b;
    final int count;

    private OffspringCreator(Individual a, Individual b, int count) {
      this.a = a;
      this.b = b;
      this.count = count;
    }

    @Override
    public Set<Individual> call() throws Exception {
      Set<Individual> offsprings = new HashSet<>();
      for (int fi = 0; fi < count; fi++) {
        offsprings.add(offspringFactory.newOffspring(a, b));
      }
      return Collections.unmodifiableSet(offsprings);
    }
  }
}
