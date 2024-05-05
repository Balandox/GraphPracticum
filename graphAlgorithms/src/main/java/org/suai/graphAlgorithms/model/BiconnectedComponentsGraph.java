package org.suai.graphAlgorithms.model;

import org.suai.graphGeneration.model.baseGraph.Graph;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;

import java.util.LinkedList;

public class BiconnectedComponentsGraph extends AdjacencyListGraph {

    static int count, time = 0;

    public BiconnectedComponentsGraph(AdjacencyListGraph source){
        super(source.getAmountOfVertex(), source.getAmountOfEdges(), source.getAdjacencyList());
    }

}
