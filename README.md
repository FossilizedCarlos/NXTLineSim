NXT Simulations
===============

NXTLineSim
----------

This program simulates a line following robot that also avoids obstacles. This is the simple
implementation, and involves the robot using color sensors to remain inside of a circle road while
avoiding obstacles.

NXTRandomSim
------------

This program simulates a robot that avoids obstacles after first encountering them. This program
will generate the obstacle images, and store them in the "src/images" folder. This code will also
randomly place obstacles in the 500px by 500px grid, and keep them at least 50px away from each other.
Once all the obstacles have been encountered, and locations tagged; the genetic algorithm will find
the shortest path between all the obstacles without any specific starting or ending points.

###Things left to add:

* LinkedList sorting
* Genetic algorithm
* Revisiting of all obstacles
* Displaying a grid with "generated" obstacles
* Document code