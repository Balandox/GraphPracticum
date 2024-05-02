package org.suai.graphAlgorithms.service.implementations.algorithmsCalculation;

import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.dijkstra.DijkstraGraph;
import org.suai.graphAlgorithms.model.dijkstra.Pair;
import org.suai.graphAlgorithms.model.kruskal.Edge;
import org.suai.graphAlgorithms.service.implementations.base.GraphCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

@Service
public class DijkstraGraphCalculatorService extends GraphCalculatorService implements IGraphCalculatorService {

    private final Random random = new Random();

    @Override
    public String makeAlgorithmCalculation(Graph graph) {
        DijkstraGraph sourceGraph = (DijkstraGraph) graph;
        System.out.println(sourceGraph);

        StringBuilder trackLogger = new StringBuilder();
        Integer start = random.nextInt(sourceGraph.getAmountOfVertex());
        trackLogger.append("\nНачало алгоритма Дейкстры. Исходная вершина - ").append(start).append("\n");

        this.dijkstra(sourceGraph, start, trackLogger);

        return trackLogger.toString();
    }

    private void dijkstra(DijkstraGraph graph, Integer src, StringBuilder trackLogger){
        boolean[] visited = new boolean[graph.getAmountOfVertex()];
        List<Edge>[] edgeList = graph.getGraph();
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(src, src + "", 0));
        int iteration = 1;
        boolean firstNeighborOnIteration = true;
        boolean atLeastOneNeighbor = false;

        while (!pq.isEmpty()) {
            // Remove the top vertex or the lowest path endpoint
            Pair rem = pq.remove();

            if (visited[rem.getVertex()])
                continue;
            trackLogger.append("\nШаг ").append(iteration++).append(": Текущая вершина - ").append(rem.getVertex()).append("; ");
            // If the vertex is not visited, mark it visited as
            // the shortest path to the vertex is found.
            visited[rem.getVertex()] = true;

            // For each adjacent node of the vertex,
            // Relax the distance to the adjacent edge
            for (Edge e : edgeList[rem.getVertex()]) {
                if (!visited[e.getDestination()]) {
                    if(firstNeighborOnIteration){
                        trackLogger.append("Ребра, исходящие в не посещенных соседей - ");
                        firstNeighborOnIteration = false;
                    }
                    else
                        trackLogger.append(", ");
                    trackLogger.append(rem.getVertex()).append("->").append(e.getDestination()).append("(").append(e.getWeight()).append(")");
                    atLeastOneNeighbor = true;
                    pq.add(new Pair(e.getDestination(), rem.getPathToSource() + "->" + e.getDestination(), rem.getPathWeight() + e.getWeight()));
                }
            }
            if(!atLeastOneNeighbor)
                trackLogger.append("Не посещенных соседей нет");
            trackLogger.append("\nОтмечаем вершину посещенной, кратчайший путь найден: ").append(rem.getPathToSource()).append(". Стоимость пути = ").append(rem.getPathWeight()).append("\n");
            atLeastOneNeighbor = false;
            firstNeighborOnIteration = true;
        }
    }

    @Override
    public Boolean isGraphFullyConnected(Graph graph) {
        return null;
    }
}
