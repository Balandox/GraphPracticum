package org.suai.graphAlgorithms.model.kruskal;

import org.suai.graphGeneration.model.baseGraph.Graph;

import java.util.List;

public class KruskalGraph extends Graph {

    protected Integer amountOfVertex;

    protected Integer amountOfEdges;

    protected List<Edge> edges;

    public KruskalGraph(Integer amountOfVertex, Integer amountOfEdges, List<Edge> edges) {
        this.amountOfVertex = amountOfVertex;
        this.amountOfEdges = amountOfEdges;
        this.edges = edges;
    }

    public KruskalGraph(Integer amountOfVertex, Integer amountOfEdges) {
        this.amountOfVertex = amountOfVertex;
        this.amountOfEdges = amountOfEdges;
    }

    public Integer getAmountOfVertex() {
        return amountOfVertex;
    }

    public void setAmountOfVertex(Integer amountOfVertex) {
        this.amountOfVertex = amountOfVertex;
    }

    public Integer getAmountOfEdges() {
        return amountOfEdges;
    }

    public void setAmountOfEdges(Integer amountOfEdges) {
        this.amountOfEdges = amountOfEdges;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public String toString() {
        return "KruskalGraph{" +
                "amountOfVertex=" + amountOfVertex +
                ", amountOfEdges=" + amountOfEdges +
                ", edges=" + edges +
                '}';
    }
}
