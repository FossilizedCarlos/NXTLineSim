/* Carlos E Barboza
 * Used for obstacle creation
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class ObstacleFactory {
    public ObstacleFactory() {
    }

    public void obstacleFactory() {
        int width = 30;
        int height = 30;

        // Create a buffered image in which to draw
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
        Color lsuPurple = new Color(70, 29, 124);
        g2d.setColor(lsuPurple);
        g2d.fillOval(0, 0, width, height);

        // Graphics context no longer needed so dispose it
        g2d.dispose();

        RenderedImage rendImage = bufferedImage;
        // Write generated image to a file, and save as PNG
        File file = new File("src/sprites/Circle.png");
        try {
            ImageIO.write(rendImage, "png", file);
        } catch (IOException e) {
            System.out.println("Image Writing Error...");
        }
    }
}