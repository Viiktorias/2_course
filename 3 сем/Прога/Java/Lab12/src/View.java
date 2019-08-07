import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class View extends JFrame implements ActionListener, MouseListener {
    List<String> list;
    JList leftList, rightList;
    JButton toLeft, toRight;
    private JPanel firstPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;
    private DefaultListModel leftModel, rightModel;
    private String buttonName;
    private ButtonGroup radioGroup;
    //private JPanel radioPanel;

    public View(List<String> list) {
        super("Lab12");
        this.list = list;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        JTabbedPane tabbedPane = new JTabbedPane();
        firstPanel = new JPanel(new BorderLayout());
        secondPanel = new JPanel(new GridLayout(3, 3));
        thirdPanel = new JPanel();
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        tabbedPane.addTab("First", firstPanel);
        tabbedPane.addTab("Second", secondPanel);
        tabbedPane.addTab("Third", thirdPanel);
        add(tabbedPane);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        setSize(900, 700);
        leftModel = new DefaultListModel();
        rightModel = new DefaultListModel();
        leftList = new JList(leftModel);
        for (String item : list) {
            leftModel.addElement(item);
        }
        rightList = new JList(rightModel);

        leftList.setPreferredSize(new Dimension(300, 700));
        firstPanel.add(leftList, BorderLayout.WEST);
        toLeft = new JButton("<");
        toRight = new JButton(">");
        JPanel buttonPanel = new JPanel(new BorderLayout());
        toRight.setPreferredSize(new Dimension(300, 200));
        buttonPanel.add(toRight, BorderLayout.NORTH);
        toLeft.setPreferredSize(new Dimension(300, 200));
        buttonPanel.add(toLeft, BorderLayout.SOUTH);
        buttonPanel.setPreferredSize(new Dimension(300, 700));
        firstPanel.add(buttonPanel, BorderLayout.CENTER);
        rightList.setPreferredSize(new Dimension(300, 700));
        firstPanel.add(rightList, BorderLayout.EAST);

        toLeft.addActionListener(this);
        toRight.addActionListener(this);

        for (int i = 0; i < 9; i++) {
            buttonName = i + 1 + " button";
            JButton button = new JButton(buttonName);
            button.addMouseListener(this);
            button.setBackground(Color.RED);
            secondPanel.add(button);
        }

        ImageIcon[] icons = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            icons[i] = new ImageIcon("src\\" + i + ".png");
        }

        radioGroup = new ButtonGroup();
        JRadioButton radioButton;
        for (int i = 0; i < 5; i++) {
            radioButton = new JRadioButton("Label" + (i + 1));
            radioButton.setFont(new Font("Verdana", Font.BOLD, 27));
            radioButton.setIcon(icons[3]);
            radioButton.setSelectedIcon(icons[1]);
            radioButton.setPressedIcon(icons[0]);
            radioButton.setRolloverIcon(icons[2]);
            radioButton.setRolloverSelectedIcon(icons[4]);
            radioGroup.add(radioButton);
            thirdPanel.add(radioButton);
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toRight) {
            int[] temp = leftList.getSelectedIndices();
            move(temp, leftModel, rightModel);
        } else if (e.getSource() == toLeft) {
            int[] temp = rightList.getSelectedIndices();
            move(temp, rightModel, leftModel);
        }
    }

    private void move(int[] indices, DefaultListModel from, DefaultListModel to) {
        if (indices.length == 0) {
            for (Object item : from.toArray()) {
                to.addElement(item);
            }
            from.clear();
        } else {
            int i = 0;
            for (int index : indices) {
                to.addElement(from.get(index - i));
                from.removeElementAt(index - i);
                i++;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttonName = ((JButton) e.getSource()).getText();
        ((JButton) e.getSource()).setText("Clicked!");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ((JButton) e.getSource()).setText(buttonName);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ((JButton) e.getSource()).setBackground(Color.GREEN);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ((JButton) e.getSource()).setBackground(Color.RED);
    }
}
