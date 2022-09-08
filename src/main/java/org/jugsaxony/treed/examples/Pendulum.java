package org.jugsaxony.treed.examples;

import javafx.geometry.Point2D;
import javafx.scene.transform.Transform;
import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

import java.util.List;

/**
 * A plane is swinging in the tree, coloring bulbs green an red until they fade.
 */
public class Pendulum extends AbstractBaseAnimationStrategy {
  private static final long PERIOD = 5000;

  private int rgb;
  private Transform transformation;

  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {
    long normalizedTime;
    double angle;

    normalizedTime = timestamp % PERIOD;

    if (normalizedTime < PERIOD / 2) {
      rgb = RGB_RED;
    } else {
      rgb = RGB_GREEN;
    }

    angle = 30.0*Math.cos(2.0*Math.PI*(double)normalizedTime/(double)PERIOD);
    transformation = Transform.rotate(angle, 0.0, 1.1*TREE_HEIGHT);

    fadeAll(bulbs, 16);
  }

  @Override
  protected void calcBulb(Bulb bulb, long timestamp) {
    if (isCloseToSwingingPlane(bulb)) {
      bulb.setRgb(rgb);
    }
  }

  private boolean isCloseToSwingingPlane(Bulb bulb) {
    boolean result = false;
    Point2D transformed;

    if (transformation != null) {
      transformed = transformation.transform(bulb.getX(), bulb.getZ());
      result = ((transformed.getX()>-0.05) && (transformed.getX()<0.05));
    }

    return result;
  }

  @Override
  public String getStrategyName() {
    return "Pendulum";
  }

  @Override
  public String getDescription() {
    return "A plane is swinging in the tree, hanging from the top. " +
            "Swinging right it lights bulbs green, swinging left it lights them red. Bulbs fade.";
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