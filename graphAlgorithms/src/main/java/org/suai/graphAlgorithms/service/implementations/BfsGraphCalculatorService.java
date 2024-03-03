package org.suai.graphAlgorithms.service.implementations;

import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;
import org.suai.graphGeneration.model.graphGenerated.GeneratedGraphElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BfsGraphCalculatorService extends GraphCalculatorService implements IGraphCalculatorService {

    //TODO обработать ситуацию с изолированными подграфами в одном общем графе
    private final Random random = new Random();

    @Override
    public String makeAlgorithmCalculation(Graph graph) {
        BfsGraph sourceGraph = (BfsGraph) graph;
        StringBuilder trackLogger = new StringBuilder();
        Integer start = random.nextInt(sourceGraph.getAmountOfVertex());
        trackLogger.append("\nНачало алгоритма BFS. Стартовая вершина - ").append(start).append("\n");

        this.bfs(sourceGraph, start, trackLogger);

        return trackLogger.toString();
    }

    @Override
    public Boolean isGraphFullyConnected(Graph graph) {
        BfsGraph sourceGraph = (BfsGraph) graph;
        Integer start = random.nextInt(sourceGraph.getAmountOfVertex());
        StringBuilder stringBuilder = new StringBuilder();

        Boolean isFullyConnectedGraph = this.bfs(sourceGraph, start, stringBuilder);
        return isFullyConnectedGraph;
    }

    // common for checking fully graph connection and for track saving
    private Boolean bfs(BfsGraph graph, Integer start, StringBuilder trackLogger){
        boolean[] visited = new boolean[graph.getAmountOfVertex()];
        LinkedList<Integer> queue = new LinkedList<>();
        int iteration = 1;
        boolean firstNeighborAtIteration = false;
        boolean atLeastOneNotVisitedNeighbor = false;

        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            // Извлекаем вершину из очереди и добавляем в ход обхода
             int node = queue.poll();

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
                        queue.add(n);
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
/*                int nextVertex = random.nextInt(graph.getAmountOfEdges());
                while(visited[nextVertex]){
                    nextVertex = random.nextInt(graph.getAmountOfEdges());
                }
                queue.add(nextVertex);
                visited[nextVertex] = true;*/
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
