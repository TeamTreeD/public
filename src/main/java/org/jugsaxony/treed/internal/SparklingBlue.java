package org.jugsaxony.treed.internal;

/**
 * Making sure blue is blue on the real tree.
 */
public class SparklingBlue extends AbstractSparklingColor {

  @Override
  public int getRGB() {
    return RGB_BLUE;
  }

  @Override
  public String getStrategyName() {
    return "Sparkling Blue";
  }

  @Override
  public String getDescription() {
    return "Sparkling Blue\n" +
            "Testing if logical blue is mapped to actual blue on the tree.";
  }
}
