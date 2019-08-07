package my.excel;

import my.cell.AbstractCell;
import my.cell.IntCell;

import javax.swing.table.DefaultTableModel;

public class MyModel extends DefaultTableModel {

    public MyModel() {
        clear();
    }

    @Override
    public Class getColumnClass(int column) {
        return String.class;
    }

    public void clear() {
        AbstractCell[][] data = new IntCell[128][26];
        AbstractCell[] row = new IntCell[26];
        String[] names = new String[26];
        for (int i = 0; i < 26; i++) {
            names[i] = Character.toString(((char) (65 + i)));
            row[i] = new IntCell(null);
        }
        for (int j = 0; j < 128; j++) {
            data[j] = row;
        }
        setDataVector(data, names);
    }

    @Override
    public AbstractCell getValueAt(int row, int column) {
        return (AbstractCell) super.getValueAt(row, column);
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        super.setValueAt((AbstractCell) aValue, row, column);
    }
}