import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class Controller implements TableModelListener {
    private DefaultTableModel model;
    private Map<String, Icon> flags;
    private View view;

    public Controller() {
        flags = new HashMap<>();
        model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return ((row != this.getRowCount() - 1) || (column == 3));
            }

            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Icon.class;
                    case 2:
                        return Integer.class;
                    case 3:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }
        };
        model.addColumn("Country");
        model.addColumn("About");
        model.addColumn("Price");
        model.addColumn("Select");
        try (Scanner scanner = new Scanner(new File("src\\tours.txt"))) {
            scanner.useDelimiter("[|]|[\r\n]+");
            String country, about;
            int price;
            boolean selection;
            while (scanner.hasNext()) {
                country = scanner.next();
                about = scanner.next();
                price = scanner.nextInt();
                selection = scanner.nextBoolean();
                model.addRow(new Object[]{country, about, price, selection});
                if (!flags.containsKey(country)) {
                    flags.put(country, new ImageIcon("src\\" + country + ".png"));
                }
            }
            flags.put("Total", new ImageIcon("src\\Total.png"));
            Integer sum = 0;
            int n = model.getRowCount();
            for (int i = 0; i < n; i++) {
                if ((Boolean) model.getValueAt(i, 3)) {
                    sum += (Integer) model.getValueAt(i, 2);
                }
            }
            model.addRow(new Object[]{"Total", null, sum, false});
            flags.put("Country", new ImageIcon("src\\Country.png"));
            model.addTableModelListener(this);
            view = new View(this, model, flags);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void add() {
        model.insertRow(model.getRowCount() - 1, new Object[]{"Country", "About", 0, false});
    }

    public void delete(int[] positions) {
        for (int i = positions.length - 1; i >= 0; i--) {
            int n = model.getRowCount() - 1;
            Integer sum;
            if ((positions[i] != n) && (positions[i] != -1)) {
                if ((Boolean) model.getValueAt(positions[i], 3)) {
                    sum = (Integer) model.getValueAt(n, 2);
                    sum -= (Integer) model.getValueAt(positions[i], 2);
                    model.setValueAt(sum, n, 2);
                }
                model.removeRow(positions[i]);
            }
        }
    }

    public void saveAll() {
        try (PrintStream printStream = new PrintStream("src\\tours.txt")) {
            StringBuilder stringBuilder = new StringBuilder();
            int n = model.getRowCount() - 1;
            for (int i = 0; i < n; i++) {
                stringBuilder.append(model.getValueAt(i, 0)).append('|').append(model.getValueAt(i, 1)).
                        append('|').append(model.getValueAt(i, 2)).append('|').append(model.getValueAt(i, 3)).append("\r\n");
            }
            printStream.print(stringBuilder);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(view, "Ошибка", "Сохранить",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("src\\save-all.png"));
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getColumn() == 3) {
            int n = model.getRowCount() - 1;
            Integer sum;
            if (e.getLastRow() == n) {
                Boolean selection = (Boolean) model.getValueAt(n, 3);
                Vector<Vector> mas = model.getDataVector();
                for (int i = 0; i < n; i++) {
                    mas.get(i).set(3, selection);
                }
                sum = 0;
                if (selection) {
                    for (int i = 0; i < n; i++) {
                        sum += (Integer) model.getValueAt(i, 2);
                    }
                }
                model.setValueAt(sum, n, 2);
                view.updateUI();
            } else if ((Boolean) model.getValueAt(e.getLastRow(), 3)) {
                sum = (Integer) model.getValueAt(n, 2);
                sum += (Integer) model.getValueAt(e.getLastRow(), 2);
                model.setValueAt(sum, n, 2);
            } else {
                sum = (Integer) model.getValueAt(n, 2);
                sum -= (Integer) model.getValueAt(e.getLastRow(), 2);
                model.setValueAt(sum, n, 2);
            }
        } else if ((e.getColumn() == 2) && (e.getLastRow() != model.getRowCount() - 1) && ((Boolean) model.getValueAt(e.getLastRow(), 3))) {
            int n = model.getRowCount() - 1;
            Integer sum = 0;
            for (int i = 0; i < n; i++) {
                if ((Boolean) model.getValueAt(i, 3)) {
                    sum += (Integer) model.getValueAt(i, 2);
                }
            }
            model.setValueAt(sum, n, 2);
        }
    }
}
