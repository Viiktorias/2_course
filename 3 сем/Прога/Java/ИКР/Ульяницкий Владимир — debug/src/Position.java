public enum Position {
    AFRICA("Africa"), AMERICA("America"), EUROPE("Europe");
    private String description;

    Position(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}