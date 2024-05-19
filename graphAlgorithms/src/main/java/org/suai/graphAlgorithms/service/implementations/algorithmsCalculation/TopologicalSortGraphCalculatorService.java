package org.suai.graphAlgorithms.service.implementations.algorithmsCalculation;

import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.TopologicalSortGraph;
import org.suai.graphAlgorithms.service.implementations.base.GraphCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;
import org.suai.graphGeneration.model.graphGenerated.GeneratedGraphElement;

import java.util.List;
import java.util.Stack;

@Service
public class TopologicalSortGraphCalculatorService extends GraphCalculatorService implements IGraphCalculatorService {

    private static int iteration = 1;

    @Override
    public String makeAlgorithmCalculation(Graph graph) {
        TopologicalSortGraph sourceGraph = (TopologicalSortGraph) graph;
        StringBuilder trackLogger = new StringBuilder();
        trackLogger.append("\nНачало Топологической сортировки. Стартовая вершина - 0");

        this.topologicalSort(sourceGraph, trackLogger);

        return trackLogger.toString();
    }

    private void topologicalSort(TopologicalSortGraph graph, StringBuilder trackLogger){
        int amountOfVertex = graph.getAmountOfVertex();
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[amountOfVertex];
        for (int i = 0; i < amountOfVertex; i++)
            if (!visited[i])
                topologicalSortUtil(i, graph.getAdjacencyList(), visited, stack, trackLogger);

        trackLogger.append("\n\nРезультат топологической сортировки: ");
        while (!stack.empty()) {
            trackLogger.append(stack.pop()).append("->");
        }
        trackLogger.setLength(trackLogger.length() - 2);
        trackLogger.append("\n");
        iteration = 1;
    }

    private void topologicalSortUtil(int curV, List<List<GeneratedGraphElement>> adjList, boolean[] visited, Stack<Integer> stack, StringBuilder trackLogger){
        visited[curV] = true;
        trackLogger.append("\nШаг ").append(iteration++).append(": Текущая вершина - ").append(curV).append(";");
        for (GeneratedGraphElement elem : adjList.get(curV)) {
            int neighbor = elem.getVertex();
            if (!visited[neighbor]) {
                topologicalSortUtil(neighbor, adjList, visited, stack, trackLogger);
            }
        }
        trackLogger.append("\nНе посещенных соседей для вершины ").append(curV).append(" нет; Добавляем ее в стэк");
        stack.push(curV);
    }


    @Override
    public Boolean isGraphFullyConnected(Graph graph) {
        return null;
    }
}
