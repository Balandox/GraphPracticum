package org.suai.graphAlgorithms.service.implementations;

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
    public void calculate(Graph graph) {
        mapAndMakeCalculation(graph);
    }

    private void mapAndMakeCalculation(Graph graph){
        IGraphCalculatorService calculatorService = graphCalculatorMapperService.getGraphCalculatorService(graph);
        calculatorService.makeAlgorithmCalculation(graph);
    }

}