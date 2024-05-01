package org.suai.graphAlgorithms.model.kruskal;

import java.util.Objects;

public class Edge implements Comparable<Edge> {
    private int source;
    private int destination;
    private int weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Edge(int source, int destination) {
        this.source = source;
        this.destination = destination;
    }

    public Edge(){

    }

    @Override
    public int compareTo(Edge edgeToCompare) {
        return this.weight - edgeToCompare.weight;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", destination=" + destination +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge edge)) return false;
        return (source == edge.source && destination == edge.destination && weight == edge.weight) ||
                (source == edge.destination && destination == edge.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }
}
