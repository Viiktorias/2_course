package my.excel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MyRenderer extends DefaultTableCellRenderer {
    private Font font;
    private JLabel label;

    public MyRenderer() {
        font = new Font("Verdana", Font.PLAIN, 16);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setFont(font);
        if (value != null)
            label.setText(value.toString());
        return label;
    }
}