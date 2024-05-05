package org.suai.graphAlgorithms.service.implementations.algorithmsCalculation;

import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.model.BiconnectedComponentsGraph;
import org.suai.graphAlgorithms.service.implementations.base.GraphCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphBaseCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;

public class BiconnectedComponentsGraphCalculatorService extends GraphCalculatorService implements IGraphCalculatorService {

    @Override
    public String makeAlgorithmCalculation(Graph graph) {
        BiconnectedComponentsGraph sourceGraph = (BiconnectedComponentsGraph) graph;
        StringBuilder trackLogger = new StringBuilder();



        return trackLogger.toString();
    }

    @Override
    public Boolean isGraphFullyConnected(Graph graph) {
        return null;
    }


}
