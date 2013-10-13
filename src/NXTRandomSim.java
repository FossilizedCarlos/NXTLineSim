// NXTRandomSim.java
// Two light sensors, polling

import ch.aplu.nxtsim.*;
import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.util.*;

public class NXTRandomSim {
    static Random generator = new Random(System.currentTimeMillis());
    static Random generatorX = new Random(3 * System.currentTimeMillis());
    static Random generatorY = new Random(7 * System.currentTimeMillis());
    static GGBackground bg;
    NxtRobot robot = new NxtRobot();
    Gear gear = new Gear();
    LightSensor ls1 = new LightSensor(SensorPort.S1);
    LightSensor ls2 = new LightSensor(SensorPort.S2);
    TouchSensor ts1 = new TouchSensor(SensorPort.S3);
    LinkedList<Point> obstacleList = new LinkedList<Point>();
    Point currentPosition = new Point();
    Point obstaclePosition = new Point();
    Color lsuYellow = new Color(253, 208, 35);
    static int randomXCoordinate;
    static int randomYCoordinate;
    static LinkedList<Point> placementList = new LinkedList<Point>();
    static Point placementPosition = new Point();
    int randomNumber = generator.nextInt(500);
    int obstacleCount = 0;
    static ObstacleFactory worker = new ObstacleFactory();

    public NXTRandomSim() {
        int leftX, centerX, rightX;
        int leftY, centerY, rightY;
        robot.addPart(gear);
        robot.addPart(ls1);
        robot.addPart(ls2);
        robot.addPart(ts1);
        gear.forward();
        gear.setSpeed(1000);
        while (true) {
            //This code will refine the collision behaviours
            /*
          centerX = gear.getX();
          centerY = gear.getY();
          gear.backward(50);
          gear.leftArc(.05);
          gear.forward(10);
          gear.rightArc(.05);
          while(!ts1.isPressed())
              gear.forward();
          leftX = gear.getX();
          leftY = gear.getY();
          gear.backward(50);
          gear.rightArc(.05);
          gear.forward(20);
          gear.leftArc(.05);
          while(!ts1.isPressed())
              gear.forward();
          rightX = gear.getX();
          rightY = gear.getY();*/

            currentPosition.setLocation(ts1.getX(), ts1.getY());
            if (ts1.isPressed()) {
                if (!obstacleList.contains(currentPosition)) {
                    obstacleList.add(new Point((int) (currentPosition.getX() + 15 * Math.cos(0.0174532925 * gear.getDirection())), (int) (currentPosition.getY() + 15 * Math.sin(0.0174532925 * gear.getDirection()))));
                    System.out.println(gear.getDirection() + ", " + new Point((int) (15 * Math.cos(0.0174532925 * gear.getDirection())), (int) (15 * Math.sin(0.0174532925 * gear.getDirection()))));
                    bg.setPaintColor(lsuYellow);
                    bg.fillArc(obstacleList.getLast(), 30, 0, 360);
                    System.out.println("Obstacle Detected");
                    gear.backward(250);
                    if (generator.nextInt(500) < 250)
                        gear.left(200);
                    else if (generator.nextInt(500) > 250)
                        gear.right(200);
                    obstacleCount++;
                    System.out.println(obstacleCount);
                }
                checkLineColor();

            }
            //Synchronizes the linked list, and checks every members proximity everytime
            synchronized (obstacleList) {
                ListIterator<Point> obstacleListChecker = obstacleList.listIterator();
                while (obstacleListChecker.hasNext()) {
                    obstaclePosition = obstacleListChecker.next();
                    if (currentPosition.distance(obstaclePosition) < 45) {
                        gear.backward(500);
                        System.out.println("Obstacle Avoided");
                        if (generator.nextInt(500) < 250)
                            gear.left(250);
                        else
                            gear.right(250);
                    }
                }
            }
            //Brings robot back to (250, 250) in the event that the robot exits the constraints box
            if (gear.getX() < 5 || gear.getX() > 495 || gear.getY() < 5 || gear.getY() > 495) {
                robot.reset();
            }
            checkLineColor();
        }
    }

    //Function Checks the edges of the boxed area to ensure the robot remains within the constraints of the box
    public void checkLineColor() {
        int v = 500;
        double r = 0.;
        int v1 = ls1.getValue();
        int v2 = ls2.getValue();
        if (v1 < v && v2 < v) {
            gear.forward();
            if (generator.nextInt(10) + 1 < 2) {
                gear.left(25);
                gear.forward(10);
            } else if (generator.nextInt(10) + 1 > 8) {
                gear.right(15);
                gear.forward(10);
            }
        }
        if (v1 < v && v2 > v)
            gear.rightArc(r);
        if (v1 > v && v2 < v)
            gear.leftArc(r);
        if (v1 > v && v2 > v) {
            gear.right(15);
        }
    }

    @SuppressWarnings("unused")
    private static void _init(GameGrid gg) {
        bg = gg.getBg();
        bg.setPaintColor(Color.black);
        bg.fillRectangle(new Point(9, 9), new Point(491, 491));
    }

    public static void main(String[] args) {
        new NXTRandomSim();
    }

    //------------------ Environment --------------------------
    static {
        worker.obstacleFactory();
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            System.out.println("Thread Error...");
        }

        NxtContext.setStartPosition(250, 250);
        NxtContext.setStartDirection(90);
        for (int i = 0; i < 9; i++) {
            randomXCoordinate = generatorX.nextInt(475) + 10;
            randomYCoordinate = generatorY.nextInt(475) + 10;
            Point currentPlacement = new Point(randomXCoordinate, randomYCoordinate);
            boolean makeIt = true;
            if (randomXCoordinate > 25 && randomXCoordinate < 475 && randomYCoordinate > 25 && randomYCoordinate < 475) {
                if (placementList.isEmpty()) {
                    placementList.add(new Point(randomXCoordinate, randomYCoordinate));
                    NxtContext.useObstacle("sprites/Circle.png", randomXCoordinate, randomYCoordinate);
                    System.out.println("(" + randomXCoordinate + ", " + randomYCoordinate + ")");
                }
                synchronized (placementList) {
                    ListIterator<Point> placementListChecker = placementList.listIterator();
                    while (placementListChecker.hasNext()) {
                        placementPosition = placementListChecker.next();
                        if (currentPlacement.distance(placementPosition) < 130) {
                            System.out.println(currentPlacement.distance(placementPosition));
                            makeIt = false;
                        }
                    }
                    if (makeIt) {
                        NxtContext.useObstacle("sprites/Circle.png", randomXCoordinate, randomYCoordinate);
                        placementList.add(new Point(randomXCoordinate, randomYCoordinate));
                        System.out.println("(" + randomXCoordinate + ", " + randomYCoordinate + ")");
                    } else
                        i--;
                }
            } else
                i--;
        }
    }
}