import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View extends JFrame implements ViewInterface, ActionListener {
    private Controller controller;
    private JTabbedPane tabbedPane;
    private JPanel firstPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;
    private JList firstList;
    private JList secondList;
    private JList thirdList;
    private JButton button;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenu data;
    private JMenuItem byPosition;
    private JMenuItem bySurname;
    private JMenuItem bySalary;
    private JMenuItem search;
    private JMenuItem minSalary;
    private JMenuItem open;
    private JMenuItem exit;
    /*private JTextArea firstArea;
    private JTextArea secondArea;
    private JTextArea thirdArea;*/

    public View(Controller controller) {
        super("ИКР (пересдача)");
        this.controller = controller;
        setIconImage(new ImageIcon("src\\Java.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        tabbedPane = new JTabbedPane();
        firstPanel = new JPanel(new BorderLayout());
        secondPanel = new JPanel(new BorderLayout());
        thirdPanel = new JPanel(new BorderLayout());
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        /*firstArea = new JTextArea();
        firstArea.setEditable(false);
        JScrollPane scrollPane1 = new JScrollPane(firstArea);*/
        firstList = new JList();
        firstList.setCellRenderer(new ListRenderer());
        JScrollPane scrollPane1 = new JScrollPane(firstList);
        firstPanel.add(scrollPane1);

        /*secondArea = new JTextArea();
        secondArea.setEditable(false);
        JScrollPane scrollPane2 = new JScrollPane(secondArea);*/
        secondList = new JList();
        secondList.setCellRenderer(new ListRenderer());
        JScrollPane scrollPane2 = new JScrollPane(secondList);
        secondPanel.add(scrollPane2);

        /*thirdArea = new JTextArea();
        thirdArea.setEditable(false);
        JScrollPane scrollPane3 = new JScrollPane(thirdArea);*/
        thirdList = new JList();
        thirdList.setCellRenderer(new ListRenderer());
        JScrollPane scrollPane3 = new JScrollPane(thirdList);
        thirdPanel.add(scrollPane3);

        tabbedPane.addTab("First", firstPanel);
        tabbedPane.addTab("Second", secondPanel);
        tabbedPane.addTab("Third", thirdPanel);
        add(tabbedPane);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        button = new JButton("Show all");
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(Box.createHorizontalGlue());
        add(button);
        button.addActionListener(this);
        button.setEnabled(false);
        button.setMnemonic(KeyEvent.VK_A);

        menuBar = new JMenuBar();

        file = new JMenu("File");
        open = new JMenuItem("Open", new ImageIcon("src\\Open.png"));
        exit = new JMenuItem("Exit", new ImageIcon("src\\Close.png"));
        file.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        file.addSeparator();
        file.add(exit);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));

        data = new JMenu("Data");
        byPosition = new JMenuItem("By position");
        bySurname = new JMenuItem("By surname");
        bySalary = new JMenuItem("By salary");
        search = new JMenuItem("Search");
        minSalary = new JMenuItem("Minimal salary");
        byPosition.setEnabled(false);
        bySurname.setEnabled(false);
        bySalary.setEnabled(false);
        search.setEnabled(false);
        minSalary.setEnabled(false);
        data.add(byPosition);
        data.add(bySurname);
        data.add(bySalary);
        data.add(search);
        data.add(minSalary);

        menuBar.add(file);
        menuBar.add(data);
        setJMenuBar(menuBar);

        open.addActionListener(this);
        exit.addActionListener(this);
        byPosition.addActionListener(this);
        bySurname.addActionListener(this);
        bySalary.addActionListener(this);
        search.addActionListener(this);
        minSalary.addActionListener(this);

        pack();
        setSize(450, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == open) {
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML file", "xml"));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text file", "txt"));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String file = fileChooser.getDescription(fileChooser.getSelectedFile());
                controller.open(file);
            }
        } else if (event.getSource() == exit) {
            setVisible(false);
            dispose();
        } else if (event.getSource() == byPosition) {
            controller.showByPosition();
            tabbedPane.setSelectedIndex(0);
        } else if (event.getSource() == bySurname) {
            controller.showBySurname();
            tabbedPane.setSelectedIndex(1);
        } else if (event.getSource() == bySalary) {
            controller.showBySalary();
            tabbedPane.setSelectedIndex(2);
        } else if (event.getSource() == search) {
            JTextField positionField = new JTextField(20);
            positionField.setText("1");
            JTextField surnameField = new JTextField(15);
            surnameField.setText("Ivanov");
            /*JTextField salaryField = new JTextField(5);*/
            JTextField objectField = new JTextField(20);
            objectField.setText("BSU");
            JPanel inputPanel = new JPanel();
            inputPanel.add(new JLabel("Position"));
            inputPanel.add(positionField);
            inputPanel.add(Box.createHorizontalStrut(5));
            inputPanel.add(new JLabel("Surname"));
            inputPanel.add(surnameField);
            /*inputPanel.add(Box.createHorizontalStrut(15));
            inputPanel.add(new JLabel("Salary"));
            inputPanel.add(salaryField);*/
            inputPanel.add(Box.createHorizontalStrut(5));
            inputPanel.add(new JLabel("Object"));
            inputPanel.add(objectField);
            if (JOptionPane.showConfirmDialog(this, inputPanel,
                    "Enter all parameters", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                controller.searchItem(positionField.getText(), surnameField.getText(),
                        /*salaryField.getText(),*/ objectField.getText());
            }
        } else if (event.getSource() == minSalary) {
            controller.showMinSalary();
        } else if (event.getSource() == button) {
            controller.showAll();
        }
    }

    @Override
    public void showByPosition(List<Security> sorted) {
        firstList.setListData(sorted.toArray());
    }

    @Override
    public void showBySurname(List<Security> sorted) {
        secondList.setListData(sorted.toArray());
    }

    @Override
    public void showBySalary(List<Security> sorted) {
        thirdList.setListData(sorted.toArray());
    }

    @Override
    public void showSearchResult(String security, String salary) {
        JOptionPane.showMessageDialog(this, salary, security, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showMinSalary(Map<Position, Integer> salaryMap) {
        JPanel salaryPanel = new JPanel(new BorderLayout());
        salaryPanel.add(new JScrollPane(new JList(salaryMap.entrySet().toArray())));
        JOptionPane.showMessageDialog(this, salaryPanel, "Minimal salary", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void enableData() {
        byPosition.setEnabled(true);
        bySurname.setEnabled(true);
        bySalary.setEnabled(true);
        search.setEnabled(true);
        minSalary.setEnabled(true);
        button.setEnabled(true);
    }

    @Override
    public void showError(Exception e) {
        JOptionPane.showMessageDialog(this, e, e.getMessage(), JOptionPane.ERROR_MESSAGE);
    }

    class ListRenderer extends DefaultListCellRenderer {
        Font font;
        Map<Integer, ImageIcon> iconMap;

        public ListRenderer() {
            font = new Font("Consolas", Font.PLAIN, 14);
            iconMap = new HashMap<>();
            iconMap.put(0, new ImageIcon("src\\0 category.png"));
            iconMap.put(1, new ImageIcon("src\\1 category.png"));
            iconMap.put(2, new ImageIcon("src\\2 category.png"));
            iconMap.put(3, new ImageIcon("src\\3 category.png"));
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            if (iconMap.containsKey(((Worker) value).getPosition().getDescription())) {
                label.setIcon(iconMap.get(((Worker) value).getPosition().getDescription()));
            } else {
                label.setIcon(iconMap.get(0));
            }
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }
}