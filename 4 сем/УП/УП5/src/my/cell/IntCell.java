package my.cell;

public class IntCell extends AbstractCell {
    private Long value;

    public IntCell(Long value) {
        super(((value == null) ? "" : value.toString()));
        this.value = ((value == null) ? null : value * 86400000);
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return Type.INT;
    }
}
