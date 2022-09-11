package org.jugsaxony.treed.api;

import java.util.List;

public class Utils {

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
   * The use of this method is not safe for multithreading because it reads the old color value.
   *
   * @param bulbs
   * @param amount
   */
  public void fadeAll(List<Bulb> bulbs, int amount) {
    for (Bulb bulb: bulbs) {
      bulb.fade(amount);
    }
  }

  public int combineRgb(int red, int green, int blue) {
    int result;

    result = (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff);

    return result;
  }

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
