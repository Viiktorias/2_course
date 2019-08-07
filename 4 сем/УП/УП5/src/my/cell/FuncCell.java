package my.cell;

import my.excel.Link;

import java.util.List;

public class FuncCell extends AbstractCell {
    private String editableValue;
    private Type type;
    private Long value;
    private List<Long> constArgs;
    private List<Link> links;
    private FuncType funcType;
    private boolean containsDate;

    public FuncCell(String editableValue, List<Long> constArgs, List<Link> links, FuncType funcType, boolean containsDate) {
        this.editableValue = editableValue;
        this.constArgs = constArgs;
        this.links = links;
        this.funcType = funcType;
        this.containsDate = containsDate;
    }

    @Override
    public String getEditableValue() {
        return editableValue;
    }

    public List<Long> getConstArgs() {
        return constArgs;
    }

    public FuncType getFuncType() {
        return funcType;
    }

    public List<Link> getLinks() {
        return links;
    }

    public boolean getContainsDate() {
        return containsDate;
    }

    @Override
    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum FuncType {
        SUM,
        MIN,
        MAX
    }
}