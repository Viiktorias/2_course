import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class IntegerCellEditor extends DefaultCellEditor {
    Object value;

    public IntegerCellEditor(JTextField textField) {
        super(textField);
    }

    public boolean stopCellEditing() {
        String s = (String) super.getCellEditorValue();
        try {
            value = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            ((JComponent) getComponent()).setBorder(new LineBorder(Color.red));
            return false;
        }
        return super.stopCellEditing();
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        this.value = null;
        ((JComponent) getComponent()).setBorder(new LineBorder(Color.black));
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

    public Object getCellEditorValue() {
        return value;
    }
}