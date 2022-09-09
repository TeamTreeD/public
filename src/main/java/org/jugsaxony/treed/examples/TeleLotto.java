package org.jugsaxony.treed.examples;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

import java.util.List;

public class TeleLotto extends AbstractBaseAnimationStrategy {
  public final static double PERIOD = 5000.0;

  private double normalizedTime;
  private double r, alpha, x, y, z;

  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {

    super.onStartFrame(bulbs, timestamp);
    normalizedTime = ((double) timestamp % PERIOD) / PERIOD;  // ranges from 0.0 to 1.0

    r     = scale(0.0, normalizedTime, 1.0, 0.3*TREE_RADIUS, TREE_RADIUS);
    alpha = scale(0.0, normalizedTime, 1.0, 0.0, 9.0*Math.PI);
    x     = r * Math.sin(alpha);
    y     = r * Math.cos(alpha);
    z     = scale(0.0, normalizedTime, 1.0, TREE_HEIGHT+TREE_RADIUS/2.0, -TREE_RADIUS/2.0);

    fadeAll(bulbs, 6);
  }

  @Override
  protected void calcBulb(Bulb bulb, long timestamp) {
    if (distance(bulb, x, y, z) < TREE_RADIUS/2.0) {
      bulb.setRgb(RGB_CYAN);
    } // no else
  }

  @Override
  public String getStrategyName() {
    return "Tele Lotto";
  }

  @Override
  public String getDescription() {
    return "A ball of light is rolling down the surface of the cone.";
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
