// NXTRandomSim.java
// Two light sensors, polling

import ch.aplu.nxtsim.*;
import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.util.*;
/*
// Used for obstacle creation
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;*/

public class NXTRandomSim
{
  static Random generator = new Random(System.currentTimeMillis());
  NxtRobot robot = new NxtRobot();
  Gear gear = new Gear();
  LightSensor ls1 = new LightSensor(SensorPort.S1);
  LightSensor ls2 = new LightSensor(SensorPort.S2);
  TouchSensor ts1 = new TouchSensor(SensorPort.S3);
  LinkedList<Point> obstacleList = new LinkedList<Point>();
  Point currentPosition = new Point();
  Point obstaclePosition = new Point();
  static int randomXCoordinate;
  static int randomYCoordinate;
  int randomNumber = generator.nextInt(500);

  public NXTRandomSim()
  {
    robot.addPart(gear);
    robot.addPart(ls1);
    robot.addPart(ls2);
    robot.addPart(ts1);
    gear.forward();
    gear.setSpeed(110);

    while (true)
    {
      currentPosition.setLocation(gear.getX(), gear.getY());
      if (ts1.isPressed())
      {
        if (!obstacleList.contains(currentPosition))
        {
          obstacleList.add(new Point(currentPosition));
          gear.backward(600);
          //gear.setDirection(randomNumber - gear.getDirection());
          gear.right(200);
          gear.forward();
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
            //gear.setDirection(randomNumber - gear.getDirection());
            gear.forward();
            System.out.println(currentPosition.distance(obstaclePosition));
            System.out.println(gear.getDirection());
          }
        }
      }
      if (gear.getX() < 0 || gear.getX() > 500 || gear.getY() < 0 || gear.getY() >500)
      {
        gear.backward(3000);
        //gear.setDirection(randomNumber - gear.getDirection());
        gear.forward();
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
      gear.left(1);
      gear.forward();
    }
    if (v1 < v && v2 > v)
      gear.rightArc(r);
    if (v1 > v && v2 < v)
      gear.leftArc(r);
    if (v1 > v && v2 > v)
      gear.backward();
  }

  @SuppressWarnings("unused")
  private static void _init(GameGrid gg)
  {
    GGBackground bg = gg.getBg();
    bg.setPaintColor(Color.black);
    bg.fillRectangle(new Point(9,9), new Point(491,491));
  }

  public static void main(String[] args)
  {
    new NXTRandomSim();
  }
/*
    public static void obstacleFactory() 
    {
        int width = 30;
        int height = 30;

        // Create a buffered image in which to draw
        //BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        GraphicsEnvironment environment = 
                GraphicsEnvironment.getLocalGraphicsEnvironment();

        GraphicsDevice device = 
                environment.getDefaultScreenDevice();

        GraphicsConfiguration config = device.getDefaultConfiguration();

        // Create an image that supports transparent pixels
        BufferedImage bufferedImage = config.createCompatibleImage(width, height, 
                Transparency.TRANSLUCENT);

        // Create a graphics contents on the buffered image
        Graphics2D g2d = bufferedImage.createGraphics();

        // Draw graphics
        g2d.setColor(Color.yellow);
        g2d.fillOval(0, 0, width, height);

        // Graphics context no longer needed so dispose it
        g2d.dispose();


        RenderedImage rendImage = bufferedImage;
        //return bufferedImage; 
        // Write generated image to a file
        try {
            // Save as PNG
            File file = new File("src/sprites/Circle.jpg");
            ImageIO.write(rendImage, "jpg", file);
        } catch (IOException e) {
        }
    }*/

  // ------------------ Environment --------------------------
  static
  {        // TODO get obstacle generation to work
    //RenderedImage rendImage = obstacleFactory();
/*
        obstacleFactory();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

    NxtContext.setStartPosition(250,250);
    NxtContext.setStartDirection(90);
    for(int i = 0; i < 10; i++) {
      randomXCoordinate = generator.nextInt(475) + 10;
      randomYCoordinate = generator.nextInt(475) + 10;
      NxtContext.useObstacle("sprites/Obstacle.png", randomXCoordinate, randomYCoordinate);
    }
    /*    NxtContext.useObstacle("sprites/Obstacle.png", 250, 25);
  NxtContext.useObstacle("sprites/Obstacle.png", 250, 475);
  NxtContext.useObstacle("sprites/Obstacle.png", 400, 250);
  NxtContext.useObstacle("sprites/Obstacle.png", 100, 250);
  NxtContext.useObstacle("sprites/Obstacle.png", 175, 350);
  NxtContext.useObstacle("sprites/Obstacle.png", 325, 350);
  NxtContext.useObstacle("sprites/Obstacle.png", 175, 150);
  NxtContext.useObstacle("sprites/Obstacle.png", 325, 150);*/
  }
}

//http://www.java-tips.org/java-se-tips/java.awt.geom/how-to-create-a-buffered-image-2.html