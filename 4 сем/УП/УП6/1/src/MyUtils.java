import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyUtils {
    public static boolean compareList(List ls1, List ls2) {
        return ls1.toString().contentEquals(ls2.toString());
    }

    public static int getNewHeight(int desiredWidth, int w, int h) {
        double ratio = desiredWidth / (double) w;
        int newHeight = (int) (h * ratio);
        return newHeight;
    }

    public static ImageProducer getImage(Image img, int x, int y, int w, int h) {
        return new FilteredImageSource(img.getSource(), new CropImageFilter(x, y, w, h));
    }

    public static BufferedImage loadImage(File sourse) throws IOException {
        return ImageIO.read(sourse);
    }

    public static BufferedImage createImage(int w, int h) {
        BufferedImage bimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        bimg.getGraphics().setColor(new Color(0xA8A8A8));
        return bimg;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int width,
                                            int height, int type) {
        var resizedImage = new BufferedImage(width, height, type);
        var g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public static boolean checkSolution(List<MyButton> buttons, List<Point> solution) {
        var current = new ArrayList<Point>();
        for (JComponent button : buttons) {
            current.add((Point) button.getClientProperty("position"));
        }
        if (MyUtils.compareList(solution, current)) {
            return true;
        }
        return false;
    }
}
