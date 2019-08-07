package my.cell;

public abstract class AbstractCell {
    private String renderedValue;

    public AbstractCell(String renderedValue) {
        this.renderedValue = renderedValue;
    }

    public AbstractCell() {
        this.renderedValue = "";
    }

    public void setRenderedValue(String renderedValue) {
        this.renderedValue = renderedValue;
    }

    abstract public Type getType();

    abstract public Long getValue();

    public String getEditableValue() {
        return renderedValue;
    }


    @Override
    public String toString() {
        return renderedValue;
    }

    public enum Type {
        INT,
        DATE
    }
}
