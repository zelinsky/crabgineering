import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ArrowSprite extends EntitySprite {
    private double theta;
    private boolean is_visible = false;

    ArrowSprite(Bounds bounds){
        super(SpriteImage.ARROW, bounds);
    }

    public void setVisiblity(boolean is_visible){
        this.is_visible = is_visible;
    }

    public void rotate(double dTheta){
        theta += dTheta;
    }

    @Override
    public void draw(Graphics g){
        if (is_visible){
            //get the old transform
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform oldTransf = g2d.getTransform();

            //create transform
            AffineTransform transform = new AffineTransform();
            transform.rotate(theta,
                    getBounds().getWidth()/2 + getBounds().getX(),
                    getBounds().getHeight()/2 + getBounds().getY());
            g2d.setTransform(transform);

            //draw the image
            super.draw(g2d);

            g2d.setTransform(oldTransf);
        }
    }
}
