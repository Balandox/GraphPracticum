package org.suai.graphGeneration.model.graphGenerated;

import org.suai.graphGeneration.model.baseGraph.Graph;

import java.util.List;

public class AdjacencyListGraph extends Graph {

    private Integer amountOfVertex;

    private Integer amountOfEdges;

    private List<List<GeneratedGraphElement>> adjacencyList;

    public AdjacencyListGraph(Integer amountOfVertex, Integer amountOfEdges, List<List<GeneratedGraphElement>> adjacencyList) {
        this.amountOfVertex = amountOfVertex;
        this.amountOfEdges = amountOfEdges;
        this.adjacencyList = adjacencyList;
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

    public List<List<GeneratedGraphElement>> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(List<List<GeneratedGraphElement>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }
}
