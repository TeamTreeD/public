package org.jugsaxony.treed.root;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

public class StringPositions implements Positions {

  private final String str;

  public StringPositions(String str) {
    this.str = str;
  }

  @Override
  public List<double[]> getAll() {
    List<double[]> result = new LinkedList<>();
    double[] coordinate;
    String separator = null;
    String line;
    BufferedReader reader;

    try {
      reader = new BufferedReader(new StringReader(str));

      while (reader.ready()) {
        line = reader.readLine();
        if (line == null) {
          break;
        }
        if (!line.startsWith("#")) {
          if (result.size() == 0) {
            separator = guessSeparator(line);
          }
          coordinate = processLine(line, separator);
          result.add(coordinate);
        }
      }

      reader.close();
    } catch (IOException ex) {
      ex.printStackTrace(System.err);
    }

    return result;
  }

  private double[] processLine(String line, String separator) {
    double[] result;
    String[] columns;

    columns = line.split(separator);
    if (columns.length == 3) {
      result = new double[3];
      for (int i = 0; i<3; i++ ) {
        result[i] = Double.parseDouble(columns[i].trim());
      }
    } else {
      throw new RuntimeException("Can't process line "+line);
    }

    return result;
  }

  private String guessSeparator(String line) {
    String result;
    int posComma, posSemicolon, posTab, posDot;

    posDot       = line.indexOf('.');  // decimal separator
    posComma     = line.indexOf(',');  // decimal separator or column separator
    posSemicolon = line.indexOf(';');  // column separator
    posTab       = line.indexOf('\t'); // column separator

    if ((posDot>-1) && (posComma>-1) && (posDot<posComma)) {
      result = ",";
    } else  if ((posDot>-1) && (posSemicolon>-1) && (posDot<posSemicolon)) {
      result = ";";
    } else  if ((posDot>-1) && (posTab>-1) && (posDot<posTab)) {
      result = "\t";
    } else  if ((posComma>-1) && (posSemicolon>-1) && (posComma<posSemicolon)) {
      result = ";";
    } else  if ((posComma>-1) && (posTab>-1) && (posComma<posTab)) {
      result = "\t";
    } else {
      throw new RuntimeException("unknown separators in csv file.");
    }

    return result;
  }
}
