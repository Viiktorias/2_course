import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

public class Viewer extends JFrame implements MouseListener, MouseMotionListener {
    private JButton button;
    private JPanel statusPanel;
    private JTextField status;
    private JPanel mousePanel;

    private Point mousePoint;
    private Point buttonPoint;

    public Viewer() throws HeadlessException {
        super("MouseListener");
        setLayout(new GridBagLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        statusPanel = new JPanel(new CardLayout(1, 1));
        status = new JTextField();
        status.setEditable(false);
        GridBagConstraints constraints = new GridBagConstraints();

        mousePanel = new JPanel();
        mousePanel.setLayout(null);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.anchor = GridBagConstraints.CENTER;
        add(mousePanel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.insets = new Insets(0, 3, 3, 3);
        constraints.gridy = 1;
        status.setFont(new Font("Consolas", Font.PLAIN, 14));
        statusPanel.add(status);
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statusPanel, constraints);

        button = new JButton("Кек");
        button.setBounds(400, 325, 100, 50);
        mousePanel.add(button);

        button.addMouseListener(this);
        button.addMouseMotionListener(this);
        mousePanel.addMouseListener(this);
        mousePanel.addMouseMotionListener(this);

        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    StringBuffer buf = new StringBuffer();
                    buf.append(button.getText());
                    if (buf.length() != 0)
                        buf.deleteCharAt(buf.length() - 1);
                    button.setText(buf.toString());
                }
                else if ((e.getKeyChar() >= '!' && e.getKeyChar() <= '~') || (e.getKeyChar() >= 'А' && e.getKeyChar() <= 'я')) {
                    StringBuffer buf = new StringBuffer();
                    buf.append(button.getText());
                    buf.append(e.getKeyChar());
                    button.setText(buf.toString());
                }
            }
        });

        pack();
        setSize(900, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mousePoint = mousePanel.getMousePosition();
        button.setLocation(mousePoint.x - button.getWidth() / 2, mousePoint.y - button.getHeight() / 2);
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == button) {
            if (mouseEvent.isControlDown()) {
                mousePoint = mousePanel.getMousePosition();
                if (mousePoint != null) {
                    button.setLocation(mousePoint.x - buttonPoint.x, mousePoint.y - buttonPoint.x);
                }
            }
            status.setText((mouseEvent.getX() + button.getX()) + ", " + (mouseEvent.getY() + button.getY()));
        }
        if (mouseEvent.getSource() == mousePanel) {
            status.setText(mouseEvent.getX() + ", " + mouseEvent.getY());
        }
    }

    public void mouseExited(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == mousePanel) {
            status.setText("");
        }
    }

    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == button) {
            buttonPoint = button.getMousePosition();
        }
    }

    public void mouseEntered(MouseEvent mouseEvent) {

    }

    public void mouseReleased(MouseEvent mouseEvent) {

    }

    public void mouseMoved(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == button) {
            status.setText((mouseEvent.getX() + button.getX()) + ", " + (mouseEvent.getY() + button.getY()));
        }
        if (mouseEvent.getSource() == mousePanel) {
            status.setText(mouseEvent.getX() + ", " + mouseEvent.getY());
        }
    }
}
