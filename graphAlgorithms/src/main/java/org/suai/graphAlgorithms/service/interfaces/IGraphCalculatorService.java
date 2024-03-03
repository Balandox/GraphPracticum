package org.suai.graphAlgorithms.service.interfaces;

import org.suai.graphGeneration.model.baseGraph.Graph;

public interface IGraphCalculatorService {

    String makeAlgorithmCalculation(Graph graph); // calculate algorithm and return steps of solution as String

    Boolean isGraphFullyConnected(Graph graph); // check that graph is fully connected, this method have implementation only in
    // BFS and DFS services

}
