package org.jugsaxony.treed;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.apache.commons.validator.routines.EmailValidator;
import org.jugsaxony.treed.api.Bulb;
import org.jugsaxony.treed.root.StringPositions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyStrategyTest {

  private MyStrategy myStrategy = new MyStrategy();

  private List<Bulb> bulbs;

  private static long invocationCounter = 0;

  public MyStrategyTest() throws IOException
  {
    URL url = getClass().getResource("/treed_01.csv");
    String str = Resources.toString(url, Charsets.UTF_8);

    StringPositions csvFile = new StringPositions(str);
    bulbs = new ArrayList<>(csvFile.getAll().size());

    int i = 0;
    for (double[] position : csvFile.getAll())
    {
      Bulb bulb = new TestBulb(i, position[0], position[1], position[2]);
      bulbs.add(i, bulb);
      i += 1;
    }
  }

  @Test
  public void testAuthorEmail() {
    assertDoesNotThrow(() -> myStrategy.getAuthorEmail(), "getAuthorEmail threw an exception");
    assertNotNull(myStrategy.getAuthorEmail(), "getAuthorEmail returned null");
    assertNotEquals("", myStrategy.getAuthorEmail().trim(), "getAuthorEmail returned a white-space only string");
    assertTrue(EmailValidator.getInstance().isValid(myStrategy.getAuthorEmail()), "getAuthorEmail did not return a well-formed email address");
  }

  @Test
  public void testAuthorName() {
    assertDoesNotThrow(() -> myStrategy.getAuthorName(), "getAuthorName threw an exception");
    assertNotNull(myStrategy.getAuthorName(), "getAuthorName returned null");
    assertNotEquals("", myStrategy.getAuthorName().trim(), "getAuthorName returned a white-space only string");
  }

  @Test
  public void testDescription() {
    assertDoesNotThrow(() -> myStrategy.getDescription(), "getDescription threw an exception");
    assertNotNull(myStrategy.getDescription(), "getDescription returned null");
    assertNotEquals("", myStrategy.getDescription().trim(), "getDescription returned a white-space only string");
  }

  @Test
  public void testStrategyName() {
    assertDoesNotThrow(() -> myStrategy.getStrategyName(), "getStrategyName threw an exception");
    assertNotNull(myStrategy.getStrategyName(), "getStrategyName returned null");
    assertNotEquals("", myStrategy.getStrategyName().trim(), "getStrategyName returned a white-space only string");
  }

  @Test
  public void testCalcBulb() {
    final long start = System.currentTimeMillis();
    for (int i = 0; i < 10; i++) {
      for (Bulb bulb : bulbs) {
        final long timestamp = start + 1000*i;
          assertDoesNotThrow(() -> myStrategy.calcBulb(bulb, timestamp), "calcBulb threw an exception");
      }
    }
    assertTrue(invocationCounter > 0, "none of the color setter methods has been invoked");
  }

  @Test
  public void testReadyToSubmit() {
    assertTrue(myStrategy.readyToSubmit(), "readyToSubmit returned false");
  }

  private class TestBulb extends Bulb {
    public TestBulb(int index, double x, double y, double z)
    {
      super(index, x, y, z);
    }

    @Override
    public void setRgb(int rgb)
    {
      super.setRgb(rgb);
      invocationCounter += 1;
    }

    @Override
    public void setR(int value)
    {
      super.setR(value);
      invocationCounter += 1;
    }

    @Override
    public void setG(int value)
    {
      super.setG(value);
      invocationCounter += 1;
    }

    @Override
    public void setB(int value)
    {
      super.setB(value);
      invocationCounter += 1;
    }
  }
}
