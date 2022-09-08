package org.jugsaxony.treed.internal;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

import java.util.List;
import java.util.Random;

public abstract class AbstractSparklingColor extends AbstractBaseAnimationStrategy {
  Random random = new Random();

  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {
    super.onStartFrame(bulbs, timestamp);
    fadeAll(bulbs, 4);
  }

  @Override
  protected void calcBulb(Bulb bulb, long timestamp) {
    if (random.nextDouble()<0.01) {
      bulb.setRgb(getRGB());
    }
  }

  public abstract int getRGB();

  @Override
  public String getAuthorName() {
    return "Steffen Gemkow";
  }

  @Override
  public String getAuthorEmail() {
    return "treed@jugsaxony.org";
  }

}
