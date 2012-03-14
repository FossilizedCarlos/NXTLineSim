import ch.aplu.nxtsim.*;
import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.util.*; 

public class NXTLineSim
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

	public NXTLineSim()
	{
		robot.addPart(gear);
		robot.addPart(ls1);
		robot.addPart(ls2);
		robot.addPart(ts1);
		gear.forward();
		gear.setSpeed(110);

		boolean inside = true;
		while (true)
		{
			currentPosition.setLocation(gear.getX(), gear.getY());
			if (ts1.isPressed())
			{
				while (gear.getX() > 175 && gear.getX() < 325 && gear.getY() > 125 && gear.getY() < 375)
				{
					while (inside) {
						if (inside && generator.nextInt(10) > 5)
						{
							gear.left(200);
							gear.forward();
							inside = false;
							System.out.println(inside);
						}
						else if (inside)
						{
							gear.right(200);
							gear.forward();
							inside = false;
							System.out.println(inside);
						}
					}
				}
				if (!obstacleList.contains(currentPosition))
				{
					obstacleList.add(new Point(currentPosition));
					gear.right(400);
					System.out.println("("+obstacleList.getLast().getX()+", "+ obstacleList.getLast().getY()+")");
				}
				checkLineColor();
			}
			synchronized(obstacleList)
			{
				ListIterator<Point> obstacleListChecker = obstacleList.listIterator();
				if (obstacleListChecker.hasNext())
				{
					obstaclePosition = obstacleListChecker.next();
					if (currentPosition.distance(obstaclePosition) < 160){
						gear.left(2);					
					System.out.println(currentPosition.distance(obstaclePosition));}
				}
			}

			if (gear.getX() < 0 || gear.getX() > 500 || gear.getY() < 0 || gear.getY() >500)
			{
				gear.backward(400);
				gear.right(400);
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
	private static void _init(final GameGrid gg)
	{
		gg.getBg().setPaintColor(Color.black);
		gg.getBg().fillArc(new Point(250, 250), 240, 0, 360);
		gg.getBg().setPaintColor(Color.white);
		gg.getBg().fillArc(new Point(250, 250), 180, 0, 360);
		gg.getBg().setPaintColor(Color.black);
		gg.getBg().fillArc(new Point(250, 195), 60, 0, 360);
		gg.getBg().setPaintColor(Color.white);
		gg.getBg().fillArc(new Point(250, 195), 40, 0, 360);
		gg.getBg().setPaintColor(Color.black);
		gg.getBg().fillArc(new Point(250, 300), 60, 0, 360);
		gg.getBg().setPaintColor(Color.white);
		gg.getBg().fillArc(new Point(250, 300), 40, 0, 360);
	}

	public static void main(String[] args)
	{
		new NXTLineSim();
	}

	// ------------------ Environment --------------------------
	static
	{
		NxtContext.setStartPosition(250, 400);
		NxtContext.setStartDirection(105);
		NxtContext.useObstacle("sprites/Obstacle.png", 250, 25);
		NxtContext.useObstacle("sprites/Obstacle.png", 25, 250);
		NxtContext.useObstacle("sprites/Obstacle.png", 200, 200);
	}
}