import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class CellRenderer extends DefaultTreeCellRenderer {
    private Icon[] icons;
    private JLabel label;

    public CellRenderer() {
        icons = new Icon[]{new ImageIcon("src\\database.png"),
                new ImageIcon("src\\course.png"), new ImageIcon("src\\group.png"),
                new ImageIcon("src\\student.png")};
        super.setFont(new Font("Verdana", Font.PLAIN, 14));
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded,
                leaf, row, hasFocus);
        if (leaf) {
            label.setIcon(icons[3]);
        } else if (value.toString().contains("группа")) {
            label.setIcon(icons[2]);
        } else if (value.toString().contains("курс")) {
            label.setIcon(icons[1]);
        } else {
            label.setIcon(icons[0]);
        }
        return label;
    }
}