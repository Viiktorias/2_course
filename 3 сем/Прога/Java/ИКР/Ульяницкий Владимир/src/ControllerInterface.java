public interface ControllerInterface {
    void open(String path);

    void showByPosition();

    void showByMass();

    void showPrispos();

    void showPlaces();

    void searchItem();

    void count(Position pos);

    void showAll();
}