package org.suai.graphAlgorithms.model.dijkstra;

public class Pair implements Comparable<Pair> {

    private int vertex; // The vertex
    private String pathToSource; // The path from source node
    private int pathWeight; // THe weight of the path

    public Pair(int vertex, String pathToSource, int pathWeight) {
        this.vertex = vertex;
        this.pathToSource = pathToSource;
        this.pathWeight = pathWeight;
    }

    public Pair(){}

    @Override
    public int compareTo(Pair p) {
        return this.pathWeight - p.pathWeight;
    }

    public int getVertex() {
        return vertex;
    }

    public void setVertex(int vertex) {
        this.vertex = vertex;
    }

    public String getPathToSource() {
        return pathToSource;
    }

    public void setPathToSource(String pathToSource) {
        this.pathToSource = pathToSource;
    }

    public int getPathWeight() {
        return pathWeight;
    }

    public void setPathWeight(int pathWeight) {
        this.pathWeight = pathWeight;
    }
}
