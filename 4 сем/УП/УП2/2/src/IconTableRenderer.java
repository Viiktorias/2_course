import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;

public class IconTableRenderer extends DefaultTableCellRenderer {
    private Map<String, Icon> flags;
    private Font font;
    private JLabel label;

    public IconTableRenderer(Map<String, Icon> flags) {
        this.flags = flags;
        font = new Font("Georgia", Font.ITALIC, 14);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (!isSelected) {
            label = (JLabel) super.getTableCellRendererComponent(table, null, false, hasFocus, row, column);
            if (flags.containsKey(value.toString())) {
                label.setIcon(flags.get(value.toString()));
            } else {
                label.setIcon(flags.get("Country"));
            }
        } else {
            label = (JLabel) super.getTableCellRendererComponent(table, value, true, hasFocus, row, column);
            label.setIcon(null);
            label.setFont(font);
        }
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }
}