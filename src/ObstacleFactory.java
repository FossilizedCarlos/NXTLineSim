/**
 * Carlos E Barboza
 * Used for obstacle creation
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import javax.swing.*;
import java.io.IOException;

public class ObstacleFactory {

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
  //g2d.setColor(Color.yellow);
  //g2d.fillOval(0, 0, width, height);

  // Graphics context no longer needed so dispose it
  //g2d.dispose();


  RenderedImage rendImage = bufferedImage;
  //return bufferedImage;
  // Write generated image to a file
  // Save as PNG
  File file = new File("src/sprites/Circle.jpg");
  //ImageIO.write(rendImage, "jpg", file);

}
