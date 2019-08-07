import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

public class View extends JFrame {
    private JComboBox<String> directionBox;
    private boolean direction;
    private int centerX;
    private int centerY;
    private JSlider speedSlider;
    private double speed;
    private double angle;
    private JPanel field;
    private Timer timer;
    private PaintPanel element;

    public View() {
        super("Вращение");
        try {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            }
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            field = new JPanel(true);
            field.setBackground(Color.GRAY);
            element = new PaintPanel(40, 40);
            element.loadImage(ImageIO.read(new File("src\\ball.png")));
            field.add(element);
            field.setLayout(null);
            add(field, BorderLayout.CENTER);
            JPanel controls = new JPanel();
            controls.setLayout(new BoxLayout(controls, BoxLayout.LINE_AXIS));
            directionBox = new JComboBox<>(new String[]{"По часовой", "Против часовой"});
            directionBox.setSelectedIndex(0);
            directionBox.addActionListener(e -> direction = (directionBox.getSelectedIndex() == 0));
            direction = true;
            speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
            speedSlider.addChangeListener(e -> speed = speedSlider.getValue());
            speed = 50;
            angle = 0;
            controls.add(directionBox);
            controls.add(speedSlider);
            add(controls, BorderLayout.SOUTH);
            pack();
            timer = new Timer(10, e -> {
                    angle += (direction ? 1 : -1) * speed / (50 * Math.min(centerX, centerY));
                    move();}
            );
            setSize(new Dimension(620, 650));
            setMinimumSize(new Dimension(220, 250));
            setLocationRelativeTo(null);
            field.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    centerY = field.getHeight() / 2;
                    centerX = field.getWidth() / 2;
                    move();
                }
            });
            centerY = field.getHeight() / 2;
            centerX = field.getWidth() / 2;
            move();
            timer.start();
            setVisible(true);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    private void move() {
        element.setBounds(centerX - 20 + (int) Math.round(Math.min(centerX - 20, centerY - 20) * Math.sin(angle)),
                centerY - 20 - (int)  Math.round(Math.min(centerX - 20, centerY - 20) * Math.cos(angle)), 40, 40);
    }
}
