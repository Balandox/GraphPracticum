package org.suai.graphAlgorithms.service.implementations.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.model.BiconnectedComponentsGraph;
import org.suai.graphAlgorithms.model.DfsGraph;
import org.suai.graphAlgorithms.model.PrimaGraph;
import org.suai.graphAlgorithms.model.dijkstra.DijkstraGraph;
import org.suai.graphAlgorithms.model.kruskal.KruskalGraph;
import org.suai.graphAlgorithms.service.implementations.algorithmsCalculation.*;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorMapperService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;

@Service
public class GraphCalculatorMapperService implements IGraphCalculatorMapperService {

    @Autowired
    private BfsGraphCalculatorService bfsGraphCalculatorService;

    @Autowired
    private PrimaGraphCalculatorService primaGraphCalculatorService;

    @Autowired
    private DfsGraphCalculatorService dfsGraphCalculatorService;

    @Autowired
    private KruskalGraphCalculatorService kruskalGraphCalculatorService;

    @Autowired
    private DijkstraGraphCalculatorService dijkstraGraphCalculatorService;

    @Autowired
    private BiconnectedComponentsGraphCalculatorService biconnectedComponentsGraphCalculatorService;

    @Override
    public IGraphCalculatorService getGraphCalculatorService(Graph graph) {
        IGraphCalculatorService graphCalculator = null;
        if(graph instanceof BfsGraph)
            graphCalculator = bfsGraphCalculatorService;
        else if(graph instanceof PrimaGraph)
            graphCalculator = primaGraphCalculatorService;
        else if(graph instanceof DfsGraph)
            graphCalculator = dfsGraphCalculatorService;
        else if(graph instanceof KruskalGraph)
            graphCalculator = kruskalGraphCalculatorService;
        else if(graph instanceof DijkstraGraph)
            graphCalculator = dijkstraGraphCalculatorService;
        else if(graph instanceof BiconnectedComponentsGraph)
            graphCalculator = biconnectedComponentsGraphCalculatorService;

        return graphCalculator;
    }
}
