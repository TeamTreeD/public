package org.jugsaxony.treed;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MyStrategyTest {

  private MyStrategy myStrategy = new MyStrategy();

  @Test
  public void testAuthorEmail() {
    assertNotNull(myStrategy.getAuthorEmail()); 
    assertNotEquals("", myStrategy.getAuthorEmail().trim()); 
  }

  @Test
  public void testAuthorName() {
    assertNotNull(myStrategy.getAuthorName());
    assertNotEquals("", myStrategy.getAuthorName().trim()); 
  }

  @Test
  public void testDescription() {
    assertNotNull(myStrategy.getDescription());
    assertNotEquals("", myStrategy.getDescription().trim()); 
  }

  @Test
  public void testStrategyName() {
    assertNotNull(myStrategy.getStrategyName());
    assertNotEquals("", myStrategy.getStrategyName().trim()); 
  }

  @Test
  public void testReadyToSubmit() {
    assertTrue(myStrategy.readyToSubmit());
  }
}
