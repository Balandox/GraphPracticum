package org.suai.graphAlgorithms.model.dijkstra;

import org.suai.graphAlgorithms.model.kruskal.Edge;
import org.suai.graphGeneration.model.baseGraph.Graph;

import java.util.*;

public class DijkstraGraph extends Graph {

    protected Integer amountOfVertex;

    protected Integer amountOfEdges;

    protected List<Edge>[] graph;

    public DijkstraGraph(Integer amountOfVertex, Integer amountOfEdges) {
        this.amountOfVertex = amountOfVertex;
        this.amountOfEdges = amountOfEdges;
    }

    public DijkstraGraph(){}

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

    public List<Edge>[] getGraph() {
        return graph;
    }

    public void setGraph(List<Edge>[] graph) {
        this.graph = graph;
    }

    @Override
    public String toString() {
        return "DijkstraGraph{" +
                "amountOfVertex=" + amountOfVertex +
                ", amountOfEdges=" + amountOfEdges +
                ", graph=" + Arrays.toString(graph) +
                '}';
    }
}
