package org.suai.graphAlgorithms.model;

import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;

public class TopologicalSortGraph extends AdjacencyListGraph {
    public TopologicalSortGraph(AdjacencyListGraph source){
        super(source.getAmountOfVertex(), source.getAmountOfEdges(), source.getAdjacencyList());
    }
}
