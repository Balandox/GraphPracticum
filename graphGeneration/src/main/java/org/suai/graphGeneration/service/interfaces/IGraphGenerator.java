package org.suai.graphGeneration.service.interfaces;

import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.model.graphGenerated.AdjencencyMatrixGraph;
import org.suai.graphGeneration.model.graphGenerated.GeneratedGraphElement;

import java.util.List;

public interface IGraphGenerator {

    public AdjacencyListGraph generateAdjacencyListGraph(int amountOfVertex, boolean withWeights, int maxWeight);

    public AdjencencyMatrixGraph generateAdjencencyMatrixGraph(int amountOfVertex, boolean withWeights, int maxWeight);

    public void printAdjacencyListGraph(List<List<GeneratedGraphElement>> graph);

}
