import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerModel extends AbstractTableModel implements Serializable {
    private final String[] colNames = {"ID", "Фамилия", "Имя", "Отчество", "Команда", "Рейтинг"};
    private List<Player> players;

    public PlayerModel(List<Player> players) {
        this.players = players;
    }

    public PlayerModel() {
        players = new ArrayList<>();
    }

    public List<Player> getItems() {
        return players;
    }

    public void setItems(List<Player> players) {
        this.players = players;
    }

    @Override
    public int getRowCount() {
        return players.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return players.get(rowIndex).getId();
            case 1:
                return players.get(rowIndex).getSurname();
            case 2:
                return players.get(rowIndex).getName();
            case 3:
                return players.get(rowIndex).getPatronymic();
            case 4:
                return players.get(rowIndex).getTeam();
            case 5:
                return players.get(rowIndex).getRating();
            default:
                return null;

        }
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if (column < 5 && column > 0)
            return String.class;
        else
            return Integer.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                players.get(rowIndex).setId((Integer) aValue);
                break;
            case 1:
                players.get(rowIndex).setSurname((String) aValue);
                break;
            case 2:
                players.get(rowIndex).setName((String) aValue);
                break;
            case 3:
                players.get(rowIndex).setPatronymic((String) aValue);
                break;
            case 4:
                players.get(rowIndex).setTeam((String) aValue);
                break;
            case 5:
                players.get(rowIndex).setRating((Integer) aValue);
                break;
        }
    }

    public void deleteRows(int[] rows) {
        for (int i = rows.length - 1; i >= 0; --i)
            if (rows[i] < players.size())
                players.remove(rows[i]);
    }
}
