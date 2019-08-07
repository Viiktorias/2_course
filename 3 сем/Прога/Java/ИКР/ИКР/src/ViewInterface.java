import java.util.List;
import java.util.Map;

public interface ViewInterface {
    void showByPosition(List<Security> sorted);

    void showBySurname(List<Security> sorted);

    void showBySalary(List<Security> sorted);

    void showSearchResult(String security, String salary);

    void showMinSalary(Map<Position, Integer> salaryMap);

    void enableData();

    void showError(Exception e);
}
