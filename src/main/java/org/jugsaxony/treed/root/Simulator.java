package org.jugsaxony.treed.root;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import org.jugsaxony.treed.api.AnimationStrategy;
import org.jugsaxony.treed.api.Bulb;
import org.jugsaxony.treed.api.Constants;
import org.jugsaxony.treed.examples.*;

import java.io.File;
import java.util.*;

/**
 * Main class of JavaFX simulator (call -h for help).
 * The positions of all Bulbs and the name of the animation can be passed in as parameter.
 */
public class Simulator extends Application implements Constants {
  public static final double BULB_DIAMETER = 0.01;
  public static final double BULB_SPREAD   = 2*BULB_DIAMETER;
  public static final int    WINDOW_HEIGHT = 1000;
  public static final int    WINDOW_WIDTH  = WINDOW_HEIGHT*3/4;
  public static final double SCALE         = 0.75 * (double) (WINDOW_HEIGHT / TREE_HEIGHT);

  public static final String KEY_STRATEGY  = "strategy";
  public static final String KEY_POSITIONS = "positions";

  private double anchorX, anchorY;
  private double anchorAngleX = 0, anchorAngleY = 0;

  private final DoubleProperty angleX = new SimpleDoubleProperty(0);
  private final DoubleProperty angleY = new SimpleDoubleProperty(0);

  private List<Bulb> bulbs;
  private AnimationStrategy strategy;

  public static void main(String[] args) {
    launch(args);
  }

  public Shape3D createBulbShape3d(Bulb3d bulb3d, double diameter) {
    Shape3D       result;
    PhongMaterial material;

    material = new PhongMaterial();
    material.setDiffuseColor(Color.LIME);
    material.setSpecularPower(0.1);

    material.diffuseColorProperty().bind(bulb3d.getColorProperty());

    result = new Sphere(diameter);
    result.setTranslateX(bulb3d.getX());
    result.setTranslateY(bulb3d.getY());
    result.setTranslateZ(bulb3d.getZ());
    result.setMaterial(material);

    return result;
  }

  public Bulb3d createBulb3d(int index, double x, double y, double z){
    Bulb3d result;

    result = new Bulb3d(index, x, y, z);

    return result;
  }

  public List<Bulb> createBulb3ds(List<double[]> ledCoordinates) {
    List<Bulb> result = new ArrayList<>(ledCoordinates.size());
    Bulb       bulb;
    double[]   coordinates;

    for (int i =0; i<ledCoordinates.size(); i++) {
      coordinates = ledCoordinates.get(i);
      bulb = createBulb3d(i, coordinates[0], coordinates[1], coordinates[2]);
      result.add(bulb);
    }

    return result;
  }

  public Group createTree(List<Bulb> bulb3ds, double bulbDiameter) {
    Group   result;
    Shape3D shape3d;

    result = new Group();

    for (Bulb bulb:bulb3ds) {
      shape3d = createBulbShape3d((Bulb3d)bulb, bulbDiameter);
      result.getChildren().add(shape3d);
    }

    result.setScaleX(SCALE);
    result.setScaleY(SCALE);
    result.setScaleZ(SCALE);

    return result;
  }

  public Camera createCamera() {
    Camera result;

    result = new PerspectiveCamera(false);
    result.setTranslateX(-WINDOW_WIDTH/2);
    result.setTranslateY(-WINDOW_HEIGHT/2);
    result.setTranslateZ(0);

    return result;
  }

  private void initKeyboardControl(Scene szene) {
    szene.setOnKeyPressed(event -> {
      Platform.exit();
    });
  }

  private void initMouseControl(Group group, Scene scene) {
    Rotate xRotate, zRotate;

    group.getTransforms().addAll(
            xRotate = new Rotate(0, Rotate.X_AXIS),
            zRotate = new Rotate(0, Rotate.Z_AXIS)
    );
    xRotate.angleProperty().bind(angleX);  // Dragging horizontally will rotate around the tree trunk.
    zRotate.angleProperty().bind(angleY);  // Dragging vertically will offer views from below or above and anything in between.

    scene.setOnMousePressed(event -> {
      anchorX = event.getSceneX();
      anchorY = event.getSceneY();
      anchorAngleX = angleX.get();
      anchorAngleY = angleY.get();
    });

    scene.setOnMouseDragged(event -> {
      angleX.set(anchorAngleX - (anchorY - event.getSceneY())/4);
      angleY.set(anchorAngleY - (anchorX - event.getSceneX())/8);
    });

    scene.setOnMouseClicked(event -> {
      if (2 == event.getClickCount()) { // Doubleclick resets all rotation.
        angleX.set(0);
        angleY.set(0);
      }
    });
  }

  private void initStartTimer() {
    Timer timerStart;
    TimerTask taskStart;

    taskStart = new TimerTask() {
      @Override
      public void run() {
        processStartAnimation();
      }
    };

    timerStart = new Timer("StartAnimation");
    timerStart.schedule(taskStart, 0);
  }

  private void initFrameTimer() {
    Timer timerFrame;
    TimerTask taskFrame;

    taskFrame = new TimerTask() {
      @Override
      public void run() {
        processFrame();
      }
    };

    timerFrame = new Timer("Frame");
    timerFrame.scheduleAtFixedRate(taskFrame, 100, 100);
  }

  public Scene createSzene(Group tree, Camera camera) {
    Scene result;
    Group root;

    root = new Group(tree);
    root.setRotationAxis(Rotate.X_AXIS);
    root.setRotate(90);

    result = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, true);
    result.setCamera(camera);
    result.setFill(new Color(0.1d, 0.1d, 0.1d, 1.0d));
    initKeyboardControl(result);
    initMouseControl(root, result);
    initStartTimer();
    initFrameTimer();

    return result;
  }

  public void start(Stage stage) {
    Group  tree;
    Camera camera;
    Scene  scene;
    Parameters params;
    Positions positions;

    if (!Platform.isSupported(ConditionalFeature.SCENE3D)) {
      System.err.println("Sorry, 3D is not supported in JavaFX on this platform.");
      System.exit(1);
    }

    params = getParameters();

    if (!areParametersOk(params)) {
      printUsage();
      System.exit(1);
    }

    strategy  = createStrategy(params);
    positions = createPositions(params);
    bulbs     = createBulb3ds(positions.getAll());    // create bulbs for animation
    tree      = createTree(bulbs, BULB_DIAMETER); // create bulbs for 3D FX
    camera    = createCamera();
    scene     = createSzene(tree, camera);

    stage.setOnCloseRequest(event -> {
      Platform.exit();
    });

    stage.setResizable(false);
    stage.setTitle("TreeD");
    stage.setScene(scene);
    stage.show();
  }

  private boolean areParametersOk(Parameters params) {
    boolean result = true;
    Map<String, String> named;
    named = params.getNamed();
    result &= isContainingCorrectKeysOnly(named);
    result &= isValidFileIfGiven(named);
    result &= isValidClassNameIfGiven(named);
    result &= (params.getUnnamed().size()==0);
    return result;
  }

  private boolean isValidFileIfGiven(Map<String, String> map) {
    boolean result;
    String fileName;
    File   file;

    fileName = map.get(KEY_POSITIONS);
    if (fileName != null) {
      file = new File(fileName);
      result = file.exists();
    } else {
      result = true;
    }

    return result;
  }

  private boolean isValidClassNameIfGiven(Map<String, String> map) {
    boolean result;
    String  className;
    Class   clazz;
    Class[] interfaces;
    className = map.get(KEY_STRATEGY);
    if (className != null) {
      try {
        clazz = Class.forName(className);

        // look at interfaces along hierarchy for AnimationStrategy
        result = false;
        do {
          interfaces = clazz.getInterfaces();
          for (Class inter:interfaces) {
            if (AnimationStrategy.class.getCanonicalName().equals(inter.getCanonicalName())) {
              result = true;
              break;
            } // no else
          }
          clazz = clazz.getSuperclass();
        } while ((clazz != null) && (!result));

      } catch (ClassNotFoundException e) {
        result = false;
      }
    } else {
      result = true;
    }

    return result;
  }

  private boolean isContainingCorrectKeysOnly(Map<String, String> map) {
    boolean result = true;

    for (String key:map.keySet()) {
      if (!key.equals(KEY_POSITIONS) && !key.equals(KEY_STRATEGY)) {
        result = false;
      }
    }

    return result;
  }

  private void printUsage() {
    System.err.println("Usage: \n\n"+
            "java "+getClass().getCanonicalName()+
            " [-"+KEY_STRATEGY+" <className>]"+
            " [-"+KEY_POSITIONS+" <fileName.csv>]\n\n"+
            "Don't forget to set the CLASSPATH to your strategy and this jar.\n\n");
  }

  private AnimationStrategy createStrategy(Parameters params) {
    AnimationStrategy result;
    Map<String, String> named;

    named = params.getNamed();
    if (named.containsKey(KEY_STRATEGY)) {
      try {
        result = (AnimationStrategy) Class.forName(named.get(KEY_STRATEGY)).newInstance();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    } else {
      result = new VerticalRainbow();
    }
    return result;
  }

  private Positions createPositions(Parameters params) {
    Positions result;
    Map<String, String> named;

    named = params.getNamed();
    if (named.containsKey(KEY_POSITIONS)) {
      result = new FilePositions(named.get(KEY_POSITIONS));
    } else {
      result = new RandomPositions(NUM_BULBS, TREE_RADIUS, TREE_HEIGHT, BULB_SPREAD);
    }
    return result;
  }

  @Override
  public void stop() {
    processEndAnimation();
    System.exit(0);
  }

  public void processStartAnimation() {
    long timestamp;

    System.out.println(strategy.getAuthorName()+" : "+strategy.getStrategyName());

    timestamp = System.currentTimeMillis();
    strategy.onStartAnimation(bulbs, timestamp);
  }

  public void processFrame() {
    long timestamp;

    timestamp = System.currentTimeMillis();
    strategy.onStartFrame(bulbs, timestamp);
    strategy.calcFrame(bulbs, timestamp);
    strategy.onEndFrame(bulbs, System.currentTimeMillis());
  }

  public void processEndAnimation() {
    long timestamp;

    timestamp = System.currentTimeMillis();
    strategy.onEndAnimation(bulbs, timestamp);
  }
}