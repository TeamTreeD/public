package org.jugsaxony.treed.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract public class AbstractAnimationStrategyTest
{
  public final static double CLOSE_ENOUGH = 0.0001;

  AnimationStrategy strategy = null;

  abstract public AnimationStrategy createStrategy();

  @BeforeEach
  public void beforeEach() {
    strategy = createStrategy();
  }

  @AfterEach
  public void afterEach() {
    strategy = null;
  }

  @Test
  public void testHasName() {
    String name = strategy.getStrategyName();

    assertNotNull(name);
    assertTrue(name.length()>3);
  }

  @Test
  public void testHasDescription() {
    String description = strategy.getDescription();

    assertNotNull(description);
    assertTrue(description.length()>20);
  }

  @Test
  public void testHasAuthorName() {
    String author = strategy.getAuthorName();

    assertNotNull(author);
    assertTrue(author.length()>3);
    assertTrue(author.contains(" "));
    assertFalse(author.startsWith(" "));
    assertFalse(author.endsWith(" "));
  }

  @Test
  public void testHasAuthorEmail() {
    String email = strategy.getAuthorEmail();

    assertNotNull(email);
    assertTrue(email.length()>5);
    assertTrue(email.matches("([a-z0-9\\._]+)@([a-z0-9\\._]+)\\.(com|de|org)"));
    assertFalse(email.endsWith("hotmail.com"));
    assertFalse(email.endsWith("aol.com"));
  }
}
