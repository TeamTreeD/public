package org.jugsaxony.treed.internal;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

import java.util.List;

/**
 * This will stress the power supply of the real tree as much as possible.
 */
public class ElectricStress extends AbstractBaseAnimationStrategy {
  private int rgb = RGB_BLACK;

  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {
    super.onStartFrame(bulbs, timestamp);

    // switching between all on and all off for each frame
    if (rgb == RGB_BLACK) {
      rgb = RGB_WHITE;
    } else {
      rgb = RGB_BLACK;
    }
  }

  @Override
  protected void calcBulb(Bulb bulb, long timestamp) {
    bulb.setRgb(rgb);
  }

  @Override
  public String getStrategyName() {
    return "ElectricStress";
  }

  @Override
  public String getDescription() {
    return "Toggle all LEDS between white and black. \n" +
            "All in sync. As fast as possible. \n" +
            "This will stress the power supply as much as possible. \n" +
            "If this test is passed (not crashing), nothing any user strategy can do will harm the hardware.";
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
