package org.jugsaxony.treed.root;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import org.jugsaxony.treed.api.Bulb;

public class Bulb3d extends Bulb {

  private SimpleObjectProperty<Color> colorProperty = new SimpleObjectProperty<>(this, "color");

  public Bulb3d(int index, double x, double y, double z) {
    super(index, x, y, z);
  }

  @Override
  public void setRgb(int rgb) {
    int red, green, blue;

    super.setRgb(rgb);
    red   = rgb>>16 & 0xff;
    green = rgb>>8 & 0xff;
    blue  = rgb & 0xff;
    setColor((Color.rgb(red, green, blue)));
  }

  public Color getColor() {
    return colorProperty.get();
  }

  public void setColor(Color value) {
    colorProperty.set(value);
  }

  public SimpleObjectProperty<Color> getColorProperty() {
    return colorProperty;
  }
}
