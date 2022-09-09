package org.jugsaxony.treed;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

public class MyStrategy extends AbstractBaseAnimationStrategy {

  @Override
  protected void calcBulb(Bulb bulb, long timestamp) {
    throw new RuntimeException("not implemented yet");
  }

  @Override
  public String getAuthorEmail() {
    throw new RuntimeException("not implemented yet");
  }

  @Override
  public String getAuthorName() {
    throw new RuntimeException("not implemented yet");
  }

  @Override
  public String getDescription() {
    throw new RuntimeException("not implemented yet");
  }

  @Override
  public String getStrategyName() {
    throw new RuntimeException("not implemented yet");
  }
}
