package org.suai.graphAlgorithms.service.implementations.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.model.PrimaGraph;
import org.suai.graphAlgorithms.service.implementations.algorithmsCalculation.BfsGraphCalculatorService;
import org.suai.graphAlgorithms.service.implementations.algorithmsCalculation.PrimaGraphCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorMapperService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;

@Service
public class GraphCalculatorMapperService implements IGraphCalculatorMapperService {

    @Autowired
    private BfsGraphCalculatorService bfsGraphCalculatorService;

    @Autowired
    private PrimaGraphCalculatorService primaGraphCalculatorService;

    @Override
    public IGraphCalculatorService getGraphCalculatorService(Graph graph) {
        IGraphCalculatorService graphCalculator = null;
        if(graph instanceof BfsGraph)
            graphCalculator = bfsGraphCalculatorService;
        else if(graph instanceof PrimaGraph)
            graphCalculator = primaGraphCalculatorService;

        return graphCalculator;
    }
}
