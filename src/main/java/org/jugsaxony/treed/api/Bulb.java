package org.jugsaxony.treed.api;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Representing a single LED on the tree. It has a position (x,y,z), a position in the sequence of bulbs and a color.
 */
public class Bulb implements Constants {
  private final double x, y, z;
  private int          rgb = 0x00000000;
  private final int    index;

  /**
   * Constructor used by the framework.
   * There is no particular need for you (the participant) to create a bulb.
   * At least, none that we can think of. But what do we know... so we didn't hide the constructor.
   *
   * @param index position in the sequence of bulbs
   * @param x     position left-right ascending in 3D space
   * @param y     position front-rear ascending in 3D space
   * @param z     position bottom-top ascending in 3D space
   */
  public Bulb(int index, double x, double y, double z) {
    this.index = index;
    this.rgb   = RGB_BLACK;
    this.x     = x;
    this.y     = y;
    this.z     = z;
  }

  /**
   * Returns the RGB color as one value.
   * @return the color as one value (<code>0x00RRGGBB</code>)
   */
  public int getRgb() {
    return rgb;
  }

  /**
   * Set the RGB color as one value.
   * @param rgb the new color as one value (<code>0x00RRGGBB</code>)
   */
  public void setRgb(int rgb) {
    this.rgb = rgb;
  }

  /**
   * Set only the red component of the RGB color.
   * @param value value of red color component (ranging from 0x00 to 0xff)
   */
  public void setR(int value) {
    int rrggbb, r,g,b;

    rrggbb = getRgb();
    r = value & 0xff;
    g = (rrggbb >> 8) & 0xff;
    b = (rrggbb) & 0xff;

    rrggbb = (r<<16) | (g<<8) | b;
    setRgb(rrggbb);
  }

  /**
   * Set only the green component of the RGB color.
   * @param value value of green color component (ranging from 0x00 to 0xff)
   */
  public void setG(int value) {
    int rrggbb, r,g,b;

    rrggbb = getRgb();
    r = (rrggbb >> 16) & 0xff;
    g = value & 0xff;
    b = (rrggbb) & 0xff;

    rrggbb = (r<<16) | (g<<8) | b;
    setRgb(rrggbb);
  }

  /**
   * Set only the blue component of the RGB color.
   * @param value value of blue color component (ranging from 0x00 to 0xff)
   */
  public void setB(int value) {
    int rrggbb, r,g,b;

    rrggbb = getRgb();
    r = (rrggbb >> 16) & 0xff;
    g = (rrggbb >> 8) & 0xff;
    b = value & 0xff;

    rrggbb = (r<<16) | (g<<8) | b;
    setRgb(rrggbb);
  }

  /**
   * The position of the bulb is defined by x,y, and z. This method returns x.
   * @return the x coordinate of this bulbs position
   */
  public double getX() {
    return x;
  }

  /**
   * The position of the bulb is defined by x,y, and z. This method returns y.
   * @return the y coordinate of this bulbs position
   */
  public double getY() {
    return y;
  }

  /**
   * The position of the bulb is defined by x,y, and z. This method returns z.
   * @return the z coordinate of this bulbs position
   */
  public double getZ() {
    return z;
  }

  /**
   * All Bulbs are in a fixed sequence and the index describes the position in that sequence.
   * @return Where in the sequence of Bulbs is this one?
   */
  public int getIndex() {
    return index;
  }

  /**
   * Reduces the brightness of each color component by amount, making it darker.
   * @param amount How much darker should each color component be?
   */
  public void fade(int amount) {
    int red, green, blue, sub;

    sub   = min(255, amount);
    sub   = max(0, sub);

    red   = (rgb & 0x00ff0000) >> 16;
    green = (rgb & 0x0000ff00) >> 8;
    blue  = rgb & 0x000000ff;

    red   = max(red-sub,   0);
    green = max(green-sub, 0);
    blue  = max(blue-sub,  0);

    setRgb((red << 16) | (green << 8) | blue);
  }
}
