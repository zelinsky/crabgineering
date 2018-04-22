import java.awt.*;

public class EntitySprite implements Sprite, BoundsListener {
    private final SpriteImage spriteImage;
    private Rectangle bounds;

    EntitySprite(SpriteImage spriteImage, Bounds bounds) {
        this.spriteImage = spriteImage;
        setBounds(bounds);
    }

    void setBounds(Bounds bounds){
        this.bounds = new Rectangle(bounds);
        bounds.addListener(this);
    }

    @Override
    public void handleTranslate(int dx, int dy) {
        bounds.translate(dx, dy);
    }

    @Override
    public void handleSetLocation(int x, int y) {
        bounds.setLocation(x, y);
    }

    public void draw(Graphics g){
        g.drawImage(spriteImage.getImage(),
                (int) bounds.getX(),
                (int) bounds.getY(),
                (int) bounds.getWidth(),
                (int) bounds.getHeight(),
                //a BufferedImage won't change while
                //the image is being loaded, so null
                //will work for our observer
                null);
    }
}
