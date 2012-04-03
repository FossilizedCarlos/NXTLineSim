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
	static LinkedList<Point> placementList = new LinkedList<Point>();
	static Point placementPosition = new Point();
	int randomNumber = generator.nextInt(500);
	static ObstacleFactory worker = new ObstacleFactory();

	public NXTRandomSim() {
		robot.addPart(gear);
		robot.addPart(ls1);
		robot.addPart(ls2);
		robot.addPart(ts1);
		gear.forward();
		gear.setSpeed(1000);
		while (true) {
			currentPosition.setLocation(gear.getX(), gear.getY());
			if (ts1.isPressed()) {
				if (!obstacleList.contains(currentPosition)) {
					if (gear.getDirection() >= 0 && gear.getDirection() < 90)
						obstacleList.add(new Point((int) (currentPosition.getX() + 10 * Math.cos(gear.getDirection())), (int) (currentPosition.getY() + 10 * Math.sin(gear.getDirection()))));
					if (gear.getDirection() >= 90 && gear.getDirection() < 180)
						obstacleList.add(new Point((int) (currentPosition.getX() + 10 * Math.cos(gear.getDirection())), (int) (currentPosition.getY() + 10 * Math.sin(gear.getDirection()))));
					if (gear.getDirection() >= 180 && gear.getDirection() < 270)
						obstacleList.add(new Point((int) (currentPosition.getX() + 10 * Math.cos(gear.getDirection())), (int) (currentPosition.getY() + 10 * Math.sin(gear.getDirection()))));
					else
						obstacleList.add(new Point((int) (currentPosition.getX() + 10 * Math.cos(gear.getDirection())), (int) (currentPosition.getY() + 10 * Math.sin(gear.getDirection()))));
					System.out.println("Obstacle Detected");
					gear.backward(300);
					if (generator.nextInt(500) < 125)
						gear.left(200);
					else if (generator.nextInt(500) > 375)
						gear.right(200);
					gear.forward();
				}
				checkLineColor();
			}
			synchronized (obstacleList) {
				ListIterator<Point> obstacleListChecker = obstacleList.listIterator();
				while (obstacleListChecker.hasNext()) {
					obstaclePosition = obstacleListChecker.next();
					if (currentPosition.distance(obstaclePosition) < 40) {
						gear.backward(300);
						System.out.println("Obstacle Avoided");
						if (generator.nextInt(500) < 50)
							gear.left(50);
						else if (generator.nextInt(500) < 100)
							gear.left(100);
						else if (generator.nextInt(500) < 150)
							gear.left(150);
						else if (generator.nextInt(500) < 400)
							gear.right(150);
						else if (generator.nextInt(500) < 450)
							gear.right(100);
						else if (generator.nextInt(500) < 500)
							gear.right(50);
						gear.forward();
					}
				}
			}
			if (gear.getX() < 0 || gear.getX() > 500 || gear.getY() < 0 || gear.getY() > 500) {
				robot.reset();
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
			gear.right(15);
		}
	}

	@SuppressWarnings("unused")
	private static void _init(GameGrid gg) {
		GGBackground bg = gg.getBg();
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
						if (currentPlacement.distance(placementPosition) < 50) {
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