package org.suai.graphAlgorithms.model;

import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;

public class DfsGraph extends AdjacencyListGraph {

    public DfsGraph(AdjacencyListGraph source){
        super(source.getAmountOfVertex(), source.getAmountOfEdges(), source.getAdjacencyList());
    }

}
