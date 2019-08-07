public class Exponential extends Series {
    private double denominator;

    public Exponential(int length, double first, double denominator) {
        super(length, first);
        this.denominator = denominator;
    }

    @Override
    public double j(int i) throws IllegalArgumentException {
        if (i < 1)
            throw new IllegalArgumentException();
        return first * Math.pow(denominator, i - 1);
    }
}
