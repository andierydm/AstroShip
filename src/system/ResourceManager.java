package system;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ResourceManager {
    public static Image ship = null;
    public static Image turbo = null;

    public static boolean isImageDimensionSet(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("Image can't be null");
        }

        return image.getWidth(null) != -1 && image.getHeight(null) != -1;
    }

    public static double getImageRatio(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("Image can't be null");
        }

        if (!isImageDimensionSet(image)) {
            throw new IllegalStateException("Either image width or height isn't set");
        }

        return ((double) image.getWidth(null)) / ((double) image.getHeight(null));
    }

    public static Image resizeImage(Image image, int newWidth) {
        if (image == null) {
            throw new IllegalArgumentException("Image can't be null");
        } else if (newWidth <= 0) {
            throw new IllegalArgumentException("Invalid new width");
        }

        if (!isImageDimensionSet(image)) {
            throw new IllegalStateException("Either image width or height isn't set");
        }

        int newHeight = (int) (newWidth / getImageRatio(image));

        return image.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("image can't be null");
        } else if (!isImageDimensionSet(image)) {
            throw new IllegalArgumentException("Either image width or height isn't set");
        }

        final BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);

        return bufferedImage;
    }

    public static boolean loadImages() {
        try {
            URL shipURL = ResourceManager.class.getClassLoader().getResource("ship.png");
            URL turboURL = ResourceManager.class.getClassLoader().getResource("fire02.png");

            if (shipURL != null && turboURL != null) {
                Image image = ImageIO.read(shipURL);
                Image imageT = ImageIO.read(turboURL);
                ship = ResourceManager.resizeImage(image, 76);
                turbo = ResourceManager.resizeImage(imageT, 76);
            }

            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
