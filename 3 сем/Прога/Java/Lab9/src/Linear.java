public class Linear extends Series {
    private double difference;

    public Linear(int length, double first, double difference) {
        super(length, first);
        this.difference = difference;
    }

    @Override
    public double j(int i) throws IllegalArgumentException {
        if (i < 1)
            throw new IllegalArgumentException();
        return first + difference * (i - 1);
    }
}
