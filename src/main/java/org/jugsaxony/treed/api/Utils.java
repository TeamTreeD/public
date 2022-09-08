package org.jugsaxony.treed.api;

import java.util.List;

/**
 * Collection of usefull helper functions for animation strategies.
 */
public class Utils {

  /**
   * Selecting a RGB color from the rainbow. Both 0.0 and 1.0 deliver
   * the very same color, but go through the whole rainbow for any
   * value in between. Very usefull for color cycling.
   *
   * @param zeroToOne normalizes value between 0.0 and 1.0
   * @return the selected color
   */
  public int calcRainbow(double zeroToOne) {
    int result;
    int r, g, b;
    double rr, gg, bb;

    zeroToOne = zeroToOne % 1.0;

    rr = 1.0+Math.sin(2.0*Math.PI*(zeroToOne+0.00000));
    gg = 1.0+Math.sin(2.0*Math.PI*(zeroToOne+0.33333));
    bb = 1.0+Math.sin(2.0*Math.PI*(zeroToOne+0.66666));
    r = (int) (rr*127.0);
    g = (int) (gg*127.0);
    b = (int) (bb*127.0);

    result = (r<<16) | (g<<8) | b;

    return result;
  }

  /**
   * Reducing the brightness of each component of RGB by a specific amount for all bulbs.
   * This works best with the 8 color values that follow the binary approach to RGB components
   * (R, G and B either full on or full off).
   *
   * @param bulbs List of bulbs
   * @param amount How much to reduce brightness?
   */
  public void fadeAll(List<Bulb> bulbs, int amount) {
    for (Bulb bulb: bulbs) {
      bulb.fade(amount);
    }
  }

  /**
   * While combining a RGB value out of values for each component is not
   * complicated, this method provides a readable way. Readability is important.
   *
   * @param red value for red color compontent
   * @param green value for green color component
   * @param blue value for blue color component
   * @return the combined RGB value
   */
  public int combineRgb(int red, int green, int blue) {
    int result;

    result = (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff);

    return result;
  }

  /**
   * Rule of Three (german: "Dreisatz") as a method.
   * You have two scalares with two end values each.
   * One of the scalars also has a ref value between the end values.
   * You want to know what this translates to on the other scalar.
   *
   * @param refA first end value of input scalar
   * @param ref input value to scale
   * @param refB second end value of input scalar
   * @param scaledA first end value of output scalar
   * @param scaledB second end value of output scalar
   * @return scaled value
   */
  public double scale(double refA, double ref, double refB, double scaledA, double scaledB) {
    double result;
    double refClipped;
    double refRel;
    double refDelta;
    double scaledDelta;

    // ensuring refClipped is between refA and refB
    if (refA < refB) {
      refClipped = Math.max(refA, ref);
      refClipped = Math.min(refB, refClipped);
    } else {
      refClipped = Math.max(refB, ref);
      refClipped = Math.min(refA, refClipped);
    }

    // rule of three with removing and adding offsets
    refRel      = refClipped-refA;
    refDelta    = refB-refA;
    scaledDelta = scaledB-scaledA;
    result      = (refRel * scaledDelta / refDelta) + scaledA;

    return result;
  }

  /**
   * Determines the distance between the position of the
   * bulb and a specific point in 3D space. Assuming you calculated
   * a position (x,y,z) and want to set all bulbs near to a specific
   * color while going though all bulbs, this is what you want.
   *
   * @param bulb The bulb to be used
   * @param x the x coordinate of the 3D position
   * @param y the y coordinate of the 3D position
   * @param z the z coordinate of the 3D position
   * @return the distance
   */
  public double distance(Bulb bulb, double x, double y, double z) {
    double result;
    double dx, dy, dz;

    dx = bulb.getX() - x;
    dy = bulb.getY() - y;
    dz = bulb.getZ() - z;

    result = Math.sqrt((dx*dx) + (dy*dy) + (dz*dz));

    return result;
  }
}
