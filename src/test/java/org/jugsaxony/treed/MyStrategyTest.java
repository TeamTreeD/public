package org.jugsaxony.treed;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class MyStrategyTest {

  private MyStrategy myStrategy = new MyStrategy();

  @Test
  public void testAuthorEmail() {
    assertNotNull(myStrategy.getAuthorEmail()); 
  }
  
}
