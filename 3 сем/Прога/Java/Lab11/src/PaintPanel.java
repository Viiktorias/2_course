import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PaintPanel extends JPanel {
    private BufferedImage bufferedImage;

    public PaintPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void setPreferredSize(Dimension dimension) {
        super.setPreferredSize(dimension);
        bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0, 0, dimension.width, dimension.height);
        graphics.dispose();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(bufferedImage, 0, 0, null);
    }

    public BufferedImage getBuffer() {
        return bufferedImage;
    }

    public void loadImage(BufferedImage buf) {
        bufferedImage.createGraphics().setColor(Color.WHITE);
        bufferedImage.createGraphics().fillRect(0, 0, getWidth(), getHeight());
        bufferedImage.createGraphics().drawImage(buf, 0, 0, null);
        repaint();
    }
}
