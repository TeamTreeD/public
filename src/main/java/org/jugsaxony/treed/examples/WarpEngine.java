package org.jugsaxony.treed.examples;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

import java.util.List;

public class WarpEngine extends AbstractBaseAnimationStrategy {
  private static final long PERIOD = 1000;
  private static final int  ON     = RGB_WHITE;
  private static final int  OFF    = RGB_BLACK;

  private double lowerBound, upperBound;

  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {
    super.onStartFrame(bulbs, timestamp);

    lowerBound = (TREE_HEIGHT * ( timestamp      % PERIOD)) / PERIOD;
    upperBound = (TREE_HEIGHT * ((timestamp+100) % PERIOD)) / PERIOD;

  }

  @Override
  public String getStrategyName() {
    return "Warp Engine";
  }

  @Override
  public String getDescription() {
    return "A plane of white light moving from bottom to top though the tree. "+
           "It's Xmas at the engine bay of Enterprise.";
  }

  @Override
  public String getAuthorName() {
    return "Steffen Gemkow";
  }

  @Override
  public String getAuthorEmail() {
    return "treed@jugsaxony.org";
  }

  @Override
  protected void calcBulb(Bulb bulb, long timestamp) {
    double z;

    z = bulb.getZ();
    if (lowerBound<upperBound) {
      if ((z > lowerBound) && (z < upperBound)) {
        bulb.setRgb(ON);
      } else {
        bulb.setRgb(OFF);
      }
    } else {
      if ((z > lowerBound) || (z < upperBound)) {
        bulb.setRgb(ON);
      } else {
        bulb.setRgb(OFF);
      }
    }
  }
}
