package org.suai.graphAlgorithms.model.kruskal;

public class Subset {

    private int parent;
    private int value;

    public Subset(int parent, int value) {
        this.parent = parent;
        this.value = value;
    }

    public Subset(){

    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Subset{" +
                "parent=" + parent +
                ", value=" + value +
                '}';
    }
}
