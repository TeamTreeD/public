package org.jugsaxony.treed.examples;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

import java.util.List;

/**
 * A rainbow descends the tree.
 */
public class VerticalRainbow extends AbstractBaseAnimationStrategy {
  private double normalizedTime;
  private long numTicks = 2000;

  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {
    super.onStartFrame(bulbs, timestamp);
    normalizedTime = (timestamp % (numTicks)) / (double) numTicks;
  }

  @Override
  protected void calcBulb(Bulb bulb, long timestamp) {
    double z;

    z = bulb.getZ();

    bulb.setRgb(calcRainbow(z+normalizedTime));
  }

  @Override
  public String getStrategyName() {
    return "Vertical Rainbow";
  }

  @Override
  public String getDescription() {
    return "All bulbs are illuminated. The color if each LED "+
           "depends on height and time. Colors are ordered as " +
           "they are in a rainbow.";
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
