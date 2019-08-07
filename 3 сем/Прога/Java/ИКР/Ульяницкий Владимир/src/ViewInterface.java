import java.util.List;

public interface ViewInterface {
    void open(List<Beast> unsorted);

    void showByPosition(List<Beast> sorted);

    void showByMass(List<Beast> sorted);

    void showPrispos(List<String> prispList);

    void showPlaces(List<String> placesList);

    void showSearchResult(String max, String founded);

    void showCount(String pos, int count);

    void enableData();

    void showError(Exception e);
}
