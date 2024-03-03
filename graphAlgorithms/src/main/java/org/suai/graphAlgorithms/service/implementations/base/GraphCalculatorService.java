package org.suai.graphAlgorithms.service.implementations.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.service.interfaces.IGraphBaseCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorMapperService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;

@Service
public class GraphCalculatorService implements IGraphBaseCalculatorService {

    @Autowired
    IGraphCalculatorMapperService graphCalculatorMapperService;

    @Override
    public String calculate(Graph graph) {
        String solutionLogs = mapAndMakeCalculation(graph);
        return solutionLogs;
    }

    private String mapAndMakeCalculation(Graph graph){
        IGraphCalculatorService calculatorService = graphCalculatorMapperService.getGraphCalculatorService(graph);
        return calculatorService.makeAlgorithmCalculation(graph);
    }

}