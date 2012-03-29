// NXTRandomSim.java
// Two light sensors, polling

import ch.aplu.nxtsim.*;
import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;


/*
// Used for obstacle creation
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
 */

public class NXTRandomSim implements MouseListener {
  static Random generator = new Random(System.currentTimeMillis());
  static Random generatorX = new Random(3 * System.currentTimeMillis());
  static Random generatorY = new Random(7 * System.currentTimeMillis());
  NxtRobot robot = new NxtRobot();
  Gear gear = new Gear();
  LightSensor ls1 = new LightSensor(SensorPort.S1);
  LightSensor ls2 = new LightSensor(SensorPort.S2);
  TouchSensor ts1 = new TouchSensor(SensorPort.S3);
  LinkedList<Point> placementList = new LinkedList<Point>();
  LinkedList<Point> obstacleList = new LinkedList<Point>();
  Point currentPosition = new Point();
  Point obstaclePosition = new Point();
  static int randomXCoordinate;
  static int randomYCoordinate;
  int randomNumber = generator.nextInt(500);
  int reset = 0;

  Container contentPane;

  public NXTRandomSim() {
    robot.addPart(gear);
    robot.addPart(ls1);
    robot.addPart(ls2);
    robot.addPart(ts1);
    gear.forward();
    gear.setSpeed(110);

    while (true) {
      currentPosition.setLocation(gear.getX(), gear.getY());
      if (ts1.isPressed()) {
        if (!obstacleList.contains(currentPosition)) {
          if (gear.getDirection() >= 0 && gear.getDirection() < 90)
            obstacleList.add(new Point((int) (currentPosition.getX() + 15 * Math.cos(gear.getDirection())), (int) (currentPosition.getY() + 15 * Math.sin(gear.getDirection()))));
          if (gear.getDirection() >= 90 && gear.getDirection() < 180)
            obstacleList.add(new Point((int) (currentPosition.getX() + 15 * Math.cos(gear.getDirection())), (int) (currentPosition.getY() + 15 * Math.sin(gear.getDirection()))));
          if (gear.getDirection() >= 180 && gear.getDirection() < 270)
            obstacleList.add(new Point((int) (currentPosition.getX() + 15 * Math.cos(gear.getDirection())), (int) (currentPosition.getY() + 15 * Math.sin(gear.getDirection()))));
          else
            obstacleList.add(new Point((int) (currentPosition.getX() + 15 * Math.cos(gear.getDirection())), (int) (currentPosition.getY() + 15 * Math.sin(gear.getDirection()))));
          //System.out.println("Obstacle " + obstacleList.size());
          //System.out.println("Headed " + gear.getDirection());
          gear.backward(600);
          //gear.setDirection(randomNumber - gear.getDirection());
          if (generator.nextInt(500) < 125)
            gear.left(200);
          else if (generator.nextInt(500) > 375)
            gear.right(200);
          gear.forward();
          //System.out.println("(" + obstacleList.getLast().getX() + ", " + obstacleList.getLast().getY() + ")");
          //System.out.println(obstacleList.toString());
        }
        checkLineColor();
      }
      synchronized (obstacleList) {
        ListIterator<Point> obstacleListChecker = obstacleList.listIterator();
        while (obstacleListChecker.hasNext()) {
          obstaclePosition = obstacleListChecker.next();
          if (currentPosition.distance(obstaclePosition) < 40) {
            gear.backward(600);
            if (generator.nextInt(500) < 250)
              gear.left(200);
            else
              gear.right(200);
            //gear.setDirection(randomNumber - gear.getDirection());
            gear.forward();
            //System.out.println("Distance " + currentPosition.distance(obstaclePosition));
            //System.out.println("Headed " + gear.getDirection());
          }
        }
      }
      if (gear.getX() < 0 || gear.getX() > 500 || gear.getY() < 0 || gear.getY() > 500) {
        //gear.backward(1500);
        //gear.setDirection(randomNumber - gear.getDirection());
        //gear.forward();
        robot.reset();
        reset++;
        //System.out.println("Reset " + reset);
        //System.out.println("Obstacle " + obstacleList.size());
      }
      checkLineColor();
    }
  }

  public void checkLineColor() {
    int v = 500;
    double r = 0.;
    int v1 = ls1.getValue();
    int v2 = ls2.getValue();
    if (v1 < v && v2 < v) {
      gear.forward();
      if (generator.nextInt(10) + 1 < 2)
        gear.left(20);
      else if (generator.nextInt(10) + 1 > 8)
        gear.right(15);
    }
    if (v1 < v && v2 > v)
      gear.rightArc(r);
    if (v1 > v && v2 < v)
      gear.leftArc(r);
    if (v1 > v && v2 > v) {
      gear.backward(100);
      gear.right(15);
    }
  }

  @SuppressWarnings("unused")
  private static void _init(GameGrid gg) {
    GGBackground bg = gg.getBg();
    //bg.setBgColor(Color.black);
    //bg.setPaintColor(Color.white);
    bg.setPaintColor(Color.black);
    bg.fillRectangle(new Point(9, 9), new Point(491, 491));
  }

  public static void main(String[] args) {
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
       } catch (IOException e) {}
   }
  */
  //------------------ Environment --------------------------
  static {        // TODO get obstacle generation to work
    //RenderedImage rendImage = obstacleFactory();
    /*
   obstacleFactory();
   try {
       Thread.sleep(5000);
   } catch (InterruptedException e) {
     System.out.println("Error...");
   }
    */
    NxtContext.setStartPosition(250, 250);
    NxtContext.setStartDirection(90);
    //NxtContext.useObstacle("sprites/9Circles.jpg", 100, 100);
    //NxtContext.useObstacle("sprites/Connected.png", 100, 100);
    for (int i = 0; i < 10; i++) {
      randomXCoordinate = generatorX.nextInt(475) + 10;
      randomYCoordinate = generatorY.nextInt(475) + 10;
      if (randomXCoordinate > 25 && randomXCoordinate < 475 && randomYCoordinate > 25 && randomYCoordinate < 475) {
        NxtContext.useObstacle("sprites/Circle.png", randomXCoordinate, randomYCoordinate);
        System.out.println("(" + randomXCoordinate + ", " + randomYCoordinate + ")");
      } else
        i--;
      NxtContext.useObstacle("sprites/Circle.png", 0, 0);
      NxtContext.useObstacle("sprites/Circle.png", 20, 20);

    }/*
  NxtContext.useObstacle("sprites/Circle.png", 250, 25);
  NxtContext.useObstacle("sprites/Circle.png", 250, 475);
  NxtContext.useObstacle("sprites/Circle.png", 400, 250);
  NxtContext.useObstacle("sprites/Circle.png", 100, 250);
  NxtContext.useObstacle("sprites/Circle.png", 175, 350);
  NxtContext.useObstacle("sprites/Circle.png", 325, 350);
  NxtContext.useObstacle("sprites/Circle.png", 175, 150);
  NxtContext.useObstacle("sprites/Circle.png", 325, 150);*/
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {
    System.out.println(arg0.getX());
    System.out.println(arg0.getY());
    System.out.println("Click");
  }

  @Override
  public void mouseEntered(MouseEvent arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseExited(MouseEvent arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mousePressed(MouseEvent arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseReleased(MouseEvent arg0) {
    // TODO Auto-generated method stub

  }
}
//http://www.java-tips.org/java-se-tips/java.awt.geom/how-to-create-a-buffered-image-2.html