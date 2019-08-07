import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    JButton edit;
    JButton add;
    JButton delete;
    private JTree tree;
    private Controller controller;

    public View(Controller controller, DefaultTreeModel model) {
        super("University");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        this.controller = controller;
        tree = new JTree(model);
        tree.setRowHeight(48);
        tree.setCellRenderer(new CellRenderer());
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        add(new JScrollPane(tree));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        add = new JButton();
        add.setIcon(new ImageIcon("src\\add.png"));
        panel.add(Box.createVerticalGlue());
        add.addActionListener(this);
        panel.add(add);
        edit = new JButton();
        edit.setIcon(new ImageIcon("src\\edit.png"));
        panel.add(Box.createVerticalGlue());
        edit.addActionListener(this);
        panel.add(edit);
        delete = new JButton();
        delete.setIcon(new ImageIcon("src\\delete.png"));
        panel.add(Box.createVerticalGlue());
        delete.addActionListener(this);
        panel.add(delete);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(Box.createHorizontalGlue());
        add(panel);
        pack();
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == add) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (controller.add(node)) {
                tree.updateUI();
            }
        } else if (source == edit) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (controller.edit(node)) {
                tree.setSelectionPath(null);
                tree.updateUI();
            }
        } else if (source == delete) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (controller.delete(node)) {
                tree.setSelectionPath(null);
                tree.updateUI();
            }
        }
    }
}