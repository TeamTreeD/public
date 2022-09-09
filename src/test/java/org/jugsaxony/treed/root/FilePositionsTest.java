package org.jugsaxony.treed.root;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilePositionsTest {

  @Test
  public void testLoadingFile1() {
    Positions coordinates;
    List<double[]> list;

    coordinates = new FilePositions("./src/main/resources/treed_01.csv");

    list = coordinates.getAll();

    assertEquals(250, list.size());
  }

  @Test
  public void testLoadingFile2() {
    Positions coordinates;
    List<double[]> list;

    coordinates = new FilePositions("./src/main/resources/treed_02.csv");

    list = coordinates.getAll();
    assertEquals(400, list.size());
  }
}
