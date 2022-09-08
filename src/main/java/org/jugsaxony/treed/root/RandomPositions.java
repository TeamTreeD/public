package org.jugsaxony.treed.root;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;
import static java.lang.Math.cos;

public class RandomPositions implements Positions {
  private int numbulbs;
  private double treeRadius, treeHeight;
  private double spread;
  private Random random;

  public RandomPositions(int numbulbs, double treeRadius, double treeHeight, double spread) {
    this.numbulbs    = numbulbs;
    this.treeRadius = treeRadius;
    this.treeHeight = treeHeight;
    this.spread     = spread;
    this.random     = new Random();
  }

  public double randomizer(double value, double plusMinus) {
    double result;

    result = value+2.0*plusMinus* (random.nextDouble()-0.5);

    return result;
  }

  @Override
  public List<double[]> getAll() {
    List<double[]> result = new LinkedList<>();

    int      numLevels;
    int      remaining, bulbsOnLevel;
    double   x, y, z;
    double   alpha, alpha0;
    double   radiusDelta;
    double[] position;

    numLevels   = 1+(int)floor(sqrt(numbulbs));
    remaining   = numbulbs;
    radiusDelta = treeRadius / numLevels;

    for(int topToBottom=0; topToBottom<numLevels; topToBottom++) {
      if (0 == topToBottom) {
        bulbsOnLevel = 1;
      } else {
        bulbsOnLevel = min(2 * topToBottom, remaining);
      }

      alpha0 = PI * 2.0 / bulbsOnLevel;
      z = treeHeight - topToBottom * treeHeight / numLevels;
      for (int i = 0; i < bulbsOnLevel; i++) {
        alpha = alpha0 * i;
        x = radiusDelta * topToBottom * sin(alpha);
        y = radiusDelta * topToBottom * cos(alpha);

        position = new double[3];
        position[0] = randomizer(x, spread);
        position[1] = randomizer(y, spread);
        position[2] = randomizer(z, spread);
        result.add(0, position);

        remaining--;
      }
    }

    return result;
  }
}
