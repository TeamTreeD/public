package org.jugsaxony.treed.examples;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

import java.util.ArrayList;
import java.util.List;

public class XmasScrew extends AbstractBaseAnimationStrategy {

  public class Drop {
    private double x,y,z;

    public void setXYZ(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
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
  }

  private static final int NUM_DROPS = 10;
  private static final int PERIOD    = 5000;

  private List<Drop> reds, greens;

  @Override
  public void onStartAnimation(List<Bulb> bulbs, long timestamp) {
    reds   = new ArrayList<>(NUM_DROPS);
    greens = new ArrayList<>(NUM_DROPS);

    for (int i=0; i< NUM_DROPS; i++) {
      reds.add(new Drop());
      greens.add(new Drop());
    }
  }

  @Override
  public void onEndAnimation(List<Bulb> bulbs, long timestamp) {
    reds   = null;
    greens = null;
  }

  @Override
  public void onStartFrame(List<Bulb> bulbs, long timestamp) {
    double progressZeroOne;

    progressZeroOne = (double)(timestamp % PERIOD)/(double) PERIOD;

    calculateDropPositions(reds, Math.PI, progressZeroOne);
    calculateDropPositions(greens, 0.0, progressZeroOne);
  }

  @Override
  public void calcBulb(Bulb bulb, long timestamp) {
    if (isHit(bulb, reds)) {
      bulb.setRgb(RGB_RED);
    } else if (isHit(bulb, greens)) {
      bulb.setRgb(RGB_GREEN);
    } else {
      bulb.setRgb(RGB_BLACK);
    }
  }

  public double distance(Bulb bulb, Drop drop) {
    double result;
    double dx, dy, dz;

    dx = bulb.getX() - drop.getX();
    dy = bulb.getY() - drop.getY();
    dz = bulb.getZ() - drop.getZ();

    result = Math.sqrt(dx*dx + dy*dy + dz*dz);

    return result;
  }

  public boolean isHit(Bulb bulb, List<Drop> drops) {
    boolean result = false;
    double closeEnough;

    closeEnough = Math.sqrt(bulb.getX()*bulb.getX() + bulb.getY()*bulb.getY());

    if (drops != null) {
      for (Drop drop : drops) {
        if (distance(bulb, drop) <= closeEnough) {
          result = true;
          break;
        } // no else
      }
    } // no else

    return result;
  }

  public void calculateDropPositions(List<Drop> drops, double radOffset, double progressZeroOne) {
    double alpha;
    double delta;
    double r, x, y, z;
    Drop   drop;

    if (drops != null) {
      alpha = Math.PI * 2.0 * progressZeroOne + radOffset;
      delta = Math.PI / drops.size();

      for (int i = 0; i < drops.size(); i++) {
        r = TREE_RADIUS * 0.1 * ((double)i/(double)drops.size());
        x = r * Math.sin(alpha + i * delta);
        y = r * Math.cos(alpha + i * delta);
        z = TREE_HEIGHT * 1.0 * ((double)i/(double)drops.size());
        drop = drops.get(i);
        drop.setXYZ(x, y, z);
      }
    } // no else
  }

  @Override
  public String getStrategyName() {
    return "Xmas Screw";
  }

  @Override
  public String getDescription() {
    return " A red / green helix rotating around the surface of the tree";
  }

  @Override
  public String getAuthorName() {
    return "Steffen Gemkow";
  }

  @Override
  public String getAuthorEmail() {
    return "treed@jugsaxony.org";
  }
}
