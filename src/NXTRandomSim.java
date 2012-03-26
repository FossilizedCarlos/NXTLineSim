// NXTRandomSim.java
// Two light sensors, polling

import ch.aplu.nxtsim.*;
import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.util.*;

public class NXTRandomSim
{
  Random generator = new Random(19580427);
  NxtRobot robot = new NxtRobot();
  Gear gear = new Gear();
  LightSensor ls1 = new LightSensor(SensorPort.S1);
  LightSensor ls2 = new LightSensor(SensorPort.S2);
  TouchSensor ts1 = new TouchSensor(SensorPort.S3);
  LinkedList<Point> obstacleList = new LinkedList<Point>();
  Point currentPosition = new Point();
  Point obstaclePosition = new Point();

  public NXTRandomSim()
  {
    robot.addPart(gear);
    robot.addPart(ls1);
    robot.addPart(ls2);
    robot.addPart(ts1);
    gear.forward();
    gear.setSpeed(110);
    //Random generator = new Random(19580427);
    int randomNumber = 500;//generator.nextInt(25);

    boolean inside = true;
    while (true)
    {
      currentPosition.setLocation(gear.getX(), gear.getY());
      if (ts1.isPressed())
      {
        if (!obstacleList.contains(currentPosition))
        {
          obstacleList.add(new Point(currentPosition));
          gear.backward(600);
          gear.right(200);
          System.out.println("("+obstacleList.getLast().getX()+", "+ obstacleList.getLast().getY()+")");
        }
        checkLineColor();
      }
      synchronized(obstacleList)
      {
        ListIterator<Point> obstacleListChecker = obstacleList.listIterator();
        while (obstacleListChecker.hasNext())
        {
          obstaclePosition = obstacleListChecker.next();
          if (currentPosition.distance(obstaclePosition) < 40){
            gear.backward(600);
            gear.left(200);
            gear.forward();
            System.out.println(currentPosition.distance(obstaclePosition));}
        }
      }
      if (gear.getX() < 0 || gear.getX() > 500 || gear.getY() < 0 || gear.getY() >500)
      {
        gear.backward(6000);
        //gear.forward();
      }
      checkLineColor();
    }
  }

  public void checkLineColor()
  {
    int v = 500;
    double r = 0.;
    int v1 = ls1.getValue();
    int v2 = ls2.getValue();
    if (v1 < v && v2 < v) {
      gear.forward();
    }
    if (v1 < v && v2 > v)
      gear.rightArc(r);
    if (v1 > v && v2 < v)
      gear.leftArc(r);
    if (v1 > v && v2 > v)
      gear.backward();
  }

	private static void _init(GameGrid gg)
  {
    GGBackground bg = gg.getBg();
    bg.setPaintColor(Color.black);
    bg.fillArc(new Point(250, 250), 240, 0, 360);
  }

  public static void main(String[] args)
  {
    new NXTRandomSim();
  }

  // ------------------ Environment --------------------------
  static
  {
        NxtContext.setStartPosition(250,250);
        NxtContext.setStartDirection(90);
        NxtContext.useObstacle("sprites/Obstacle.png", 250, 25);
        NxtContext.useObstacle("sprites/Obstacle.png", 250, 475);
        NxtContext.useObstacle("sprites/Obstacle.png", 400, 250);
        NxtContext.useObstacle("sprites/Obstacle.png", 100, 250);
        NxtContext.useObstacle("sprites/Obstacle.png", 175, 350);
        NxtContext.useObstacle("sprites/Obstacle.png", 325, 350);
        NxtContext.useObstacle("sprites/Obstacle.png", 175, 150);
        NxtContext.useObstacle("sprites/Obstacle.png", 325, 150);
  }
}