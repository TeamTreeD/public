package org.jugsaxony.treed.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UtilsTest {
  public static final double CLOSE_ENOUGH = 0.001;

  Utils utils = null;

  @BeforeEach
  public void beforeEach() {
    utils = new Utils();
  }

  @AfterEach
  public void afterEach() {
    utils = null;
  }

  @Test
  public void testFadeAll() {
    List<Bulb> bulbs = new LinkedList<>();
    Bulb bulb;

    for (int i=0; i<10; i++) {
      bulb = new Bulb(i, 0.0, 1.1, 2.2);
      bulb.setRgb(0x00ffffff);
      bulbs.add(bulb);
    }

    utils.fadeAll(bulbs, 128);
    for (Bulb bulb1:bulbs) {
      assertEquals(0x007f7f7f, bulb1.getRgb());
    }

    utils.fadeAll(bulbs, 128);
    for (Bulb bulb2:bulbs) {
      assertEquals(0x00000000, bulb2.getRgb());
    }
  }

  @Test
  public void testCalcRainbow() {
    long first, second, third;

    first  = utils.calcRainbow(0.0);
    second = utils.calcRainbow(0.0);

    assertEquals(first, second, CLOSE_ENOUGH, "reproduzierbar");

    third = utils.calcRainbow(0.01);

    assertNotEquals(first, third, "ist abhaengig vom Input");
  }

  @Test
  public void testScale() {
    assertEquals(2.0, utils.scale(0.0,-0.1, 0.2,  2.0, /*?*/ 4.0), CLOSE_ENOUGH);
    assertEquals(2.0, utils.scale(0.0, 0.0, 0.2,  2.0, /*?*/ 4.0), CLOSE_ENOUGH);
    assertEquals(3.0, utils.scale(0.0, 0.1, 0.2,  2.0, /*?*/ 4.0), CLOSE_ENOUGH);
    assertEquals(4.0, utils.scale(0.0, 0.2, 0.2,  2.0, /*?*/ 4.0), CLOSE_ENOUGH);
    assertEquals(4.0, utils.scale(0.0, 0.3, 0.2,  2.0, /*?*/ 4.0), CLOSE_ENOUGH);

    assertEquals(4.0, utils.scale(0.0,-0.1, 0.2,  4.0, /*?*/ 2.0), CLOSE_ENOUGH);
    assertEquals(4.0, utils.scale(0.0, 0.0, 0.2,  4.0, /*?*/ 2.0), CLOSE_ENOUGH);
    assertEquals(3.0, utils.scale(0.0, 0.1, 0.2,  4.0, /*?*/ 2.0), CLOSE_ENOUGH);
    assertEquals(2.0, utils.scale(0.0, 0.2, 0.2,  4.0, /*?*/ 2.0), CLOSE_ENOUGH);
    assertEquals(2.0, utils.scale(0.0, 0.3, 0.2,  4.0, /*?*/ 2.0), CLOSE_ENOUGH);

    assertEquals(2.0, utils.scale(0.2, 0.3, 0.0,  2.0, /*?*/ 4.0), CLOSE_ENOUGH);
    assertEquals(2.0, utils.scale(0.2, 0.2, 0.0,  2.0, /*?*/ 4.0), CLOSE_ENOUGH);
    assertEquals(3.0, utils.scale(0.2, 0.1, 0.0,  2.0, /*?*/ 4.0), CLOSE_ENOUGH);
    assertEquals(4.0, utils.scale(0.2, 0.0, 0.0,  2.0, /*?*/ 4.0), CLOSE_ENOUGH);
    assertEquals(4.0, utils.scale(0.2,-0.1, 0.0,  2.0, /*?*/ 4.0), CLOSE_ENOUGH);

    assertEquals(4.0, utils.scale(0.2, 0.3, 0.0,  4.0, /*?*/ 2.0), CLOSE_ENOUGH);
    assertEquals(4.0, utils.scale(0.2, 0.2, 0.0,  4.0, /*?*/ 2.0), CLOSE_ENOUGH);
    assertEquals(3.0, utils.scale(0.2, 0.1, 0.0,  4.0, /*?*/ 2.0), CLOSE_ENOUGH);
    assertEquals(2.0, utils.scale(0.2, 0.0, 0.0,  4.0, /*?*/ 2.0), CLOSE_ENOUGH);
    assertEquals(2.0, utils.scale(0.2,-0.1, 0.0,  4.0, /*?*/ 2.0), CLOSE_ENOUGH);

    assertEquals(29.0, utils.scale(-10,-1.0, 10.0,  20, /*?*/ 40.0), CLOSE_ENOUGH);
  }
}
