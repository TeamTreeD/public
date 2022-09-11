package org.jugsaxony.treed.api;

import java.util.List;

abstract public class AbstractBaseAnimationStrategy extends Utils implements AnimationStrategy {

  @Override
  public void onStartAnimation(List<Bulb> bulbs, long timestamp) {
    // Overwrite to build structures which live though the whole animation.
  }

  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {
    // Overwrite to build structures which live though the frame.
  }

  @Override
  public void onEndFrame(List<Bulb> bulbs, long timestamp) {
    // Overwrite to tear down structures which lived though the frame.
  }

  @Override
  public void onEndAnimation(List<Bulb> bulbs, long timestamp) {
    // Overwrite to tear down structures which lived though the whole animation.
  }

  /**
   * Calculation all colors of #Bulp's one at a time in the order given.
   * Note:
   * Other implementations may calculate multiple #Bulp's im parallel.
   *
   * @param bulbs ordered list of Bulbs (grouping index, (x,y,z), rgb for each LED)
   * @param timestamp current time in milliseconds
   */
  @Override
  public void calcFrame(List<Bulb> bulbs, long timestamp) {
    for (Bulb bulb:bulbs) {
      calcBulb(bulb, timestamp);
    }
  }

  protected abstract void calcBulb(Bulb bulb, long timestamp);
}
