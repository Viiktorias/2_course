import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class Controller implements ControllerInterface {
    final private List<Beast> model;
    final private Map<Position, Integer> countMap;
    private ViewInterface view;

    {
        model = new ArrayList<>();
        countMap = new TreeMap<>();
    }

    public Controller() {
        view = new View(this);
    }

    @Override
    public void open(String path) {
        try {
            model.clear();
            if (path.substring(path.length() - 4).compareToIgnoreCase(".xml") == 0) {
                BeastReader.readXml(model, path);
            } else if (path.substring(path.length() - 4).compareToIgnoreCase(".txt") == 0) {
                BeastReader.readTxt(model, path);
            }
            if (model.isEmpty())
                throw new IOException("File does not contain required data");
            countMap.clear();
            for (Animal elem : model) {
                if (countMap.containsKey(elem.getPosition())) {
                    countMap.replace(elem.getPosition(), countMap.get(elem.getPosition()) + 1);
                } else countMap.put(elem.getPosition(), 1);
            }
            view.open(model);
            view.enableData();
        } catch (SAXException | ParserConfigurationException | IOException e) {
            view.showError(e);
        }
    }

    @Override
    public void showByPosition() {
        List<Beast> sorted = (ArrayList<Beast>) ((ArrayList<Beast>) model).clone();
        Collections.sort(sorted, new ByPositionComparator());
        view.showByPosition(sorted);
    }

    @Override
    public void showByMass() {
        List<Beast> sorted = (ArrayList<Beast>) ((ArrayList<Beast>) model).clone();
        Collections.sort(sorted, new ByMassComparator());
        view.showByMass(sorted);
    }

    @Override
    public void showPrispos() {
        Map<Position, String> placeMap = new HashMap<>();
        for (Animal elem : model) {
            if (!placeMap.containsKey(elem.getPosition())) {
                placeMap.put(elem.getPosition(), elem.getName());
            }
        }
        List<String> prispList = new ArrayList<>();
        for (Position key : placeMap.keySet())
            prispList.add(placeMap.get(key));
        view.showPrispos(prispList);
    }

    @Override
    public void showPlaces() {
        Set<Position> placeSet = new TreeSet<>();
        for (Animal elem : model) {
            if (!placeSet.contains(elem.getPosition())) {
                placeSet.add(elem.getPosition());
            }
        }
        List<String> placesList = new ArrayList<>();
        for (Position elem : placeSet) {
            placesList.add(elem.toString());
        }
        view.showPlaces(placesList);
    }

    @Override
    public void searchItem() {

        ArrayList<Beast> sorted = (ArrayList<Beast>) ((ArrayList<Beast>) model).clone();
        Collections.sort(sorted, new ByMassComparator());
        Beast max = sorted.get(0);
        Beast elem = null;
        boolean founded = false;
        for (Beast item : sorted) {
            if (2 * item.getMass() == max.getMass()) {
                elem = item;
                founded = true;
                break;
            }
        }
        if (!founded) {
            view.showSearchResult(max.toString(), "Not founded");
        } else {
            view.showSearchResult(max.toString(), elem.toString());
        }
    }

    @Override
    public void count(String pos) {
        try {
            view.showCount(pos, countMap.get(Position.valueOf(pos.toUpperCase())));
        } catch (IllegalArgumentException e) {
            view.showError(new IllegalArgumentException("No such position"));
        }
    }

    @Override
    public void showAll() {
        showByPosition();
        showByMass();
        showPrispos();
        showPlaces();
    }
}