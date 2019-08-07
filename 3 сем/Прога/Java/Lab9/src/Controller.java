import java.io.FileNotFoundException;

public class Controller {
    private  View view;
    private Series model;
    public Controller(Series model) {
        this.model = model;
        view = new View(this, model);
    }
    public void save(String path, String length, String first, String difOrDen, int type) throws IllegalArgumentException {
        try {
            calculate(length, first, difOrDen, type);
            model.save(path);
        }
        catch (FileNotFoundException e) {}
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    public void calculate(String length, String first, String difOrDen, int type) throws IllegalArgumentException {
        try {
            switch (type) {
                case 0: {
                    model = new Linear(Integer.parseInt(length), Double.parseDouble(first), Double.parseDouble(difOrDen));
                    break;
                }
                case 1: {
                    model = new Exponential(Integer.parseInt(length), Double.parseDouble(first), Double.parseDouble(difOrDen));
                    break;
                }
            }
            view.out(model.toString());
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }
}
