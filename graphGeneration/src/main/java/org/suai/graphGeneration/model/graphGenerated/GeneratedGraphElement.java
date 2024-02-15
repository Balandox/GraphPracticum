package org.suai.graphGeneration.model.graphGenerated;

/*
class, which represent element in adjencencyList and adjencencyMatrix
for example
 0 -> {1(5)} vertex 0 have a edge with vertex 1 and cost of this edge is 5
 1 -> {0(5), 2(3)}
 2 -> {1(3)}
 */

import java.util.Objects;

public class GeneratedGraphElement {

    private Integer vertex;

    private Integer weight;

    public GeneratedGraphElement(Integer vertex, Integer weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public GeneratedGraphElement(Integer vertex) {
        this.vertex = vertex;
    }

    public Integer getVertex() {
        return vertex;
    }

    public void setVertex(Integer vertex) {
        this.vertex = vertex;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GeneratedGraphElement that))
            return false;
        return Objects.equals(vertex, that.vertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex, weight);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.vertex);
        if(this.weight != null)
            sb.append("(").append(this.weight).append(")");
        return sb.toString();
    }
}
