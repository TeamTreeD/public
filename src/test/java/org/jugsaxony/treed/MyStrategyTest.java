package org.jugsaxony.treed;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MyStrategyTest {

  private MyStrategy myStrategy = new MyStrategy();

  @Test
  public void testAuthorEmail() {
    assertNotNull(myStrategy.getAuthorEmail()); 
  }
  
  @Test
  public void testReadyToSubmit() {
    assertTrue(myStrategy.readyToSubmit());
  }
}
