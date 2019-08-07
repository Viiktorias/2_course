import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class IntegerTableRenderer extends DefaultTableCellRenderer {
    private Font font;
    private JLabel label;

    public IntegerTableRenderer() {
        font = new Font("Georgia", Font.ITALIC, 14);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }
}