package my.excel;

import my.cell.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyController implements TableModelListener {
    private MyModel model;
    private MyTable view;
    private List<List<Integer>> observers;
    private List<List<Integer>> observed;
    private int maxFilledCol;
    private int maxFilledRow;

    public MyController() {
        model = new MyModel();
        view = new MyTable(model, this);
        model.addTableModelListener(this);
        observed = new ArrayList<>(3328);
        observers = new ArrayList<>(3328);
        for (int i = 0; i < 3328; i++) {
            observed.add(new ArrayList<>());
            observers.add(new ArrayList<>());
        }
    }

    public AbstractCell addCell(int col, int row, String value) throws ExcelException {
        String valueWithoutSpaces = value.replaceAll("\\s+", "");
        Integer pos = row * 26 + col;
        List<Integer> observedByCur = observed.get(row * 26 + col);
        int size = observedByCur.size();
        for (int i = 0; i < size; i++) {
            observers.get(observedByCur.get(i)).remove(pos);
        }
        observedByCur.clear();
        if (valueWithoutSpaces.equals("")) {
            return new IntCell(null);
        }
        if (valueWithoutSpaces.charAt(0) == '=') {
            List<String> tokens = MyParser.parseFunc(valueWithoutSpaces.substring(1));
            List<Long> constArgs = new ArrayList<>();
            List<Link> links = new ArrayList<>();
            Integer link;
            boolean containsDate = false;
            FuncCell.FuncType funcType;
            if (tokens.get(0).equals("MAX")) {
                funcType = FuncCell.FuncType.MAX;
            } else if (tokens.get(0).equals("MIN")) {
                funcType = FuncCell.FuncType.MIN;
            } else funcType = FuncCell.FuncType.SUM;
            size = tokens.size();
            for (int i = 1; i < size; i++) {
                if ((tokens.get(i).equals(";")) || (tokens.get(i).equals("+")) || (tokens.get(i).equals("-"))) {
                    continue;
                } else if ((tokens.get(i).equals(":"))) {
                    int maxl = Integer.parseInt(tokens.get(i + 1).substring(1));
                    int maxj = tokens.get(i + 1).charAt(0) - 65;
                    for (int l = Integer.parseInt(tokens.get(i - 1).substring(1)); l <= maxl; l++) {
                        for (int j = tokens.get(i - 1).charAt(0) - 65; j <= maxj; j++) {
                            link = j + 26 * l - 26;
                            links.add(new Link(1, link));
                            observedByCur.add(link);
                            observers.get(link).add(pos);
                            if (link == row * 26 + col)
                                throw new CycleRefException("Функция содержит ссылку на текущую ячейку");
                            if (isCycle(row * 26 + col, row * 26 + col, link))
                                throw new CycleRefException("Функция содержит циклическую ссылку на текущую ячейку");
                        }
                    }
                } else if (MyParser.intVerify(tokens.get(i))) {
                    try {
                        if (tokens.get(i - 1).equals("-"))
                            constArgs.add(-86400000L * Integer.parseInt(tokens.get(i)));
                        else
                            constArgs.add(86400000L * Integer.parseInt(tokens.get(i)));
                    } catch (NumberFormatException e) {
                        throw new ExcelException("Слишком длинное число");
                    }
                } else if (MyParser.smellsLikeDate(tokens.get(i))) {
                    containsDate = true;
                    if (tokens.get(i - 1).equals("-"))
                        constArgs.add(-1 * parseDate(tokens.get(i)));
                    else
                        constArgs.add(parseDate(tokens.get(i)));
                } else if ((tokens.get(i - 1).equals(":")) || ((i < size - 1) && (tokens.get(i + 1).equals(":")))) {
                } else {
                    link = tokens.get(i).charAt(0) - 91 + Integer.parseInt(tokens.get(i).substring(1)) * 26;
                    if (tokens.get(i - 1).equals("-"))
                        links.add(new Link(-1, link));
                    else
                        links.add(new Link(1, link));
                    observedByCur.add(link);
                    observers.get(link).add(pos);
                    if (link == row * 26 + col)
                        throw new CycleRefException("Функция содержит ссылку на текущую ячейку");
                    if (isCycle(row * 26 + col, row * 26 + col, link))
                        throw new CycleRefException("Функция содержит циклическую ссылку на текущую ячейку");
                }
            }
            maxFilledCol = Math.max(maxFilledCol, col);
            maxFilledRow = Math.max(maxFilledRow, row);
            return new FuncCell(value, constArgs, links, funcType, containsDate);

        } else if (MyParser.intVerify(value)) {
            try {
                maxFilledCol = Math.max(maxFilledCol, col);
                maxFilledRow = Math.max(maxFilledRow, row);
                return new IntCell((long) Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ExcelException("Слишком длинное число");
            }
        }
        if (MyParser.smellsLikeDate(value)) {
            if (MyParser.dateVerify(value)) {
                maxFilledCol = Math.max(maxFilledCol, col);
                maxFilledRow = Math.max(maxFilledRow, row);
                return new DateCell(parseDate(value), value);
            } else throw new DateException("Дата не существует");
        }
        throw new ExcelException("Формат не распознан");
    }

    private Long parseDate(String value) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat format3 = new SimpleDateFormat("M/d/yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format1.parse(value));
        } catch (ParseException e) {
        }
        try {
            calendar.setTime(format3.parse(value));
        } catch (ParseException e) {
        }
        return calendar.getTime().getTime();
    }

    private boolean isCycle(int checked, int cur, int link) {
        if (link == checked)
            return true;
        boolean cycle = false;
        for (int i = 0; i < observed.get(cur).size(); i++) {
            if (isCycle(checked, link, observed.get(cur).get(i))) {
                cycle = true;
                break;
            }
        }
        return cycle;
    }

    private void updateCell(int col, int row) {
        int size;
        if (model.getValueAt(row, col) instanceof FuncCell) {
            FuncCell cell = (FuncCell) model.getValueAt(row, col);
            AbstractCell linkedCell;
            List<Long> constArgs = cell.getConstArgs();
            List<Link> links = cell.getLinks();
            Long value;
            boolean isDate = cell.getContainsDate();
            if (FuncCell.FuncType.MAX.equals(cell.getFuncType())) {
                value = Long.MIN_VALUE;
                size = constArgs.size();
                for (int i = 0; i < size; i++) {
                    value = Math.max(value, constArgs.get(i));
                }
                size = links.size();
                for (int i = 0; i < size; i++) {
                    linkedCell = model.getValueAt(links.get(i).link / 26, links.get(i).link % 26);
                    if (linkedCell.getValue() != null)
                        value = Math.max(value, linkedCell.getValue());
                    if (AbstractCell.Type.DATE.equals(linkedCell.getType()))
                        isDate = true;
                }
                if (value == Long.MIN_VALUE)
                    value = 0L;
            } else if (FuncCell.FuncType.MIN.equals(cell.getFuncType())) {
                value = Long.MAX_VALUE;
                size = constArgs.size();
                for (int i = 0; i < size; i++) {
                    value = Math.min(value, constArgs.get(i));
                }
                size = links.size();
                for (int i = 0; i < size; i++) {
                    linkedCell = model.getValueAt(links.get(i).link / 26, links.get(i).link % 26);
                    if (linkedCell.getValue() != null)
                        value = Math.min(value, linkedCell.getValue());
                    if (AbstractCell.Type.DATE.equals(linkedCell.getType()))
                        isDate = true;
                }
                if (value == Long.MAX_VALUE)
                    value = 0L;
            } else {
                value = 0L;
                size = constArgs.size();
                for (int i = 0; i < size; i++) {
                    value += constArgs.get(i);
                }
                size = links.size();
                for (int i = 0; i < size; i++) {
                    linkedCell = model.getValueAt(links.get(i).link / 26, links.get(i).link % 26);
                    if (linkedCell.getValue() != null)
                        value += links.get(i).abs * linkedCell.getValue();
                    if (AbstractCell.Type.DATE.equals(linkedCell.getType())) {
                        isDate = true;
                    }
                }
            }
            cell.setValue(value);
            if (!isDate) {
                value /= 86400000;
                cell.setRenderedValue((value).toString());
                cell.setType(AbstractCell.Type.INT);
            } else {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                cell.setRenderedValue(format.format(new Date(value)));
                cell.setType(AbstractCell.Type.DATE);
            }
        }
        List<Integer> observersOfCur = observers.get(row * 26 + col);
        size = observersOfCur.size();
        for (int i = 0; i < size; i++) {
            updateCell(observersOfCur.get(i) % 26, observersOfCur.get(i) / 26);
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if ((e.getColumn() != -1) && (e.getLastRow() != -1))
            updateCell(e.getColumn(), e.getLastRow());
        view.updateUI();
    }

    public void save(String path) {
        try (PrintStream printStream = new PrintStream(path)) {
            StringBuilder stringBuilder = new StringBuilder();
            AbstractCell cell;
            for (int i = 0; i <= maxFilledRow; i++) {
                for (int j = 0; j <= maxFilledCol; j++) {
                    cell = model.getValueAt(i, j);
                    stringBuilder.append(cell.getEditableValue()).append(',');
                }
                while (stringBuilder.charAt(stringBuilder.length() - 1) == ',')
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                stringBuilder.append("\r\n");
            }
            printStream.print(stringBuilder);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(view, "Ошибка: путь не существует", "Сохранить",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("src\\save.png"));
        }
    }

    public void open(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            model.clear();
            for (int i = 0; i < 3328; i++) {
                observed.get(i).clear();
                observers.get(i).clear();
            }
            maxFilledCol = 0;
            maxFilledRow = 0;
            imp(scanner);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(view, "Ошибка: файл не найден", "Открыть",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("src\\open.png"));
        }
    }

    public void insert(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            imp(scanner);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(view, "Ошибка: файл не найден", "Импортировать",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon("src\\import.png"));
        }
    }

    private void imp(Scanner scanner) {
        StringTokenizer tokenizer;
        int i, j;
        String token;
        i = 0;
        while (scanner.hasNextLine()) {
            tokenizer = new StringTokenizer(scanner.nextLine(), "[,]", true);
            j = 0;
            while (tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken();
                if (!token.equals(",")) {
                    try {
                        model.setValueAt(addCell(j, i, token), i, j);
                    } catch (DateException e) {
                        String label = (char) (65 + j) + Integer.toString(i + 1);
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(view, e.getMessage(), "Некорректная дата: " + label, JOptionPane.ERROR_MESSAGE)
                        );
                    } catch (FuncFormatException e) {
                        String label = (char) (65 + j) + Integer.toString(i + 1);
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(view, e.getMessage(), "Некорректная запись функции: " + label, JOptionPane.ERROR_MESSAGE)
                        );
                    } catch (CycleRefException e) {
                        String label = (char) (65 + j) + Integer.toString(i + 1);
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(view, e.getMessage(), "Циклическая ссылка: " + label, JOptionPane.ERROR_MESSAGE)
                        );
                    } catch (ExcelException e) {
                        String label = (char) (65 + j) + Integer.toString(i + 1);
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(view, e.getMessage(), "Некорректный формат: " + label, JOptionPane.ERROR_MESSAGE)
                        );
                    }
                } else j++;
            }
            i++;
        }
    }
}