package org.jugsaxony.treed.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BulbTest {
  Bulb bulb;

  @BeforeEach
  public void beforeEach() {
    bulb = new Bulb(0, 0.0, 0.0, 0.0);
    bulb.setRgb(0x00000000);
  }

  @AfterEach
  public void afterEach() {
    bulb = null;
  }

  @Test
  public void testSetR() {
    bulb.setRgb(0x00000000);
    bulb.setR(0xff);
    assertEquals(0x00ff0000, bulb.getRgb());
    bulb.setR(0x1ff);
    assertEquals(0x00ff0000, bulb.getRgb());
  }

  @Test
  public void testSetG() {
    bulb.setRgb(0x00000000);
    bulb.setG(0xff);
    assertEquals(0x0000ff00, bulb.getRgb());
    bulb.setG(0x1ff);
    assertEquals(0x0000ff00, bulb.getRgb());
  }

  @Test
  public void testSetB() {
    bulb.setRgb(0x00000000);
    bulb.setB(0xff);
    assertEquals(0x000000ff, bulb.getRgb());
    bulb.setB(0x1ff);
    assertEquals(0x000000ff, bulb.getRgb());
  }
}
