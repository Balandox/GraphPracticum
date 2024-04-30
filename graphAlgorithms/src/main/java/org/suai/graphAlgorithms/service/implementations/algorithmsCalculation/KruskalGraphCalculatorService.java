package org.suai.graphAlgorithms.service.implementations.algorithmsCalculation;

import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.PrimaGraph;
import org.suai.graphAlgorithms.model.kruskal.KruskalGraph;
import org.suai.graphAlgorithms.service.implementations.base.GraphCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;

@Service
public class KruskalGraphCalculatorService extends GraphCalculatorService implements IGraphCalculatorService {

    @Override
    public String makeAlgorithmCalculation(Graph graph) {
        KruskalGraph sourceGraph = (KruskalGraph) graph;
        System.out.println(sourceGraph.getAmountOfVertex() + " V");
        System.out.println(sourceGraph.getAmountOfEdges() + " E");
        System.out.println(sourceGraph.getEdges());
        StringBuilder trackLogger = new StringBuilder();
        trackLogger.append("\nНачало алгоритма Крускала. Стартовая вершина - ").append(0).append("\n");

        //this.primMST(sourceGraph, trackLogger);

        return trackLogger.toString();
    }

    @Override
    public Boolean isGraphFullyConnected(Graph graph) {
        return null;
    }
}
