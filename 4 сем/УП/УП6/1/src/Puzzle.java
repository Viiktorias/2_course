import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Puzzle extends JFrame implements KeyListener {
    private int verticalPieces;
    private int horizontalPieces;
    private int desiredWidth;
    private int desiredHeight;
    private JPanel panel;
    private JPanel examplePanel;
    private BufferedImage source;
    private BufferedImage resized;
    private Image image;
    private int width, height;
    private List<MyButton> buttons;
    private MyButton example;
    private List<Point> solution;

    public Puzzle() {
        verticalPieces = 4;
        horizontalPieces = 3;
        desiredWidth = 600;
        initUI(new File("src\\puzzle.png"));
    }

    private void initUI(File picture) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        solution = new ArrayList<>();
        for (int i = 0; i < verticalPieces; i++) {
            for (int j = 0; j < horizontalPieces; j++) {
                solution.add(new Point(i, j));
            }
        }
        buttons = new ArrayList<>();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
        panel.setLayout(new GridLayout(verticalPieces, horizontalPieces, 0, 0));
        try {
            source = MyUtils.loadImage(picture);
            desiredHeight = MyUtils.getNewHeight(desiredWidth, source.getWidth(), source.getHeight());
            resized = MyUtils.resizeImage(source, desiredWidth, desiredHeight,
                    BufferedImage.TYPE_INT_ARGB);
            width = resized.getWidth();
            height = resized.getHeight();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Не удалось загрузить изображение", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            desiredHeight = desiredWidth * 2 / 3;
            source = MyUtils.createImage(desiredWidth, desiredHeight);
            resized = source;
            width = desiredWidth;
            height = desiredHeight;
        }

        for (int i = 0; i < verticalPieces; i++) {
            for (int j = 0; j < horizontalPieces; j++) {
                image = createImage(MyUtils.getImage(resized, j * width / horizontalPieces, i * height / verticalPieces,
                        width / horizontalPieces, height / verticalPieces));
                var button = new MyButton(image);
                button.putClientProperty("position", new Point(i, j));
                buttons.add(button);
            }
        }
        Collections.shuffle(buttons);
        for (MyButton button : buttons) {
            panel.add(button);
            button.setBorder(BorderFactory.createLineBorder(Color.gray));
            button.addKeyListener(this);
        }
        add(panel, BorderLayout.WEST);

        examplePanel = new JPanel(new BorderLayout());
        examplePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        example = new MyButton(resized);
        example.setFocusable(false);
        example.setBackground(Color.black);
        example.setBorder(BorderFactory.createLineBorder(Color.black));
        examplePanel.add(example, BorderLayout.CENTER);
        examplePanel.setPreferredSize(new Dimension(width, height));
        add(examplePanel, BorderLayout.EAST);
        pack();
        setTitle("Puzzle");
        setResizable(false);
        setIconImage(new ImageIcon("src\\icon.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_RIGHT)
                || (e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyCode() == KeyEvent.VK_UP)) {
            var button = (MyButton) e.getSource();
            int bidx = buttons.indexOf(button);
            int lidx = 0;
            boolean canSwap = true;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT: {
                    if (bidx % horizontalPieces != 0) {
                        lidx = bidx - 1;
                    } else {
                        canSwap = false;
                    }
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    if (bidx % horizontalPieces != horizontalPieces - 1) {
                        lidx = bidx + 1;
                    } else {
                        canSwap = false;
                    }
                    break;
                }
                case KeyEvent.VK_UP: {
                    if (bidx >= horizontalPieces) {
                        lidx = bidx - horizontalPieces;
                    } else {
                        canSwap = false;
                    }
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    if (bidx < horizontalPieces * (verticalPieces - 1)) {
                        lidx = bidx + horizontalPieces;
                    } else {
                        canSwap = false;
                    }
                    break;
                }
            }
            if (canSwap) {
                Collections.swap(buttons, bidx, lidx);
                Rectangle old = buttons.get(bidx).getBounds();
                buttons.get(bidx).setBounds(buttons.get(lidx).getBounds());
                buttons.get(lidx).setBounds(old);
                if (MyUtils.checkSolution(buttons, solution)) {
                    JOptionPane.showMessageDialog(this, "Взлитаем",
                            "Готово", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}