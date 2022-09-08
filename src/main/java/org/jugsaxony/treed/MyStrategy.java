package org.jugsaxony.treed;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

/**
 * This class serves as starting point when developing your animation
 * on the EntwicklerHeld platform.
 */
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

  /**
   * This is a helper method that is only needed on the EntwicklerHeld
   * platform. It prevents you from submitting by clicking on the 
   * "STAGE ABGEBEN" button accidentally. You can only submit, if you return
   * <code>true</code> from this method, so we suggest that you change
   * the return value when you're really done, right before submitting.
   * 
   * @return Whether the strategy is ready to be submitted.
   */
  public boolean readyToSubmit() {
    return false;
  }
}
