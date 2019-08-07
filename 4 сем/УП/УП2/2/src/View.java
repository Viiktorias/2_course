import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class View extends JFrame implements ActionListener {
    private JTable table;
    private JButton add;
    private JButton delete;
    private JButton saveAll;
    private Controller controller;

    public View(Controller controller, DefaultTableModel model, Map<String, Icon> flags) {
        super("Tours");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        this.controller = controller;
        table = new JTable(model);
        table.setDefaultRenderer(Icon.class, new IconTableRenderer(flags));
        table.setDefaultRenderer(String.class, new StringTableRenderer());
        table.setDefaultRenderer(Integer.class, new IntegerTableRenderer());
        table.getTableHeader().setDefaultRenderer(new HeaderRenderer());
        table.setRowHeight(64);
        table.getColumnModel().getColumn(0).setMinWidth(128);
        table.getColumnModel().getColumn(0).setMaxWidth(128);
        table.getColumnModel().getColumn(2).setMaxWidth(256);
        table.getColumnModel().getColumn(3).setMaxWidth(64);
        JTextField textField = new JTextField();
        textField.setFont(new Font("Verdana", Font.PLAIN, 14));
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        DefaultCellEditor defaultCellEditor = new DefaultCellEditor(textField);
        textField = new JTextField();
        textField.setFont(new Font("Verdana", Font.PLAIN, 14));
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        IntegerCellEditor integerCellEditor = new IntegerCellEditor(textField);
        table.setDefaultEditor(String.class, defaultCellEditor);
        table.setDefaultEditor(Icon.class, defaultCellEditor);
        table.setDefaultEditor(Integer.class, integerCellEditor);
        //((DefaultCellEditor) table.getCellEditor(0, 2)).getComponent().setFont(new Font("Verdana", Font.PLAIN, 14));
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        add(new JScrollPane(table));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        add = new JButton();
        add.setIcon(new ImageIcon("src\\add-row.png"));
        panel.add(Box.createVerticalGlue());
        add.addActionListener(this);
        panel.add(add);
        delete = new JButton();
        delete.setIcon(new ImageIcon("src\\delete-row.png"));
        panel.add(Box.createVerticalGlue());
        delete.addActionListener(this);
        panel.add(delete);
        saveAll = new JButton();
        saveAll.setIcon(new ImageIcon("src\\save-all.png"));
        panel.add(Box.createVerticalGlue());
        saveAll.addActionListener(this);
        panel.add(saveAll);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(Box.createHorizontalGlue());
        add(panel);
        pack();
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateUI() {
        SwingUtilities.invokeLater(() ->
                table.updateUI()
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == add) {
            controller.add();
        } else if (source == delete) {
            int[] positions = table.getSelectedRows();
            controller.delete(positions);
        } else if (source == saveAll) {
            controller.saveAll();
        }
    }
}