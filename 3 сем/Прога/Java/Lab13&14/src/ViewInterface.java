import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ViewInterface {
    void open(List<Result> resultList, Set<String> subjectSet);

    void addResult(List<Result> resultList);

    void addSubject(Set<String> subjectSet);

    void enableSave();

    void showError(Exception e);
}
