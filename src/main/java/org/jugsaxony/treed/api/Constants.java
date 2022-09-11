package org.jugsaxony.treed.api;

/**
 * What you need to know about TreeD:
 *
 * The tree lives in a quite mathematical environment.
 * The coordinate origin (0,0,0) is in the center of the tree at the bottom.
 * The diameter at the bottom is 1.0, so the radius is 0.5 .
 * The height is assumed to be 1.25 .
 * There can always be some LED bulbs that are located a bit outside of this system.
 *
 */
public interface Constants {

  int NUM_BULBS = 400;

  /**
   * Assume the tree has a diameter of one and design your algorithm accordingly.
   */
  double TREE_RADIUS   = 0.5;

  /**
   * Assume the tree is about 1.25 as high as it's diameter.
   */
  double TREE_HEIGHT   = 1.25;


  int RGB_BLACK  = 0x00000000;
  int RGB_BLUE   = 0x000000ff;
  int RGB_GREEN  = 0x0000ff00;
  int RGB_CYAN   = 0x0000ffff;
  int RGB_RED    = 0x00ff0000;
  int RGB_VIOLET = 0x00ff00ff;
  int RGB_YELLOW = 0x00ffff00;
  int RGB_WHITE = 0x00ffffff;
}
