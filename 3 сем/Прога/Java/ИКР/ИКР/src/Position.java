public enum Position {
    THIRD(3), SECOND(2), FIRST(1);
    private int description;

    Position(int description) {
        this.description = description;
    }

    public int getDescription() {
        return description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(description).append(" category");
        return sb.toString();
    }
}