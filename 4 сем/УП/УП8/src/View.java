import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class View extends JFrame implements ActionListener {
    private JMenuItem newGame;
    private JMenuItem pause;
    private JMenuItem lvl1;
    private JMenuItem lvl2;
    private JMenuItem lvl3;
    private JMenuItem lvl4;
    private JMenuItem lvl5;
    private TankClient client;
    private int curLevel;
    private ProgressLine progress;

    public View() {
        super("Battle City Mini");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        setIconImage(new ImageIcon("src\\images\\icon.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JMenuBar JMenuBar = new JMenuBar();
        JMenu game = new JMenu("Game");
        JMenu level = new JMenu("Level");
        game.setFont(new Font("Consolas", Font.PLAIN, 15));
        level.setFont(new Font("Consolas", Font.PLAIN, 15));
        newGame = new JMenuItem("New Game");
        pause = new JMenuItem("Pause");
        lvl1 = new JMenuItem("Level 1");
        lvl2 = new JMenuItem("Level 2");
        lvl3 = new JMenuItem("Level 3");
        lvl4 = new JMenuItem("Level 4");
        lvl5 = new JMenuItem("Level 5");
        lvl1.setFont(new Font("Consolas", Font.PLAIN, 15));
        lvl2.setFont(new Font("Consolas", Font.PLAIN, 15));
        lvl3.setFont(new Font("Consolas", Font.PLAIN, 15));
        lvl4.setFont(new Font("Consolas", Font.PLAIN, 15));
        lvl5.setFont(new Font("Consolas", Font.PLAIN, 15));
        newGame.setFont(new Font("Consolas", Font.PLAIN, 15));
        pause.setFont(new Font("Consolas", Font.PLAIN, 15));
        game.add(newGame);
        game.add(pause);
        level.add(lvl1);
        level.add(lvl2);
        level.add(lvl3);
        level.add(lvl4);
        level.add(lvl5);
        JMenuBar.add(game);
        JMenuBar.add(level);
        newGame.addActionListener(this);
        pause.addActionListener(this);
        lvl1.addActionListener(this);
        lvl2.addActionListener(this);
        lvl3.addActionListener(this);
        lvl4.addActionListener(this);
        lvl5.addActionListener(this);
        newGame.setActionCommand("newGame");
        pause.setActionCommand("pause");
        lvl1.setActionCommand("lvl1");
        lvl2.setActionCommand("lvl2");
        lvl3.setActionCommand("lvl3");
        lvl4.setActionCommand("lvl4");
        lvl5.setActionCommand("lvl5");
        setJMenuBar(JMenuBar);
        try {
            client = new TankClient(new File("src\\games\\lvl1.game"));
            add(client, BorderLayout.WEST);
        } catch (IOException ignored) {
        }
        curLevel = 1;
        progress = new ProgressLine(client.getHeight(), client.getColOfTanks(), "1");
        add(progress, BorderLayout.EAST);
        client.setObserver(progress);
        addKeyListener(new KeyMonitor());
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "newGame": {
                setNewGame(curLevel);
                break;
            }
            case "pause": {
                client.pause();
                if (client.isRun())
                    pause.setText("Pause");
                else pause.setText("Continue");
                break;
            }
            case "lvl1": {
                setNewGame(1);
                curLevel = 1;
                break;
            }
            case "lvl2": {
                setNewGame(2);
                curLevel = 2;
                break;
            }
            case "lvl3": {
                setNewGame(3);
                curLevel = 3;
                break;
            }
            case "lvl4": {
                setNewGame(4);
                curLevel = 4;
                break;
            }
            case "lvl5": {
                setNewGame(5);
                curLevel = 5;
                break;
            }
        }
    }

    private void setNewGame(int level) {
        client.stop();
        this.remove(client);
        try {
            client = new TankClient(new File("src\\games\\lvl" + level + ".game"));
            add(client, BorderLayout.WEST);
        } catch (IOException ignored) {
        }
        this.remove(progress);
        progress = new ProgressLine(client.getHeight(), client.getColOfTanks(), Integer.toString(level));
        add(progress, BorderLayout.EAST);
        client.setObserver(progress);
        pause.setText("Pause");
        pack();
    }

    private class KeyMonitor extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            client.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            client.keyPressed(e);
        }

    }
}
