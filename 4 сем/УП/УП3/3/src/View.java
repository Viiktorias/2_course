import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    public View(double[] values, String[] names, String title) {
        super(title);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        ChartPanel pie = new ChartPanel(values, names, title);
        add(pie, BorderLayout.CENTER);
        pack();
        pie.setDoubleBuffered(true);
        setSize(new Dimension(600, 600));
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}