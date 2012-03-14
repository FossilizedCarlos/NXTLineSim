// NXTSim.java
// Two light sensors, polling

import ch.aplu.nxtsim.*;
import ch.aplu.jgamegrid.*;
import java.awt.*;

public class NXTLineSimOrig
{
  public NXTLineSimOrig()
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
    gear.setSpeed(110);
    int x1 = 0,
        x2 = 0,
        y1 = 0,
        y2 = 0;

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
              gear.right(400);
          }
          else if (x2 == 0 && y2 == 0)
          {
              x2 = gear.getX();
              y2 = gear.getY();
              gear.left(400);
          }
      }

      if (gear.getX() > (x1 + 35) && gear.getX() < (x1 + 85) 
    		  && gear.getY() > (y1) && gear.getY() < (y1 + 85) && x2 != 0)
      {
          gear.left(2);
          both = true;
      }          
      else if (gear.getX() > (x2) && gear.getX() < (x2 + 110) 
    		  && gear.getY() > (y2 - 120) && gear.getY() < (y2-60) && x2 != 0 && both)
      {
          gear.left(2);
      }
    }
  }

  @SuppressWarnings("unused")
	private static void _init(GameGrid gg)
  {
    GGBackground bg = gg.getBg();
    bg.setPaintColor(Color.black);
    bg.fillArc(new Point(250, 250), 240, 0, 360);
    bg.setPaintColor(Color.white);
    bg.fillArc(new Point(250, 250), 180, 0, 360);
  }

  public static void main(String[] args)
  {
    new NXTLineSimOrig();
  }

  // ------------------ Environment --------------------------
  static
  {
        NxtContext.setStartPosition(50,250);
        NxtContext.setStartDirection(90);
        NxtContext.useObstacle("sprites/Obstacle.png", 250, 25);
        NxtContext.useObstacle("sprites/Obstacle.png", 25, 250);
  }
}