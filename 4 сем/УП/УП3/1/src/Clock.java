import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Clock extends JFrame implements ActionListener {
    private PaintPanel clockFace;
    private BufferedImage clock;
    private Timer timer;
    private Time time;
    private Clip tick;
    //center 172, 300;

    public Clock() throws HeadlessException {
        super("Clock");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setIconImage(new ImageIcon("src\\gear.png").getImage());
            clockFace = new PaintPanel(360, 480);
            clockFace.setDoubleBuffered(true);
            clock = ImageIO.read(new File("src\\clock.png"));
            //clock = new BufferedImage(360, 480, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = (Graphics2D) clock.getGraphics();
            graphics.setColor(Color.BLACK);
            graphics.setStroke(new BasicStroke(6));
            graphics.drawOval(29, 157, 286, 286);
            timer = new Timer(1000, this);
            tick = AudioSystem.getClip();
            tick.open(AudioSystem.getAudioInputStream(new File("src\\tick.wav")));
            tick.loop(Integer.MAX_VALUE);
            time = new Time();
            clockFace.loadImage(clock);
            draw();
            add(clockFace);
            pack();
            timer.start();
            tick.start();
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException | IOException |
                UnsupportedAudioFileException | LineUnavailableException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            time.plus();
            draw();
        }
    }

    private void draw() {
        clockFace.loadImage(clock);
        Graphics2D graphics = (Graphics2D) clockFace.getBuffer().getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.drawLine(172, 300,
                172 + (int) Math.round(80 * Math.sin(time.hours * Math.PI / 6)),
                300 - (int) Math.round(80 * Math.cos(time.hours * Math.PI / 6)));
        graphics.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.drawLine(172, 300,
                172 + (int) Math.round(100 * Math.sin(time.minutes * Math.PI / 30)),
                300 - (int) Math.round(100 * Math.cos(time.minutes * Math.PI / 30)));
        graphics.setColor(Color.RED);
        graphics.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.drawLine(172, 300,
                172 + (int) Math.round(120 * Math.sin(time.seconds * Math.PI / 30)),
                300 - (int) Math.round(120 * Math.cos(time.seconds * Math.PI / 30)));
        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(10));
        graphics.drawOval(167, 295, 10, 10);
        clockFace.repaint();
    }
}
