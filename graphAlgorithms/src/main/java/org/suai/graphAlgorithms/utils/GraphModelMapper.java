package org.suai.graphAlgorithms.utils;

import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;

public class GraphModelMapper {

    public static BfsGraph convertGeneratedGraphToBfsGraph(AdjacencyListGraph sourceGraph) {
        return new BfsGraph(sourceGraph);
    }

}
