package org.suai.graphAlgorithms.model;

import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;

public class BfsGraph extends AdjacencyListGraph {

    public BfsGraph(AdjacencyListGraph source){
        super(source.getAmountOfVertex(), source.getAmountOfEdges(), source.getAdjacencyList());
    }


}
