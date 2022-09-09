package org.jugsaxony.treed.examples;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

import java.util.List;

public class DukeSaxony extends AbstractBaseAnimationStrategy {
  public static final long PERIOD = 10000;
  long normalizedTime;

  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {
    super.onStartFrame(bulbs, timestamp);
    normalizedTime = timestamp % PERIOD;
  }

  @Override
  protected void calcBulb(Bulb bulb, long timestamp) {
    double sash;

    bulb.setRgb(RGB_BLACK);

    if (normalizedTime > PERIOD / 5) {
      bulb.setRgb(RGB_GREEN);
    } // no else

    if (normalizedTime > 2*PERIOD/5) {
      sash = bulb.getZ()-bulb.getX();
      if ((sash>0.5*TREE_RADIUS) && (sash<0.9*TREE_RADIUS)){
        bulb.setRgb(RGB_YELLOW);
      } // no else
    } // no else

    if (normalizedTime > 3*PERIOD/5) {
      if (distance(bulb, 0, -0.5*TREE_RADIUS, 0.4*TREE_HEIGHT) < 0.4*TREE_RADIUS) {
        bulb.setRgb(RGB_RED);
      } // no else
    } // no else

    if (((normalizedTime > 16*PERIOD/20) && (normalizedTime<17*PERIOD/20)) ||
         ((normalizedTime > 18*PERIOD/20) && (normalizedTime<19*PERIOD/20))){
      if ((bulb.getZ()>0.6*TREE_HEIGHT) && (bulb.getZ()<0.7*TREE_HEIGHT) && (bulb.getY()<0.0)){
        bulb.setRgb(RGB_WHITE);
      } // no else
    } // no else
  }

  @Override
  public String getStrategyName() {
    return "Duke Saxony";
  }

  @Override
  public String getDescription() {
    return "The Java mascot in it's JUG Saxony form";
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
