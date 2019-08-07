import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class View extends JFrame {
    private JTable playerTable;
    private PlayerModel playerModel;
    private JCheckBoxMenuItem validateXML;
    private File lastFile;


    public View() {
        super("XML");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
        }
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icon.png")).getImage());
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem loadFromXML = new JMenuItem("Открыть используя DOM");
        loadFromXML.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(".");
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("XML файл", "xml"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    if (validateXML.getState()) {
                        Utils.validate(chooser.getSelectedFile());
                        JOptionPane.showMessageDialog(this, "Файл корректен", "Информация", JOptionPane.INFORMATION_MESSAGE);
                    }
                    playerModel = new PlayerModel(Utils.parseDOM(chooser.getSelectedFile()));
                    playerTable.setModel(playerModel);
                    lastFile = chooser.getSelectedFile();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Файл не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (SAXException | ParserConfigurationException ex) {
                    JOptionPane.showMessageDialog(this, "Некорректный файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        fileMenu.add(loadFromXML);
        JMenuItem SAXXml = new JMenuItem("Открыть используя SAX");
        SAXXml.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(".");
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("XML файл", "xml"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    if (validateXML.getState()) {
                        Utils.validate(chooser.getSelectedFile());
                        JOptionPane.showMessageDialog(this, "Файл корректен", "Информация", JOptionPane.INFORMATION_MESSAGE);
                    }
                    playerModel = new PlayerModel(Utils.parseSAX(chooser.getSelectedFile()));
                    playerTable.setModel(playerModel);
                    lastFile = chooser.getSelectedFile();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Файл не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (ParserConfigurationException | SAXException ex) {
                    JOptionPane.showMessageDialog(this, "Некорректный файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        fileMenu.add(SAXXml);

        JMenuItem saveToXML = new JMenuItem("Сохранить как");
        saveToXML.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(".");
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("XML файл", "xml"));
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    Utils.saveToXML(chooser.getSelectedFile(), playerModel.getItems());
                    lastFile = chooser.getSelectedFile();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Путь не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        fileMenu.add(saveToXML);

        JMenuItem calcXml = new JMenuItem("Рассчитать используя SAX");
        calcXml.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(".");
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("XML файл", "xml"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    StringBuilder answer = new StringBuilder();
                    if (validateXML.getState()) {
                        Utils.validate(chooser.getSelectedFile());
                        answer.append("Файл корректен\n");
                    }
                    answer.append(Utils.count(chooser.getSelectedFile()));
                    JOptionPane.showMessageDialog(this, answer,
                            "Информация", JOptionPane.INFORMATION_MESSAGE);
                    lastFile = chooser.getSelectedFile();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Файл не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (ParserConfigurationException | SAXException ex) {
                    JOptionPane.showMessageDialog(this, "Некорректный файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        fileMenu.add(calcXml);
        fileMenu.addSeparator();

        validateXML = new JCheckBoxMenuItem("Проверять корректность XML");
        validateXML.setState(true);
        fileMenu.add(validateXML);
        fileMenu.addSeparator();

        JMenuItem saveToBinary = new JMenuItem("Сохранить в бинарный файл");
        saveToBinary.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(".");
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Бинарный файл", "bin"));
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    Utils.saveToBinary(chooser.getSelectedFile(), playerModel.getItems());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Путь не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        fileMenu.add(saveToBinary);

        JMenuItem loadFromBinary = new JMenuItem("Открыть бинарный файл");
        loadFromBinary.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(".");
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Бинарный файл", "bin"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    playerModel = new PlayerModel(Utils.loadFromBinary(chooser.getSelectedFile()));
                    playerTable.setModel(playerModel);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Файл не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "Некорректный файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        fileMenu.add(loadFromBinary);

        fileMenu.addSeparator();

        JMenuItem convertToHTML = new JMenuItem("Конвертировать в HTML");
        convertToHTML.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(".");
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("HTML файл", "html"));
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    Utils.convert(lastFile, chooser.getSelectedFile(), getClass().getClassLoader().getResource("html.xsl"));
                    JOptionPane.showMessageDialog(this, lastFile.getName() + " конвертирован в " + chooser.getSelectedFile().getName(), "Converted", JOptionPane.INFORMATION_MESSAGE);
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(this, "Файл не открыт", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Путь не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (TransformerException ex) {
                    JOptionPane.showMessageDialog(this, "Некорректный файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        fileMenu.add(convertToHTML);

        JMenuItem convertToTXT = new JMenuItem("Конвертировать в TXT");
        convertToTXT.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(".");
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    Utils.convert(lastFile, chooser.getSelectedFile(), getClass().getClassLoader().getResource("txt.xsl"));
                    JOptionPane.showMessageDialog(this, lastFile.getName() + " конвертирован в " + chooser.getSelectedFile().getName(), "Converted", JOptionPane.INFORMATION_MESSAGE);
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(this, "Файл не открыт", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Путь не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (TransformerException ex) {
                    JOptionPane.showMessageDialog(this, "Некорректный файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        fileMenu.add(convertToTXT);


        JMenu playersMenu = new JMenu("Игроки");
        JMenuItem addPlayer = new JMenuItem("Добавить");
        addPlayer.addActionListener(e -> {
            playerModel.getItems().add(new Player());
            playerTable.updateUI();
        });
        playersMenu.add(addPlayer);

        JMenuItem deletePlayer = new JMenuItem("Удалить выделенных");
        deletePlayer.addActionListener(e -> {
            playerModel.deleteRows(playerTable.getSelectedRows());
            playerTable.updateUI();
        });
        playersMenu.add(deletePlayer);

        menuBar.add(fileMenu);
        menuBar.add(playersMenu);
        add(menuBar, BorderLayout.NORTH);

        playerModel = new PlayerModel();
        playerTable = new JTable(playerModel);
        add(new JScrollPane(playerTable), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}