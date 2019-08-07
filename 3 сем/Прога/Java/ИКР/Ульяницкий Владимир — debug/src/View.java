import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class View extends JFrame implements ViewInterface, ActionListener {
    private Controller controller;
    private JTabbedPane tabbedPane;
    private JPanel firstPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;
    private JPanel fourthPanel;
    private JPanel fifthPanel;
    private JList firstList;
    private JList secondList;
    private JList thirdList;
    private JList fourthList;
    private JList fifthList;
    private JButton button;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenu data;
    private JMenuItem byPosition;
    private JMenuItem byMass;
    private JMenuItem prispos;
    private JMenuItem places;
    private JMenuItem search;
    private JMenuItem count;
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
        fourthPanel = new JPanel(new BorderLayout());
        fifthPanel = new JPanel(new BorderLayout());
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

        fourthList = new JList();
        //fourthList.setCellRenderer(new ListRenderer());
        JScrollPane scrollPane4 = new JScrollPane(fourthList);
        fourthPanel.add(scrollPane4);

        fifthList = new JList();
        //fifthList.setCellRenderer(new ListRenderer());
        JScrollPane scrollPane5 = new JScrollPane(fifthList);
        fifthPanel.add(scrollPane5);

        tabbedPane.addTab("First", firstPanel);
        tabbedPane.addTab("Second", secondPanel);
        tabbedPane.addTab("Third", thirdPanel);
        tabbedPane.addTab("Fourth", fourthPanel);
        tabbedPane.addTab("Fifth", fifthPanel);

        add(tabbedPane);
        setupTabTraversalKeys(tabbedPane);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);


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
        byMass = new JMenuItem("By mass");
        prispos = new JMenuItem("Prisposoblency");
        places = new JMenuItem("Places");
        search = new JMenuItem("Search");
        count = new JMenuItem("Count");
        byPosition.setEnabled(false);
        byMass.setEnabled(false);
        prispos.setEnabled(false);
        places.setEnabled(false);
        search.setEnabled(false);
        count.setEnabled(false);
        data.add(byPosition);
        data.add(byMass);
        data.add(prispos);
        data.add(places);
        data.add(search);
        data.add(count);

        menuBar.add(file);
        menuBar.add(data);
        setJMenuBar(menuBar);

        open.addActionListener(this);
        exit.addActionListener(this);
        byPosition.addActionListener(this);
        byMass.addActionListener(this);
        prispos.addActionListener(this);
        places.addActionListener(this);
        search.addActionListener(this);
        count.addActionListener(this);

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
            tabbedPane.setSelectedIndex(1);
        } else if (event.getSource() == byMass) {
            controller.showByMass();
            tabbedPane.setSelectedIndex(2);
        } else if (event.getSource() == prispos) {
            controller.showPrispos();
            tabbedPane.setSelectedIndex(3);
        } else if (event.getSource() == places) {
            controller.showPlaces();
            tabbedPane.setSelectedIndex(4);
        } else if (event.getSource() == search) {
            controller.searchItem();
        } else if (event.getSource() == count) {
            JTextField positionField = new JTextField(10);
            positionField.setText("Africa");
            JPanel inputPanel = new JPanel();
            inputPanel.add(new JLabel("Position"));
            inputPanel.add(positionField);
            if (JOptionPane.showConfirmDialog(this, inputPanel,
                    "Enter position", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                controller.count(positionField.getText());
            }
        } else if (event.getSource() == button) {
            controller.showAll();
        }
    }

    @Override
    public void open(List<Beast> unsorted) {
        firstList.setListData(unsorted.toArray());
    }

    @Override
    public void showByPosition(List<Beast> sorted) {
        secondList.setListData(sorted.toArray());
    }

    @Override
    public void showByMass(List<Beast> sorted) {
        thirdList.setListData(sorted.toArray());
    }

    @Override
    public void showPrispos(List<String> prispList) {
        fourthList.setListData(prispList.toArray());
    }

    @Override
    public void showPlaces(List<String> placesList) {
        fifthList.setListData(placesList.toArray());
    }

    @Override
    public void showSearchResult(String max, String founded) {
        JOptionPane.showMessageDialog(this, founded, max, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showCount(String pos, int count) {
        JOptionPane.showMessageDialog(this, count, pos, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void enableData() {
        byPosition.setEnabled(true);
        byMass.setEnabled(true);
        prispos.setEnabled(true);
        places.setEnabled(true);
        search.setEnabled(true);
        count.setEnabled(true);
        button.setEnabled(true);
    }

    @Override
    public void showError(Exception e) {
        JOptionPane.showMessageDialog(this, e, e.getMessage(), JOptionPane.ERROR_MESSAGE);
    }

    private void setupTabTraversalKeys(JTabbedPane tabbedPane) {
        KeyStroke ctrlTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK);

        // Remove ctrl-tab from normal focus traversal
        Set<AWTKeyStroke> forwardKeys = new HashSet<>(tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        forwardKeys.remove(ctrlTab);
        tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);

        // Remove ctrl-shift-tab from normal focus traversal
        Set<AWTKeyStroke> backwardKeys = new HashSet<>(tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        backwardKeys.remove(ctrlShiftTab);
        tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);

        // Add keys to the tab's input map
        InputMap inputMap = tabbedPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(ctrlTab, "navigateNext");
        inputMap.put(ctrlShiftTab, "navigatePrevious");
    }


    class ListRenderer extends DefaultListCellRenderer {
        Font font;
        Map<Integer, ImageIcon> iconMap;

        public ListRenderer() {
            font = new Font("Consolas", Font.PLAIN, 14);
            iconMap = new HashMap<>();
            iconMap.put(0, new ImageIcon("src\\0.png"));
            iconMap.put(1, new ImageIcon("src\\1.png"));
            iconMap.put(2, new ImageIcon("src\\2.png"));
            iconMap.put(3, new ImageIcon("src\\3.png"));
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            if (iconMap.containsKey(((Animal) value).getPosition().ordinal() + 1)) {
                label.setIcon(iconMap.get(((Animal) value).getPosition().ordinal() + 1));
            } else {
                label.setIcon(iconMap.get(0));
            }
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }
}