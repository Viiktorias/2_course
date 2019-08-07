import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class View extends JFrame implements ViewInterface, ActionListener {
    private Controller controller;
    private JTabbedPane tabbedPane;
    private JPanel firstPanel;
    private JPanel secondPanel;
    private JList firstList;
    private JList secondList;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenu data;
    private JMenuItem add;
    private JMenuItem open;
    private JMenuItem saveAs;
    private JMenuItem exit;

    public View(Controller controller) {
        super("Lab 13&14");
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
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        firstList = new JList();
        JScrollPane scrollPane1 = new JScrollPane(firstList);
        firstPanel.add(scrollPane1);

        secondList = new JList();
        JScrollPane scrollPane2 = new JScrollPane(secondList);
        secondPanel.add(scrollPane2);


        tabbedPane.addTab("Results", firstPanel);
        tabbedPane.addTab("Subjects", secondPanel);
        add(tabbedPane);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        menuBar = new JMenuBar();

        file = new JMenu("File");
        open = new JMenuItem("Open", new ImageIcon("src\\Open.png"));
        saveAs = new JMenuItem("Save as", new ImageIcon("src\\Save as.png"));
        exit = new JMenuItem("Exit", new ImageIcon("src\\Close.png"));
        file.add(open);
        file.add(saveAs);
        saveAs.setEnabled(false);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
        file.addSeparator();
        file.add(exit);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));

        data = new JMenu("Data");
        add = new JMenuItem("Add");
        data.add(add);

        menuBar.add(file);
        menuBar.add(data);
        setJMenuBar(menuBar);

        open.addActionListener(this);
        exit.addActionListener(this);
        saveAs.addActionListener(this);
        add.addActionListener(this);

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
        } else if (event.getSource() == saveAs) {
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML file", "xml"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String file = fileChooser.getDescription(fileChooser.getSelectedFile());
                controller.saveAs(file);
            }
        } else if (event.getSource() == add) {
            JTextField numberField = new JTextField(10);
            numberField.setText("1723225");
            JTextField nameField = new JTextField(15);
            nameField.setText("Ulyanitsky");
            JTextField subjectField = new JTextField(35);
            subjectField.setText("Programming");
            JTextField markField = new JTextField(2);
            markField.setText("8");
            JPanel inputPanel = new JPanel();
            inputPanel.add(new JLabel("Number"));
            inputPanel.add(numberField);
            inputPanel.add(Box.createHorizontalStrut(5));
            inputPanel.add(new JLabel("Surname"));
            inputPanel.add(nameField);
            inputPanel.add(Box.createHorizontalStrut(5));
            inputPanel.add(new JLabel("Subject"));
            inputPanel.add(subjectField);
            inputPanel.add(Box.createHorizontalStrut(5));
            inputPanel.add(new JLabel("Mark"));
            inputPanel.add(markField);
            if (JOptionPane.showConfirmDialog(this, inputPanel,
                    "Enter all parameters", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                controller.add(numberField.getText(), nameField.getText(),
                        subjectField.getText(), markField.getText());
            }
        }
    }

    @Override
    public void open(List<Result> resultList, Set<String> subjectSet){
        firstList.setListData(resultList.toArray());
        secondList.setListData(subjectSet.toArray());
    }

    @Override
    public void addResult(List<Result> resultList) {
        firstList.setListData(resultList.toArray());
    }

    @Override
    public void addSubject(Set<String> subjectSet) {
        secondList.setListData(subjectSet.toArray());
    }

    @Override
    public void enableSave() {
        saveAs.setEnabled(true);
    }

    @Override
    public void showError(Exception e) {
        JOptionPane.showMessageDialog(this, e, e.getMessage(), JOptionPane.ERROR_MESSAGE);
    }

}