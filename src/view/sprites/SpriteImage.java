package view.sprites;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * view.sprites.SpriteImage - an enum to handle inclusion of Sprites in game
 */
public enum SpriteImage {
    CLAM("clam.png"),
    CRAB("crab.png"),
    SHRIMP("shrimp.png"),
    TURTLE("turtle.png"),
    TRASH("trash.png"),
    ARROW("arrow.png");

    //should be the same for all Sprites
    final static String IMAGE_DIR = "src/resources/images/";

    final BufferedImage image;

    /**
     * Constructor to create a view.sprites.SpriteImage
     * @param image_name the type of image to make
     */
    SpriteImage(String image_name){
        //get image should be in the folder IMAGE_DIR
        //and have the lowercase version of the name of the constant
        //with the IMAGE_TYPE file extension
        String imagePath = IMAGE_DIR + image_name;

        //load in the image
        image = createImage(imagePath);
    }

    /**
     * Read image from file and return
     * @param imagePath relative image path from PWD
     * @return BufferedImage of either the requested image, or null
     */
    private BufferedImage createImage(String imagePath) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(imagePath));
            return bufferedImage;
        } catch (IOException e) {
            System.out.println(imagePath);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get BufferedImage
     * @return The image
     */
    public BufferedImage getImage() {
        return image;
    }
}
