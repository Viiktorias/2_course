import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem open;
    private JMenuItem save;
    private JMenuItem close;
    /*private JButton dimgrayButton;
    private JButton grayButton;
    private JButton darkgrayButton;*/
    private JPanel controlsPanel;
    private PaintPanel paintPanel;
    private JRadioButton dimgray;
    private JRadioButton gray;
    private JRadioButton darkgray;
    private Color color;

    private int xOld;
    private int yOld;
    private int xCur;
    private int yCur;

    public Main(String title) {
        super(title);
        setIconImage(new ImageIcon("src\\Paint.png").getImage());
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }

       /* dimgrayButton = new JButton("Dimgray");
        grayButton = new JButton("Gray");
        darkgrayButton = new JButton("Darkgray");*/

        dimgray = new JRadioButton("Dimgray");
        gray = new JRadioButton("Gray");
        darkgray = new JRadioButton("Darkgray");

        dimgray.addActionListener(this);
        gray.addActionListener(this);
        darkgray.addActionListener(this);

        menuBar = new JMenuBar();
        file = new JMenu("File");
        open = new JMenuItem("Open", new ImageIcon("src\\Open.png"));
        save = new JMenuItem("Save", new ImageIcon("src\\Save.png"));
        close = new JMenuItem("Exit", new ImageIcon("src\\Close.png"));
        file.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        file.add(save);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        file.addSeparator();
        file.add(close);
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
        menuBar.add(file);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlsPanel = new JPanel(new GridLayout(1, 3));
        GridBagConstraints constraints = new GridBagConstraints();
        paintPanel = new PaintPanel(1500, 900);
        paintPanel.setLayout(null);
        JScrollPane pane = new JScrollPane(paintPanel);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.anchor = GridBagConstraints.CENTER;
        add(pane, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.insets = new Insets(0, 3, 3, 3);
        constraints.gridy = 1;
        constraints.ipady = 20;
        controlsPanel.add(darkgray);
        controlsPanel.add(gray);
        controlsPanel.add(dimgray);
        ButtonGroup group = new ButtonGroup();
        group.add(darkgray);
        group.add(gray);
        group.add(dimgray);
        gray.setSelected(true);
        color = new Color(128, 128, 128);
        add(controlsPanel, constraints);

        paintPanel.addMouseMotionListener(this);
        paintPanel.addMouseListener(this);

        open.addActionListener(this);
        save.addActionListener(this);
        close.addActionListener(this);

        pack();
    }

    public static void main(String[] args) {
        Main frame = new Main("PaintLite");
        frame.setSize(1300, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == paintPanel) {
            Graphics graphics = paintPanel.getBuffer().getGraphics();
            graphics.setColor(color);
            graphics.fillRect(e.getX(), e.getY(), 1, 1);
            paintPanel.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == paintPanel) {
            xOld = e.getX();
            yOld = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == paintPanel) {
            xCur = e.getX();
            yCur = e.getY();
            Graphics graphics = paintPanel.getBuffer().getGraphics();
            graphics.setColor(color);
            graphics.drawLine(xOld, yOld, xCur, yCur);
            xOld = xCur;
            yOld = yCur;
            paintPanel.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == darkgray) {
            color = new Color(169, 169, 169);
        } else if (e.getSource() == gray) {
            color = new Color(128, 128, 128);
        } else if (e.getSource() == dimgray) {
            color = new Color(105, 105, 105);
        } else if (e.getSource() == open) {
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image file", ImageIO.getReaderFormatNames()));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage bufImage = ImageIO.read(fileChooser.getSelectedFile());
                    paintPanel.loadImage(bufImage);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex, ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == save) {
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image file", ImageIO.getWriterFormatNames()));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    ImageIO.write(paintPanel.getBuffer(), Main.getFileExtension(file), file);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex, ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == close) {
            setVisible(false);
            dispose();
        }
    }
}