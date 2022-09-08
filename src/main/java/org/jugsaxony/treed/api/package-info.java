/**
 * <p>To implement your own animation (either by implementing AnimationStrategy or by deriving from
 * AbstractBaseAnimationStrategy) you need to know a little bit about the world our tree lives in.</p>
 *
 * <h3>Coordinates</h3>
 * <p>In a world of x,y and z it is important to know which is which.</p>
 * <ul>
 * <li>X starts with negative values at the left side, in the middle is 0 and to the right values keep rising.</li>
 * <li>Y starts with negative values at the front, in the middle is 0 and to the rear values keep rising.</li>
 * <li>Z starts with 0 at the bottom and values keep rising as you move up.</li>
 *</ul>
 *
 * <h3>Tree</h3>
 * <ul>
 * <li>At the bottom the tree has a radius of TREE_RADIUS. Please not, that one or two Bulbs might be a bit further out.</li>
 * <li>Height of the tree is TREE_HEIGHT. Who would have guessed.</li>
 * <li>The bottom center of the tree is located at (0, 0, 0).</li>
 * </ul>
 *
 * <h3>Colors</h3>
 * <ul>
 * <li>RGB is nothing new, and we tried to make developing with it easy for you. So we provided color constants.</li>
 * <li>While you are free to use any RGB color, we encourage you to use only these colors if you use the fade function.</li>
 *</ul>
 *
 * <h3>Hints</h3>
 * <ul>
 * <li>We've developed a few examples as strategies. Please feel free to get inspired.</li>
 * <li>While 400 LEDs sounds like a lot, in reality it's less pixels than most icons on your phone have.
 * So don't expect to be able to show images. DukeSaxony tries that, but fails.</li>
 * <li>Make use of the scale method. See TeleLotto how simple an implementation can be with scale applied at the right places.</li>
 * <li>RGB is king. If you want to drown us in unicorn vomit (rainbow colors), see VerticalRainbow how calcRainbow can make this easy.</li>
 * </ul>
 */

package org.jugsaxony.treed.api;

