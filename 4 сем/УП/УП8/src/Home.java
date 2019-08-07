import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Home extends Entity {
    private static Image[] images;

    static {
        try {
            images = new Image[]{ImageIO.read(new File("src\\images\\home.png")),
                    ImageIO.read(new File("src\\images\\homeCr.png"))};
        } catch (IOException e) {
            System.err.println("Can't load home image");
        }
    }

    private boolean live;

    public Home(int x, int y, TankClient tankClient) {
        super(x, y, tankClient);
        live = true;
    }

    @Override
    public void draw(Graphics g) {
        if (live)
            g.drawImage(images[0], x, y, null);
        else
            g.drawImage(images[1], x, y, null);

    }

    public void hit() {
        tankClient.lose();
        tankClient.addExplosion(new Explosion(x - 15, y - 15, true, tankClient, 2));
        live = false;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }
}
