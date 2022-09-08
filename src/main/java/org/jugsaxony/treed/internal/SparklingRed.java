package org.jugsaxony.treed.internal;

/**
 * Making sure red is red on the real tree.
 */
public class SparklingRed extends AbstractSparklingColor {

  @Override
  public int getRGB() {
    return RGB_RED;
  }

  @Override
  public String getStrategyName() {
    return "Sparkling Red";
  }

  @Override
  public String getDescription() {
    return "Sparkling Red\n" +
           "Testing if logical red is mapped to actual red on the tree.";
  }
}
