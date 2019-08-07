public interface ControllerInterface {
    void open(String path);

    void add(String num, String name, String subject, String mark);

    void saveAs(String path);
}