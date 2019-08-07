import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    private Controller controller;
    private Series model;
    private JButton calculateButton;
    private JButton saveButton;
    private JComboBox progressionBox;
    private JLabel firstElemLabel;
    private JLabel difLabel;
    private JLabel denLabel;
    private JLabel countLabel;
    private JTextArea outputText;
    private JTextField firstElemText;
    private JTextField difOrDenText;
    private JTextField countText;

    public View(Controller controller, Series model) {
        super("Progression");
        this.controller = controller;
        this.model = model;
        String[] info = {"Арифметическая", "Геометрическая"};
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Elements
        calculateButton = new JButton("Рассчитать");
        saveButton = new JButton("Сохранить");
        firstElemText = new JTextField(5);
        firstElemText.setText("1");
        difOrDenText = new JTextField(5);
        difOrDenText.setText("1");
        countText = new JTextField(5);
        countText.setText("1");
        progressionBox = new JComboBox(info);
        outputText = new JTextArea();
        outputText.setEditable(false);
        outputText.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(outputText);
        firstElemLabel = new JLabel("Первый:");
        difLabel = new JLabel("Разность:");
        denLabel = new JLabel("Знаменатель:");
        countLabel = new JLabel("Количество:");

        //Layouts
        setLayout(new GridLayout(2, 1));
        JPanel interPanel = new JPanel(new GridLayout(1, 2));

        //формирование ячеек для ввода
        JPanel actionPanel = new JPanel(new GridLayout(3, 1));
        actionPanel.add(progressionBox);
        progressionBox.addActionListener(this);
        progressionBox.setSelectedIndex(1);
        ((JLabel) progressionBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        actionPanel.add(calculateButton);
        calculateButton.addActionListener(this);
        actionPanel.add(saveButton);
        saveButton.addActionListener(this);
        interPanel.add(actionPanel);


        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(firstElemLabel);
        firstElemLabel.setHorizontalAlignment(JLabel.CENTER);
        inputPanel.add(firstElemText);

        JPanel difOrDenLabel = new JPanel(new CardLayout(0, 0));
        difOrDenLabel.add(difLabel);
        difOrDenLabel.add(denLabel);
        difLabel.setHorizontalAlignment(JLabel.CENTER);
        denLabel.setHorizontalAlignment(JLabel.CENTER);
        difLabel.setVisible(false);
        denLabel.setVisible(true);

        inputPanel.add(difOrDenLabel);
        inputPanel.add(difOrDenText);
        inputPanel.add(countLabel);
        countLabel.setHorizontalAlignment(JLabel.CENTER);
        inputPanel.add(countText);
        interPanel.add(inputPanel);

        add(interPanel);
        add(scroll);


        pack();
        setSize(600, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == saveButton) {
            try {
                JFileChooser fileChooser = new JFileChooser(".");
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    String file = fileChooser.getDescription(fileChooser.getSelectedFile());
                    controller.save(file, countText.getText(), firstElemText.getText(), difOrDenText.getText(), progressionBox.getSelectedIndex());
                }
            } catch (IllegalArgumentException e) {
                out("Incorrect argument");
            }
        } else if (event.getSource() == calculateButton) {
            try {
                controller.calculate(countText.getText(), firstElemText.getText(), difOrDenText.getText(), progressionBox.getSelectedIndex());
            } catch (IllegalArgumentException e) {
                out("Incorrect argument");
            }
        } else if (event.getSource() == progressionBox) {
            if (progressionBox.getSelectedIndex() == 0) {
                difLabel.setVisible(true);
                denLabel.setVisible(false);
            }
            else {
                difLabel.setVisible(false);
                denLabel.setVisible(true);
            }
        }
    }

    public void out(String text) {
        outputText.setText(text);
    }
}
