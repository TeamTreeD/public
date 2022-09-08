package org.jugsaxony.treed.api;

import java.util.List;

/**
 * The interface defining what you need to implement.
 */
public interface AnimationStrategy extends Constants {
  /**
   * Whenever your animation is started, this is the first method to be called by the framework.
   * You can use it to set up internal data structures that will last for the whole animation.
   * Please also tear down this data structures in #onEndAnimation.
   *
   * @param bulbs The list of all bulbs gives you the number of them as well as the position and color of each one.
   * @param timestamp Provides a view at the clock ( <code>System.currentTimeMillis()</code> ) is a consistant way.
   */
  void onStartAnimation(List<Bulb> bulbs, long timestamp);

  /**
   * The framework will present the animation as a sequence of frames and at the beginning of each frame,
   * this method is called. You can use it to do some calculations that will have value for the whole frame.
   * If you create a dynamic model of some sort in this method, please be a good citizen and destroy it in #onEndFrame.
   *
   * @param bulbs The list of all bulbs gives you the number of them as well as the position and color of each one.
   * @param timestamp Provides a view at the clock ( <code>System.currentTimeMillis()</code> ) is a consistant way.
   */
  void onStartFrame(List<Bulb> bulbs, long timestamp);
  void calcFrame(List<Bulb> bulbs, long timestamp);

  /**
   * If you create a dynamic model of some sort in this method, please be a good citizen and destroy it in #onEndFrame.
   *
   * @param bulbs The list of all bulbs gives you the number of them as well as the position and color of each one.
   * @param timestamp Provides a view at the clock ( <code>System.currentTimeMillis()</code> ) is a consistant way.
   */
  void onEndFrame(List<Bulb> bulbs, long timestamp);

  /**
   * When your animation is ending, this is the last method to be called by the framework.
   * You should use it to clean up after you. Thanks.
   *
   * BTW: It's not your decision, to end the animation. Only the framework has this superpower.
   *
   * @param bulbs The list of all bulbs gives you the number of them as well as position and color of each one.
   * @param timestamp Provides a view at the clock ( <code>System.currentTimeMillis()</code> ) is a consistant way.
   */
  void onEndAnimation(List<Bulb> bulbs, long timestamp);

  /**
   * To process many animations by many participants we need to automate to later provide tables
   * of data and rankings. The name of your strategy/animation will be published, so please be polite.
   * Otherwise you'll risk disqualification.
   *
   * @return The name you are giving your animation.
   */
  String getStrategyName();

  /**
   * To process many animations by many participants we need to automate. The description of
   * your strategy/animation will be read out, when the ranking takes place.
   * Please be polite, otherwise you'll risk disqualification.
   *
   * @return The description you provide to viewers of what they are about to see.
   */
  String getDescription();

  /**
   * Who is the brilliant person that implemented this beautiful animation?
   * We need to know. Especially during the ranking process and to contact winners.
   *
   * @return Your Name
   */
  String getAuthorName();

  /**
   * During development you can send in animations. We may look at them, check some criteria
   * and use it to harden our framework. In case we need to contact you, this is the way we will do that.
   * So please use a valid address. We won't give it away or store it somewhere else.
   *
   * @return An email address where we can reach you. We won't give it away or store it somewhere else.
   */
  String getAuthorEmail();
}
