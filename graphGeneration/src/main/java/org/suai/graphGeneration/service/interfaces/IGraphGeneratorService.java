package org.suai.graphGeneration.service.interfaces;

import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;

public interface IGraphGeneratorService {

    public AdjacencyListGraph generateAdjacencyListGraph(int amountOfVertex, boolean withWeights, int maxWeight);

    public AdjacencyListGraph generateAdjacencyMatrixGraph(int amountOfVertex, boolean withWeights, int maxWeight);

    AdjacencyListGraph generateAcyclicDirectedGraph(int amountOfVertex, boolean withWeights, int maxWeight);

    public String printAdjacencyListGraph(AdjacencyListGraph adjacencyListGraph);

    public String printAdjacencyMatrixGraph(AdjacencyListGraph adjacencyListGraph, int maxWeight);

}
