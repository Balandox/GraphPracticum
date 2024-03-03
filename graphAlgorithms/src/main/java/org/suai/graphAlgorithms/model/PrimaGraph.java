package org.suai.graphAlgorithms.model;

import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;

public class PrimaGraph extends AdjacencyListGraph{

    public PrimaGraph(AdjacencyListGraph source){
        super(source.getAmountOfVertex(), source.getAmountOfEdges(), source.getAdjacencyList());
    }

}