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
   *
   * Note:
   * Other implementations may calculate multiple #Bulp's im parallel.
   *
   * @param bulbs
   * @param timestamp
   */
  @Override
  public void calcFrame(List<Bulb> bulbs, long timestamp) {
    for (Bulb bulb:bulbs) {
      calcBulb(bulb, timestamp);
    }
  }

  protected abstract void calcBulb(Bulb bulb, long timestamp);

  @Override
  public boolean canRunInParallel() {
    // Overwrite if multiple frames and/or bulbs can be calculated in parallel by multiple instances of your strategy.
    // This means:
    //  * No need to read the old color value.
    //  * No need to process the bulbs in a particular order.
    // We are not sure if we will be able to implement any form of parallelism. Just in case,
    // please let us know if your algorithm and its data structures are able to work multithreaded.
    return false;
  }
}
