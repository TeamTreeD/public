package org.jugsaxony.treed.examples;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategyTest;
import org.jugsaxony.treed.api.AnimationStrategy;

public class TeleLottoTestAnimationStrategyTest extends AbstractBaseAnimationStrategyTest
{

  @Override
  public AnimationStrategy createStrategy() {
    return new TeleLotto();
  }
}
