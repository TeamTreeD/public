package org.jugsaxony.treed.examples;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

import java.util.List;
import java.util.Random;

/**
 * A spot of Light wandering around.
 */
public class SearchLight extends AbstractBaseAnimationStrategy {
  static long PERIOD = 2000;

  double      angle;
  double      height;
  double      x, y, z, r, closeEnough;
  double      angleOld  = 0.0;
  double      heightOld = TREE_HEIGHT / 2.0;
  double      angleNew, heightNew;
  boolean     targeting = true;
  Random      random    = new Random();

  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {
    long normalizedTime;

    super.onStartFrame(bulbs, timestamp);

    normalizedTime = timestamp % PERIOD;

    if (normalizedTime < PERIOD/2) {
      if (targeting) {
        angleOld  = angleNew;
        heightOld = heightNew;
        angleNew  = random.nextDouble() * 2.0 * Math.PI;
        heightNew = random.nextDouble() * TREE_HEIGHT;
        targeting = false;
      } // no else

      angle  = scale(0.0, (double) normalizedTime, (double) PERIOD/2.0, angleOld, angleNew);
      height = scale(0.0, (double) normalizedTime, (double) PERIOD/2.0, heightOld, heightNew);
    } else {
      targeting = true;
    }

    r = scale(0.0, height, TREE_HEIGHT, TREE_RADIUS, 0.0);
    x = r * Math.sin(angle);
    y = r * Math.cos(angle);
    z = height; // could be unified, but is better readable this way.

    closeEnough = r/2.0 + TREE_RADIUS/3.0;
  }

  @Override
  protected void calcBulb(Bulb bulb, long timestamp) {
    if (distance(bulb, x, y, z) < closeEnough) {
      bulb.setRgb(RGB_WHITE);
    } else {
      bulb.setRgb(RGB_BLACK);
    }
  }

  @Override
  public String getStrategyName() {
    return "Search Light";
  }

  @Override
  public String getDescription() {
    return "A spot of Light wandering around";
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
