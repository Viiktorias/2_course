import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class Controller implements ControllerInterface {
    private List<Security> model;
    private Map<Position, Integer> salaryMap;
    private View view;

    public Controller() {
        model = new ArrayList<>();
        view = new View(this);
        salaryMap = new TreeMap<>();
    }

    @Override
    public void open(String path) {
        try {
            model.clear();
            if (path.substring(path.length() - 4).compareToIgnoreCase(".xml") == 0) {
                SecurityReader.readXml(model, path);
            } else if (path.substring(path.length() - 4).compareToIgnoreCase(".txt") == 0) {
                SecurityReader.readTxt(model, path);
            }
            if (model.isEmpty())
                throw new IOException("File does not contain required data");
            salaryMap.clear();
            for (Worker elem : model) {
                if (salaryMap.containsKey(elem.getPosition())) {
                    if (salaryMap.get(elem.getPosition()) > elem.getSalary())
                        salaryMap.replace(elem.getPosition(), elem.getSalary());
                } else salaryMap.put(elem.getPosition(), elem.getSalary());
            }
            view.enableData();
        } catch (SAXException | ParserConfigurationException | IOException e) {
            view.showError(e);
        }
    }

    @Override
    public void showByPosition() {
        ArrayList<Security> sorted = (ArrayList<Security>) ((ArrayList<Security>) model).clone();
        Collections.sort(sorted, new ByPositionComparator());
        view.showByPosition(sorted);
    }

    @Override
    public void showBySurname() {
        ArrayList<Security> sorted = (ArrayList<Security>) ((ArrayList<Security>) model).clone();
        Collections.sort(sorted);
        view.showBySurname(sorted);
    }

    @Override
    public void showBySalary() {
        ArrayList<Security> sorted = (ArrayList<Security>) ((ArrayList<Security>) model).clone();
        Collections.sort(sorted, new BySalaryComparator());
        view.showBySalary(sorted);
    }

    @Override
    public void searchItem(String position, String surname, String guardedObject) {
        try {
            Security item = new Security(Integer.parseInt(position), surname, 1, guardedObject);
            boolean founded = false;
            for (Security elem : model) {
                if (elem.equals(item)) {
                    view.showSearchResult(surname, Integer.toString(elem.getSalary()));
                    founded = true;
                    break;
                }
            }
            if (!founded) {
                view.showSearchResult(surname, "Not founded");
            }
        } catch (IncorrectInputDataException | NumberFormatException e) {
            view.showError(e);
        }
    }

    @Override
    public void showMinSalary() {
        view.showMinSalary(salaryMap);
    }

    @Override
    public void showAll() {
        ArrayList<Security> sorted = (ArrayList<Security>) ((ArrayList<Security>) model).clone();
        Collections.sort(sorted, new ByPositionComparator());
        view.showByPosition(sorted);
        Collections.sort(sorted);
        view.showBySurname(sorted);
        Collections.sort(sorted, new BySalaryComparator());
        view.showBySalary(sorted);
    }
}