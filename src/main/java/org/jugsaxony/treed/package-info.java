/**
 * <p>TreeD is a Christmas project of JUG Saxony based on an idea by Matt Parker.
 * (Please visit his Youtube channel at <a href="https://www.youtube.com/user/standupmaths">https://www.youtube.com/user/standupmaths</a> .)</p>
 *
 * <p>A real Christmas tree is equipped with a long strain of digital LED bulbs, which means
 * a computer can set the RGB value of every single LED independently. Fun fact: by some
 * basic image processing we also calculated the 3D position of each LED.</p>
 *
 * <p>It is your job (if you choose to accept it) to program an animation for this
 * Christmas tree. Since you probably don't have such a Christmas tree, we provide
 * a software simulation to be used during development. If you follow all the rules,
 * your implementation will work with both the simulation and the real tree (controlled
 * by a Raspberry Pi).</p>
 *
 * <p>Yes, on the 24th of November 2022 we will present all working implementations of
 * present participants during our Christmas session. We will measure a few metrics
 * like FPS or memory load.</p>
 * <p>All presented animations are ranked and the best few even win nice prices.</p>
 *
 * <p>To participate please register by sending an email to treed@jugsaxony.org.
 * Once you've implemented your animation send it to treed@jugsaxony.org.
 * (For details please see <a href="http://www.jugsaxony.org/">www.jugsaxony.org</a> .)</p>
 *
 * <p>The real tree will be controlled by a Raspberry PI 4. So please be gentle with
 * the workload. Don't use anything else than Java 8 and your own code.
 * Don't use threads or IO or the network. You know, keep things simple.</p>
 *
 * <h3>What it's all about?</h3>
 * <ul>
 *   <li>You implement an animation as a <a href="https://en.wikipedia.org/wiki/Strategy_pattern">Strategy</a>.</li>
 *   <li>You define the color of each bulb as a function of index, 3D position and time.</li>
 *   <li>If you need to calculate some internal data before or clean up after each frame or the animation,
 *   there are hook methods for that.</li>
 *   <li>Don't forget to implement the methods to provide some data about yourself and your animation.</li>
 *   <li>And most important: <b>Have fun.</b></li>
 *   <li>By sending in your strategy as source code you can win nice one of the prices (see 
 *   <a href="https://jugsaxony.org/timeline/2022/10/1/Start-Weihnachtswettbewerb">this blogpost</a> for details).</li>
 * </ul>
 *
 * <h3>How to get started:</h3>
 * <ol>
 *   <li>Create your own package. (Unless you go the EntwicklerHeld route.)</li>
 *   <li>Create a class in your package that either implements AnimationStrategy
 *   or derives from AbstractBaseAnimationStrategy. (Within EntwicklerHeld this is taken care of.)</li>
 *   <li>Overwrite abstract methods / implement the interface methods.</li>
 *   <li>See your animation either in the web frontend of EntwicklerHeld or the JavaFX simulator provided.</li>
 *   <li>Create a JUnit5 testcase and have it inherit from AbstractAnimationStrategyTest or
 *   AbstractBaseAnimationStrategyTest an overwrite createStrategy to create an instance of your Strategy. (By using EntwicklerHeld you can skip this.)</li>
 * </ol>
 */

package org.jugsaxony.treed;
