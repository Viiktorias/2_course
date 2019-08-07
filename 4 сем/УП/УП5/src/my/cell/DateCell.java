package my.cell;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCell extends AbstractCell {
    private Long value;

    public DateCell(Long value) {
        this.value = value;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        setRenderedValue(format.format(new Date(value)));
    }

    public DateCell(Long value, String renderedValue) {
        super(renderedValue);
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }


    @Override
    public Type getType() {
        return Type.DATE;
    }
}
