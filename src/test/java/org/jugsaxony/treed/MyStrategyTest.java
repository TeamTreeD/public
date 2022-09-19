package org.jugsaxony.treed;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyStrategyTest {

  private MyStrategy myStrategy = new MyStrategy();

  @Test
  public void testAuthorEmail() {
    assertDoesNotThrow(() -> myStrategy.getAuthorEmail());
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
