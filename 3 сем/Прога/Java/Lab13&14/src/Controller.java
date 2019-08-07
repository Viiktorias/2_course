import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Controller implements ControllerInterface {
    private List<Result> model;
    private View view;
    private Set<String> subjects;

    public Controller() {
        model = new ArrayList<>();
        view = new View(this);
        subjects = new TreeSet<>();
    }

    @Override
    public void open(String path) {
        try {
            model.clear();
            subjects.clear();
            if (path.substring(path.length() - 4).compareToIgnoreCase(".xml") == 0) {
                 ResultReader.readXml(model, path);
            } else if (path.substring(path.length() - 4).compareToIgnoreCase(".txt") == 0) {
                ResultReader.readTxt(model, path);
            }
            if (model.isEmpty())
                throw new IOException("File does not contain required data");
            view.enableSave();
            Iterator<Result> it = model.listIterator();
            Result elem;
            while (it.hasNext()) {
                elem = it.next();
                if (!subjects.contains(elem.getSubject())) {
                    subjects.add(elem.getSubject());
                }
            }
            view.open(model, subjects);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            view.showError(e);
        }
    }


    @Override
    public void add(String num, String name, String subject, String mark) {
        try {
            Result item = new Result(num, name, subject, Integer.parseInt(mark));
            model.add(item);
            view.enableSave();
            view.addResult(model);
            if (!subjects.contains(item.getSubject())) {
                subjects.add(item.getSubject());
                view.addSubject(subjects);
            }
        }
        catch (IllegalArgumentException e) {
            view.showError(e);
        }
    }

    @Override
    public void saveAs(String path) {
        try(PrintStream printStream = new PrintStream(path)) {
            printStream.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            printStream.println("<Results>");
            Iterator<Result> it = model.listIterator();
            while (it.hasNext()) {
                printStream.println(it.next().toTag());
            }
            printStream.print("</Results>");
        }
        catch (IOException e) {
            view.showError(e);
        }
    }
}