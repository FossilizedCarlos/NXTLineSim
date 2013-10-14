NXT Simulations
===============

This program simulates what a [LEGO MINDSTORMS NXT robot](http://mindstorms.lego.com/en-us/products/default.aspx) would do when following a line or encounter
objects, while utilizing the touch sensor and light sensor.

NXTLineSimOrig
----------

![ScreenShot](/src/sprites/NXTLineSimOrig.png)

This is the original implementation of the program. The robot follows the circular path line, and
reverses direction upon encountering the obstacle on the path.

NXTLineSim
----------

![ScreenShot](/src/sprites/NXTLineSim.png)

This program simulates a line following robot that also avoids obstacles. This is simple alteration to
the original implementation, and involves the robot using color sensors to remain inside of a circular road while
avoiding obstacles.

NXTRandomSim
------------

![ScreenShot](/src/sprites/NXTRandomSim.png)

This updated iteration simulates a robot that avoids obstacles after first encountering them. The program
will generate the obstacle images utilizing the ObstacleFactory class, and store them in the "src/sprites"
folder. This code will randomly place these obstacles in the 500px by 500px grid, and keep them at least
50px away from each other. The obstacles receive a yellow field once the location is discovered. Once all
the obstacles have been encountered, and locations tagged; the goal is for a genetic algorithm implementation
to find the shortest path between all the obstacles without any specific starting or ending points.

###Things left to add:

* A better way of centering the field on discovered obstacles.
* LinkedList sorting
* Genetic algorithm
* Revisiting of all obstacles
* Displaying a grid with "generated" obstacles
* Documenting the code