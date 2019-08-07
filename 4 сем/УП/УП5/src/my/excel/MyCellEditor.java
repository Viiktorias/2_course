package my.excel;

import my.cell.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class MyCellEditor extends DefaultCellEditor implements TableCellEditor {
    AbstractCell value;
    MyController controller;
    MyTable view;
    JTable table;

    public MyCellEditor(JTextField textField, MyController controller, MyTable view, JTable table) {
        super(textField);
        this.controller = controller;
        this.view = view;
        this.table = table;
    }

    public boolean stopCellEditing() {
        try {
            value = controller.addCell(table.getEditingColumn(), table.getEditingRow(), (String) super.getCellEditorValue());
        } catch (DateException e) {
            ((JComponent) getComponent()).setBorder(new LineBorder(new Color(247, 168, 171), 2));
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(view, e.getMessage(), "Некорректная дата", JOptionPane.ERROR_MESSAGE)
            );
            return false;
        } catch (FuncFormatException e) {
            ((JComponent) getComponent()).setBorder(new LineBorder(new Color(247, 168, 171), 2));
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(view, e.getMessage(), "Некорректная запись функции", JOptionPane.ERROR_MESSAGE)
            );
            return false;
        } catch (CycleRefException e) {
            ((JComponent) getComponent()).setBorder(new LineBorder(new Color(247, 168, 171), 2));
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(view, e.getMessage(), "Циклическая ссылка", JOptionPane.ERROR_MESSAGE)
            );
            return false;
        } catch (ExcelException e) {
            ((JComponent) getComponent()).setBorder(new LineBorder(new Color(247, 168, 171), 2));
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(view, e.getMessage(), "Некорректный формат", JOptionPane.ERROR_MESSAGE)
            );
            return false;
        }
        return super.stopCellEditing();
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        this.value = null;
        ((JComponent) getComponent()).setBorder(new LineBorder(new Color(33, 115, 70), 2));
        AbstractCell cell = (AbstractCell) value;
        return super.getTableCellEditorComponent(table, cell.getEditableValue(), isSelected, row, column);
    }

    public AbstractCell getCellEditorValue() {
        return value;
    }

    /*@Override
    public void cancelCellEditing() {
        value = new IntCell(null);
        super.cancelCellEditing();
    }*/
}