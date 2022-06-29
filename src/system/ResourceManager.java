package system;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

interface ImageLoaderCallback {
    Image onImageLoaded(ImageLoaderInformation information);
}

class ImageLoaderInformation {
    private final String url;
    private final String name;
    private Image image;
    private final ImageLoaderCallback callback;

    ImageLoaderInformation(String url, String name, ImageLoaderCallback callback) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL is not valid");
        }

        this.url = url;
        this.name = name;
        this.callback = callback;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public ImageLoaderCallback getCallback() {
        return callback;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}

public class ResourceManager {
    static class ResourceManagerBuilder {
        private final List<ImageLoaderInformation> images = new ArrayList<>();

        public ResourceManagerBuilder loadImage(String url, String name, ImageLoaderCallback callback) {
            if (url == null || url.isBlank()) {
                throw new IllegalArgumentException("URL is not valid");
            }

            images.add(new ImageLoaderInformation(url, name, callback));

            return this;
        }

        public void finish() {
            for (ImageLoaderInformation information : images) {
                try {
                    URL resourceURL = ResourceManager.class.getClassLoader().getResource(information.getUrl());

                    if (resourceURL == null) {
                        throw new IllegalStateException("Could not locate a image resource with the url: %s".formatted(information.getUrl()));
                    }

                    Image image = ImageIO.read(resourceURL);
                    information.setImage(image);

                    if (information.getCallback() != null) {
                        final Image callbackImage = information.getCallback().onImageLoaded(information);

                        if (callbackImage == null) {
                            throw new IllegalStateException("Image is null after processed by callback");
                        }

                        information.setImage(callbackImage);
                    }
                } catch (IOException ex) {
                    throw new IllegalArgumentException("Could not load image with the path: %s".formatted(information.getUrl()), ex.getCause());
                }
            }

            imageLoaderInformation = images.toArray(new ImageLoaderInformation[0]);
        }
    }

    public static ImageLoaderInformation[] imageLoaderInformation;

    public static Image ship = null;
    public static Image turbo = null;

    public static Image findImageResource(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }

        for (ImageLoaderInformation information : imageLoaderInformation) {
            if (information.getName().equals(name)) {
                return information.getImage();
            }
        }

        throw new IllegalArgumentException("Could not locate image resource with the name: %s".formatted(name));
    }

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

    public static ResourceManagerBuilder loadResource() {
        return new ResourceManagerBuilder();
    }
}
