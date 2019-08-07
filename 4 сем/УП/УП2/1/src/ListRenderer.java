import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ListRenderer extends DefaultListCellRenderer {
    private Font font;

    public ListRenderer() {
        font = new Font("Verdana", Font.PLAIN, 24);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        var valueEntry = (Map.Entry<String, String>) value;
        var valueString = valueEntry.getKey() + " - " + valueEntry.getValue();
        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, valueString, index, isSelected, cellHasFocus);
        if (isSelected) {
            label.setIcon(new ImageIcon("src\\" + valueEntry.getKey() + ".png"));
        }
        label.setHorizontalTextPosition(JLabel.RIGHT);
        label.setFont(font);
        return label;
    }
}