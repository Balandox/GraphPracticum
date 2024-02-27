package org.suai.graphAlgorithms.service.implementations;

import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;

@Service
public class BfsGraphCalculatorService extends GraphCalculatorService implements IGraphCalculatorService {

    @Override
    public void makeAlgorithmCalculation(Graph graph) {
        BfsGraph sourceGraph = (BfsGraph) graph;
    }

}
