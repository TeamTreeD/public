package org.jugsaxony.treed.internal;
/**
 * Making sure green is green on the real tree.
 */
public class SparklingGreen extends AbstractSparklingColor {

  @Override
  public int getRGB() {
    return RGB_GREEN;
  }

  @Override
  public String getStrategyName() {
    return "Sparkling Green";
  }

  @Override
  public String getDescription() {
    return "Sparkling Green\n" +
           "Testing if logical green is mapped to actual green on the tree.";
  }
}
