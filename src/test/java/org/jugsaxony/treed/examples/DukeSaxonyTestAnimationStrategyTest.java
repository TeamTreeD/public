package org.jugsaxony.treed.examples;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategyTest;
import org.jugsaxony.treed.api.AnimationStrategy;

public class DukeSaxonyTestAnimationStrategyTest extends AbstractBaseAnimationStrategyTest
{

  @Override
  public AnimationStrategy createStrategy() {
    return new DukeSaxony();
  }
}
