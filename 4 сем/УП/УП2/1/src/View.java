import javax.swing.*;
import java.util.Map;

public class View extends JFrame {
    private JList list;
    private Controller controller;

    public View(Controller controller, Map<String, String> countries) {
        super("Countries");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        this.controller = controller;
        list = new JList(countries.entrySet().toArray());
        list.setCellRenderer(new ListRenderer());
        list.setFixedCellHeight(64);
        add(new JScrollPane(list));
        pack();
        setSize(450, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}