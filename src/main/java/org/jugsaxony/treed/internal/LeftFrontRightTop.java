package org.jugsaxony.treed.internal;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

import java.util.List;

/**
 * To validate the real tree and both simulators are using the same coordinate system.
 */
public class LeftFrontRightTop extends AbstractBaseAnimationStrategy {
  int phase = 0;

  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {
    super.onStartFrame(bulbs, timestamp);
    phase = (int)(timestamp%2000L) / 500;
  }

  @Override
  protected void calcBulb(Bulb bulb, long timestamp) {

    switch (phase) {

      case 0: // left
        if (distance(bulb, -TREE_RADIUS, 0, 0) < TREE_RADIUS) {
          bulb.setRgb(RGB_RED);
        } else {
          bulb.setRgb(RGB_BLACK);
        }
        break;

      case 1: // front
        if (distance(bulb, 0, -TREE_RADIUS, 0) < TREE_RADIUS) {
          bulb.setRgb(RGB_GREEN);
        } else {
          bulb.setRgb(RGB_BLACK);
        }
        break;

      case 2: // right
        if (distance(bulb, TREE_RADIUS, 0, 0) < TREE_RADIUS) {
          bulb.setRgb(RGB_BLUE);
        } else {
          bulb.setRgb(RGB_BLACK);
        }
        break;

      case 3: // top
        if (distance(bulb, 0, 0, TREE_HEIGHT) < TREE_RADIUS) {
          bulb.setRgb(RGB_WHITE);
        } else {
          bulb.setRgb(RGB_BLACK);
        }
        break;
    }
  }

  @Override
  public String getStrategyName() {
    return "LeftFrontRightTop";
  }

  @Override
  public String getDescription() {
    return "Placing colored Bubbles at well defined positions.\n"+
           "  left  (-TREE_RADIUS, 0, 0) -> red   \n"+
           "  front (0, -TREE_RADIUS, 0) -> green \n"+
           "  right ( TREE_RADIUS, 0, 0) -> blue  \n"+
           "  top   (0, 0,  TREE_HEIGHT) -> white \n";
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
