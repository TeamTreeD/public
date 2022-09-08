package org.jugsaxony.treed.examples;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

import java.util.List;

/**
 * Line of lights chasing up the tree.
 */
public class SnakeUp extends AbstractBaseAnimationStrategy {

  private long normalizedTime;
  private int  numTicks    = 20000;
  private int  newPosition = 0;
  private int  oldPosition = -1;

  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {
    super.onStartFrame(bulbs, timestamp);
    normalizedTime = timestamp % numTicks;

    newPosition = (int)normalizedTime * NUM_BULBS / numTicks;
    if (newPosition<oldPosition) {
      oldPosition = -1;
    }

    fadeAll(bulbs, 2);
  }

  @Override
  public void onEndFrame(List<Bulb> bulbs, long timestamp) {
    super.onEndFrame(bulbs, timestamp);

    oldPosition = newPosition;
  }

  @Override
  protected void calcBulb(Bulb bulb, long timestamp) {
    if ((bulb.getIndex() > oldPosition) && (bulb.getIndex() <= newPosition)){
      bulb.setRgb(0x00ff0000);
    } // no else
  }

  @Override
  public String getStrategyName() {
    return "Snake Up";
  }

  @Override
  public String getDescription() {
    return "Line of lights chasing through the tree. "+
           "Brightest light is leading, following lights getting dimmer and dimmer.";
  }

  @Override
  public String getAuthorName() {
    return "Steffen Gemkow";
  }

  @Override
  public String getAuthorEmail() {
    return "treed@jugsaxony.org";
  }
}
