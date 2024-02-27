package org.suai.graphAlgorithms.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorMapperService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;

@Service
public class GraphCalculatorMapperService implements IGraphCalculatorMapperService {

    @Autowired
    private BfsGraphCalculatorService bfsGraphCalculatorService;

    @Override
    public IGraphCalculatorService getGraphCalculatorService(Graph graph) {
        IGraphCalculatorService graphCalculator = null;
        if(graph instanceof BfsGraph)
            graphCalculator = bfsGraphCalculatorService;

        return graphCalculator;
    }
}
