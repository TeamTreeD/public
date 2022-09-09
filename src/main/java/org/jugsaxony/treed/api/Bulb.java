package org.jugsaxony.treed.api;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Bulb {
  private final double x, y, z;
  private int          rgb = 0x00000000;
  private final int    index;

  public Bulb(int index, double x, double y, double z) {
    this.index = index;
    this.rgb   = 0x000000;   // black
    this.x     = x;
    this.y     = y;
    this.z     = z;
  }

  public int getRgb() {
    return rgb;
  }

  public void setRgb(int rgb) {
    this.rgb = rgb;
  }

  public void setR(int value) {
    int rrggbb, r,g,b;

    rrggbb = getRgb();
    r = value & 0xff;
    g = (rrggbb >> 8) & 0xff;
    b = (rrggbb) & 0xff;

    rrggbb = (r<<16) | (g<<8) | b;
    setRgb(rrggbb);
  }

  public void setG(int value) {
    int rrggbb, r,g,b;

    rrggbb = getRgb();
    r = (rrggbb >> 16) & 0xff;
    g = value & 0xff;
    b = (rrggbb) & 0xff;

    rrggbb = (r<<16) | (g<<8) | b;
    setRgb(rrggbb);
  }

  public void setB(int value) {
    int rrggbb, r,g,b;

    rrggbb = getRgb();
    r = (rrggbb >> 16) & 0xff;
    g = (rrggbb >> 8) & 0xff;
    b = value & 0xff;

    rrggbb = (r<<16) | (g<<8) | b;
    setRgb(rrggbb);
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public int getIndex() {
    return index;
  }

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
