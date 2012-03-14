// NXTRandomSim.java
// Two light sensors, polling

import ch.aplu.nxtsim.*;
import ch.aplu.jgamegrid.*;
import java.awt.*;
//import java.util.Random;

public class NXTRandomSim
{
  public NXTRandomSim()
  {
    NxtRobot robot = new NxtRobot();
    Gear gear = new Gear();
    LightSensor ls1 = new LightSensor(SensorPort.S1);
    LightSensor ls2 = new LightSensor(SensorPort.S2);
    TouchSensor ts1 = new TouchSensor(SensorPort.S3);
    robot.addPart(gear);
    robot.addPart(ls1);
    robot.addPart(ls2);
    robot.addPart(ts1);
    gear.forward();
    gear.setSpeed(250);
    //Random generator = new Random(19580427);
    int randomNumber = 500;//generator.nextInt(25);
    int x1 = 0,
        x2 = 0,
        x3 = 0,
        x4 = 0,
        x5 = 0,
        x6 = 0,
        x7 = 0,
        x8 = 0,
        y1 = 0,
        y2 = 0,
        y3 = 0,
        y4 = 0,
        y5 = 0,
        y6 = 0,
        y7 = 0,
        y8 = 0;

    int v = 500;
    double r = 0.;
    boolean both = false;
    while (true)
    {
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
      if (ts1.isPressed())
      {
          if (x1 == 0 && y1 == 0)
          {
              x1 = gear.getX();
              y1 = gear.getY();
              gear.right(350);
          }
          else if (x2 == 0 && y2 == 0)
          {
              x2 = gear.getX();
              y2 = gear.getY();
              gear.left(350);
          }
          else if (x3 == 0 && y3 == 0)
          {
              x3 = gear.getX();
              y3 = gear.getY();
              gear.left(350);
          }
          else if (x4 == 0 && y4 == 0)
          {
              x4 = gear.getX();
              y4 = gear.getY();
              gear.left(350);
          }
          else if (x5 == 0 && y5 == 0)
          {
              x5 = gear.getX();
              y5 = gear.getY();
              gear.left(350);
          }
          else if (x6 == 0 && y6 == 0)
          {
              x6 = gear.getX();
              y6 = gear.getY();
              gear.left(350);
          }
          else if (x7 == 0 && y7 == 0)
          {
              x7 = gear.getX();
              y7 = gear.getY();
              gear.left(350);
          }
          else if (x8 == 0 && y8 == 0)
          {
              x8 = gear.getX();
              y8 = gear.getY();
              gear.left(350);
          }
      }

      if (gear.getX() > (x1 - 45) && gear.getX() < (x1 + 45) 
    		  && gear.getY() > (y1 - 45) && gear.getY() < (y1 + 45) && x2 != 0)
      {
          gear.left(randomNumber);
          both = true;
      }          
      else if (gear.getX() > (x2 - 45) && gear.getX() < (x2 + 45) 
    		  && gear.getY() > (y2 - 45) && gear.getY() < (y2 + 45) && x2 != 0 && both)
      {
          gear.left(randomNumber);
      }
      else if (gear.getX() > (x3 - 45) && gear.getX() < (x3 + 45) 
    		  && gear.getY() > (y3 - 45) && gear.getY() < (y3 + 45) && x3 != 0 && both)
      {
          gear.left(randomNumber);
      }
      else if (gear.getX() > (x4 - 45) && gear.getX() < (x4 + 45) 
    		  && gear.getY() > (y4 - 45) && gear.getY() < (y4 + 45) && x4 != 0 && both)
      {
          gear.left(randomNumber);
      }
      else if (gear.getX() > (x5 - 45) && gear.getX() < (x5 + 45) 
    		  && gear.getY() > (y5 - 45) && gear.getY() < (y5 + 45) && x5 != 0 && both)
      {
          gear.left(randomNumber);
      }
      else if (gear.getX() > (x6 - 45) && gear.getX() < (x6 + 45) 
    		  && gear.getY() > (y6 - 45) && gear.getY() < (y6 + 45) && x6 != 0 && both)
      {
          gear.left(randomNumber);
      }
      else if (gear.getX() > (x7 - 45) && gear.getX() < (x7 + 45) 
    		  && gear.getY() > (y7 - 45) && gear.getY() < (y7 + 45) && x7 != 0 && both)
      {
          gear.left(randomNumber);
      }
      else if (gear.getX() > (x8 - 45) && gear.getX() < (x8 + 45) 
    		  && gear.getY() > (y8 - 45) && gear.getY() < (y8 + 45) && x8 != 0 && both)
      {
          gear.left(randomNumber);
      }
    }
  }

  @SuppressWarnings("unused")
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