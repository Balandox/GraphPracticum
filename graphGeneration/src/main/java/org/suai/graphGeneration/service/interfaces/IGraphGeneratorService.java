package org.suai.graphGeneration.service.interfaces;

import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;

public interface IGraphGeneratorService {

    public AdjacencyListGraph generateAdjacencyListGraph(int amountOfVertex, boolean withWeights, int maxWeight);

    public AdjacencyListGraph generateAdjacencyMatrixGraph(int amountOfVertex, boolean withWeights, int maxWeight);

    public void printAdjacencyListGraph(AdjacencyListGraph adjacencyListGraph);

    public void printAdjacencyMatrixGraph(AdjacencyListGraph adjacencyListGraph, int maxWeight);

}
