package org.suai.graphAlgorithms.service.implementations.algorithmsCalculation;

import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.DfsGraph;
import org.suai.graphAlgorithms.service.implementations.base.GraphCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;
import org.suai.graphGeneration.model.graphGenerated.GeneratedGraphElement;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;

@Service
public class DfsGraphCalculatorService extends GraphCalculatorService implements IGraphCalculatorService{

    private final Random random = new Random();

    @Override
    public String makeAlgorithmCalculation(Graph graph) {
        DfsGraph sourceGraph = (DfsGraph) graph;
        StringBuilder trackLogger = new StringBuilder();
        //Integer start = random.nextInt(sourceGraph.getAmountOfVertex());
        trackLogger.append("\nНачало алгоритма DFS. Стартовая вершина - ").append("0").append("\n");

        this.dfs(sourceGraph, 0, trackLogger);

        return trackLogger.toString();
    }

    @Override
    public Boolean isGraphFullyConnected(Graph graph) {
        DfsGraph sourceGraph = (DfsGraph) graph;
        Integer start = random.nextInt(sourceGraph.getAmountOfVertex());
        StringBuilder stringBuilder = new StringBuilder();

        Boolean isFullyConnectedGraph = this.dfs(sourceGraph, start, stringBuilder);
        return isFullyConnectedGraph;
    }

    private Boolean dfs(DfsGraph graph, Integer start, StringBuilder trackLogger){
        boolean[] visited = new boolean[graph.getAmountOfVertex()];
        Deque<Integer> stack = new ArrayDeque<>();
        int iteration = 1;
        boolean firstNeighborAtIteration = false;
        boolean atLeastOneNotVisitedNeighbor = false;

        visited[start] = true;
        stack.addFirst(start);
        while (!stack.isEmpty()){
            // Извлекаем вершину из стэка и добавляем в ход обхода
            int node = stack.removeFirst();

            // Получаем все смежные вершины текущей вершины
            List<Integer> neighbors = graph.getAdjacencyList().get(node).stream()
                    .map(GeneratedGraphElement::getVertex)
                    .toList();
            trackLogger.append("Шаг ").append(iteration).append(": Текущая вершина - ").append(node).append("; ");
            if (!neighbors.isEmpty()) {
                for (int n : neighbors) {
                    if (!visited[n]) {
                        if(!firstNeighborAtIteration) {
                            trackLogger.append("Не посещенные соседи - ").append(n);
                            firstNeighborAtIteration = true;
                        }
                        else
                            trackLogger.append(", ").append(n);
                        visited[n] = true;
                        stack.addFirst(n);
                        atLeastOneNotVisitedNeighbor = true;
                    }
                }
                if(atLeastOneNotVisitedNeighbor)
                    trackLogger.append(";");
                else
                    trackLogger.append("Не посещенных соседей нет;");
            }
            else{
                trackLogger.append("Изолированная вершина. Граф не подходит");
                return false;
            }
            firstNeighborAtIteration = false;
            atLeastOneNotVisitedNeighbor = false;
            iteration++;
            trackLogger.append("\n");
        }
        for (boolean v : visited)
            if (!v) return false; // if at least one vertex wasn't reached, then it's not fully connected graph

        return true;
    }
}

