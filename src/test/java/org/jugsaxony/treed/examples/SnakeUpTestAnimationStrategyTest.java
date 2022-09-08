package org.jugsaxony.treed.examples;

import org.jugsaxony.treed.api.AbstractBaseAnimationStrategyTest;
import org.jugsaxony.treed.api.AnimationStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class SnakeUpTestAnimationStrategyTest extends AbstractBaseAnimationStrategyTest
{

  @BeforeEach
  public void beforeEach() {
    super.beforeEach();
  }

  @AfterEach
  public void afterEach() {
    super.afterEach();
  }

  @Override
  public AnimationStrategy createStrategy() {
    return new SnakeUp();
  }
}
