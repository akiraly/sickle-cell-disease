package com.github.akiraly.scd;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.math3.random.RandomGenerator;

/**
 * Based on http://www.cdc.gov/NCBDDD/sicklecell/data.html 100_000 SCD and
 * http://www.hematology.org/Patients/Anemia/Sickle-Cell-Trait.aspx  1-3 million SCT and
 * https://www.google.com/publicdata/explore?ds=kf7tgg1uo9ude_&ctype=l&strail=false&bcs=d&nselm=h&met_y=population&scale_y=lin&ind_y=false&rdim=country&idim=country:US&ifdim=country&hl=en&dl=en&ind=false
 * USA population 321 million
 */
class SCDLevelDistribution extends DistributionBasedRandom<SCDLevel> {
  SCDLevelDistribution(RandomGenerator randomGenerator) {
    this(randomGenerator, 0.000312, 0.4); // 0.009346);
  }

  private SCDLevelDistribution(RandomGenerator randomGenerator, double infectedRatio, double carrierRatio) {
    super(randomGenerator,
        newDistribution(ImmutableMap.of(infectedRatio, SCDLevel.Infected,
            carrierRatio, SCDLevel.Carrier,
            1 - infectedRatio - carrierRatio, SCDLevel.Healthy))
    );
  }
}
