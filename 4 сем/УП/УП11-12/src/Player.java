import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private String surname;
    private String patronymic;
    private int id;
    private String team;
    private int rating;

    public Player(String surname, String name, String patronymic, int id, String team, int rating) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.id = id;
        this.team = team;
        this.rating = rating;
    }

    public Player() {
        this("Иванов", "Иван", "Иванович", 0, "Умник", 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
