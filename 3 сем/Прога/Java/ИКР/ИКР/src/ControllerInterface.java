public interface ControllerInterface {
    void open(String path);

    void showByPosition();

    void showBySurname();

    void showBySalary();

    void searchItem(String position, String surname, String guardedObject);

    void showMinSalary();

    void showAll();
}