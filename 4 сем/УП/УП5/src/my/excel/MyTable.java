package my.excel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MyTable extends JFrame implements ActionListener {
    private MyModel model;
    private MyController controller;
    private JTable table;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem open;
    private JMenuItem imp;
    private JMenuItem save;
    private JMenuItem saveAs;
    private String filename;

    public MyTable(MyModel model, MyController controller) {
        super("Excel Date Lite");
        this.model = model;
        this.controller = controller;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        setIconImage(new ImageIcon("src\\excel.png").getImage());

        ListModel<String> listModel = new AbstractListModel<>() {
            String[] headers;

            {
                headers = new String[128];
                for (int i = 0; i < 128; i++) {
                    headers[i] = Integer.toString(i + 1);
                }
            }

            public int getSize() {
                return headers.length;
            }

            public String getElementAt(int index) {
                return headers[index];
            }
        };

        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setDefaultRenderer(new HeaderRenderer(table));
        table.setDefaultRenderer(String.class, new MyRenderer());
        JTextField textField = new JTextField();
        textField.setFont(new Font("Verdana", Font.PLAIN, 16));
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        MyCellEditor cellEditor = new MyCellEditor(textField, controller, this, table);
        table.setDefaultEditor(String.class, cellEditor);
        table.setRowHeight(26);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);

        JList<String> rowHeader = new JList<>(listModel);
        rowHeader.setFixedCellWidth(50);

        rowHeader.setFixedCellHeight(table.getRowHeight());
        rowHeader.setCellRenderer(new RowHeaderRenderer(table));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setRowHeaderView(rowHeader);
        getContentPane().add(scroll, BorderLayout.CENTER);

        menuBar = new JMenuBar();

        file = new JMenu("Файл");
        open = new JMenuItem("Открыть", new ImageIcon("src\\open.png"));
        file.add(open);
        imp = new JMenuItem("Импортировать", new ImageIcon("src\\import.png"));
        file.add(imp);
        save = new JMenuItem("Сохранить", new ImageIcon("src\\save.png"));
        file.add(save);
        saveAs = new JMenuItem("Сохранить как", new ImageIcon("src\\saveAs.png"));
        file.add(saveAs);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        imp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));

        menuBar.add(file);
        setJMenuBar(menuBar);

        open.addActionListener(this);
        imp.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        if (e.getSource() == open) {
            if (open())
                controller.open(filename);
        } else if (e.getSource() == imp) {
            if (open())
                controller.insert(filename);
        } else if (e.getSource() == save) {
            if (filename != null) {
                controller.save(filename);
            } else {
                saveAs();
            }
        } else if (e.getSource() == saveAs) {
            saveAs();
        }
    }

    private void saveAs() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("csv table file", "csv"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            filename = fileChooser.getDescription(fileChooser.getSelectedFile());
            controller.save(filename);
        }
    }

    private boolean open() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("csv table file", "csv"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            filename = fileChooser.getDescription(fileChooser.getSelectedFile());
            return true;
        }
        return false;
    }
}

class RowHeaderRenderer extends DefaultListCellRenderer {

    private JLabel label;
    private JTableHeader header;

    RowHeaderRenderer(JTable table) {
        header = table.getTableHeader();
    }

    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value, int index, boolean isSelected, boolean cellHasFocus) {
        label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setText((value == null) ? "" : value.toString());
        label.setOpaque(true);
        label.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        label.setHorizontalAlignment(CENTER);
        label.setForeground(header.getForeground());
        label.setBackground(header.getBackground());
        label.setFont(header.getFont());
        return label;
    }
}

class HeaderRenderer extends DefaultTableCellRenderer {

    private JLabel label;
    private JTableHeader header;

    HeaderRenderer(JTable table) {
        header = table.getTableHeader();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setText((value == null) ? "" : value.toString());
        label.setOpaque(true);
        label.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        label.setHorizontalAlignment(CENTER);
        label.setForeground(header.getForeground());
        label.setBackground(header.getBackground());
        label.setFont(header.getFont());
        return label;
    }
}