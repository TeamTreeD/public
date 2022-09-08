package org.jugsaxony.treed.api;

import java.util.List;

/**
 * Convinence class implementing some of the interface methods to let you focus on only
 * the important ones for your strategy. Also provides some helper methods inherited
 * from Utils and breaks down processing all Bulbs of the list to processing each
 * one at a time.
 */
abstract public class AbstractBaseAnimationStrategy extends Utils implements AnimationStrategy {

  /** {@inheritDoc} */
  @Override
  public void onStartAnimation(List<Bulb> bulbs, long timestamp) {
    // Overwrite to build structures which live though the whole animation.
  }

  /** {@inheritDoc} */
  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {
    // Overwrite to build structures which live though the frame.
  }

  /** {@inheritDoc} */
  @Override
  public void onEndFrame(List<Bulb> bulbs, long timestamp) {
    // Overwrite to tear down structures which lived though the frame.
  }

  /** {@inheritDoc} */
  @Override
  public void onEndAnimation(List<Bulb> bulbs, long timestamp) {
    // Overwrite to tear down structures which lived though the whole animation.
  }

  /** {@inheritDoc} */
  @Override
  public void calcFrame(List<Bulb> bulbs, long timestamp) {
    for (Bulb bulb:bulbs) {
      calcBulb(bulb, timestamp);
    }
  }

  /**
   * This method is the essence of most animations. It determines the rgb color of the bulb depending on:
   * <ul>
   *   <li>time</li>
   *   <li>3D position (x,y,z)</li>
   *   <li>index (position in the sequence of bulbs)</li>
   *   <li>old rgb color</li>
   * </ul>
   *
   * What a dry, mathematical way of looking at all your amazing animations. Sorry about that.
   *
   * @param bulb One bulb waiting to have it's rgb color set.
   * @param timestamp Provides a view at the clock ( <code>System.currentTimeMillis()</code> ) is a consistant way.
   */
  protected abstract void calcBulb(Bulb bulb, long timestamp);
}
