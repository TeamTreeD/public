package org.jugsaxony.treed.root;

import org.jugsaxony.treed.api.AnimationStrategy;
import org.jugsaxony.treed.api.Bulb;

import java.util.List;

public abstract class AbstractMain
{
  private static String KEY_POSITIONS = "--positions=";
  private static String KEY_STRATEGY  = "--strategy=";

  List<double[]>    positions;
  AnimationStrategy strategy;

  public List<double[]> loadPositions(String fileName) {
    List<double[]> result;
    FilePositions csvFile;

    csvFile = new FilePositions(fileName);
    result = csvFile.getAll();

    return result;
  }

  public AnimationStrategy loadStrategy(String className) {
    AnimationStrategy result;

    try {
      result = (AnimationStrategy) (Class.forName(className).newInstance());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  public static void printUsage() {
    System.out.println("java org.jugsaxony.treed.raspi.Main --positions=<csvFile> --strategy=<className>\n");
  }

  public void execute() {
    List<Bulb> bulbs;
    long timeStamp;

    System.out.println(strategy.getAuthorName()+" : "+strategy.getStrategyName());

    bulbs = createBulbs(positions);
    strategy.onStartAnimation(bulbs, System.currentTimeMillis());
    do {
      timeStamp = System.currentTimeMillis();
      strategy.onStartFrame(bulbs, timeStamp);
      strategy.calcFrame(bulbs, timeStamp);
      strategy.onEndFrame(bulbs, System.currentTimeMillis());
    } while (goAhead());
    strategy.onEndAnimation(bulbs, timeStamp);
  }

  protected abstract List<Bulb> createBulbs(List<double[]> positions);

  public boolean goAhead() {
    return true;
  }

  public boolean processArgs(String[] args) {
    boolean result;
    boolean positionaOk = false;
    boolean strategyOk = false;

    result = (args.length == 2);
    for (String arg:args) {

      if (arg.startsWith(KEY_POSITIONS)) {

        positions = loadPositions(arg.substring(KEY_POSITIONS.length()));
        positionaOk = (positions != null) && (positions.size()>0);

      } else if (arg.startsWith(KEY_STRATEGY)) {

        strategy = loadStrategy(arg.substring(KEY_STRATEGY.length()));
        strategyOk = (strategy != null);

      } else {
        result = false;
      }
    }

    result = result && positionaOk && strategyOk;

    return result;
  }
}
