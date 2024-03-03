package org.suai.graphAlgorithms.service.implementations.algorithmsCalculation;

import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.PrimaGraph;
import org.suai.graphAlgorithms.service.implementations.base.GraphCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;
import org.suai.graphGeneration.model.graphGenerated.GeneratedGraphElement;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

@Service
public class PrimaGraphCalculatorService extends GraphCalculatorService implements IGraphCalculatorService {

    private final Random random = new Random();

    @Override
    public String makeAlgorithmCalculation(Graph graph) {
        PrimaGraph sourceGraph = (PrimaGraph) graph;
        StringBuilder trackLogger = new StringBuilder();
        trackLogger.append("\nНачало алгоритма Прима. Стартовая вершина - ").append(0).append("\n");

        this.primMST(sourceGraph, trackLogger);

        return trackLogger.toString();
    }

    // MST - minimum spanning tree
    void primMST(PrimaGraph graph, StringBuilder trackLogger) {
        // TODO понять как правильно трекать ход решения
        int amountOfVertex = graph.getAmountOfVertex();
        List<List<GeneratedGraphElement>> adj = graph.getAdjacencyList();
        int[] parent = new int[amountOfVertex];
        int[] key = new int[amountOfVertex];
        boolean[] inMST = new boolean[amountOfVertex];
        int iteration = 1;

        // key value - минимальный вес ребра исходящий из данной вершины
        // parent[3] = 1
        // key[3] = 5 - Из вершины номер 3 исходит ребро с минимальной стоимостью 5, которое ведет в вершину 1 (1 - родитель 3 в MST)
        for (int i = 0; i < amountOfVertex; i++) {
            parent[i] = -1;          // Array to store the parent node of each vertex in the MST
            key[i] = Integer.MAX_VALUE;  // Array to store the minimum edge weight value for each vertex
            inMST[i] = false;        // Array to track if the vertex is in the MST or not
        }

        PriorityQueue<GeneratedGraphElement> minHeap = new PriorityQueue<>(Comparator.comparingInt(GeneratedGraphElement::getWeight));

        key[0] = 0; // Start the MST from vertex 0
        minHeap.add(new GeneratedGraphElement(0, key[0]));

        while (!minHeap.isEmpty()) {
            GeneratedGraphElement cur = minHeap.poll();    //Получаем из приоритетной очереди ту вершину, которая имеет минимальное ребро
            int curVertex = cur.getVertex();               // в любую вершину, которая еще не находится в MST
            inMST[curVertex] = true;

            if(!isAllVertexesInMST(inMST) || minHeap.isEmpty())
                trackLogger.append("Шаг ").append(iteration).append(": Текущие вершины в MST - ").append(getCurrentMSTStateAsString(inMST)).append("\n");

            for (GeneratedGraphElement v : adj.get(curVertex)) { // Проходимся по списку смежных вершин и для каждой из них
                int neighborVertex = v.getVertex();
                int weight = v.getWeight();
                // Если neighborVertex еще не включена в MST и вес curVertex -> neighborVertex меньше, чем текущее минимальное ребро, исходящее из neighborVertex,
                // то обновляем его и делаем curVertex - родителем
                if (!inMST[neighborVertex] && weight < key[neighborVertex]) {
                    parent[neighborVertex] = curVertex;
                    key[neighborVertex] = weight;
                    minHeap.add(new GeneratedGraphElement(neighborVertex, key[neighborVertex]));
                }
            }
            iteration++;
        }

        addMSTResultToLogger(parent, trackLogger);
    }

    // Function to print the edges of the Minimum Spanning Tree
    void addMSTResultToLogger(int[] parent, StringBuilder trackLogger) {
        trackLogger.append("\nСписок ребер в MST:\n");
        for (int i = 1; i < parent.length; i++)
            trackLogger.append(parent[i]).append(" <-> ").append(i).append("\n");
    }

    private String getCurrentMSTStateAsString(boolean[] inMST){
        StringBuilder result = new StringBuilder();
        boolean isFirstElem = true;

        for(int i = 0; i < inMST.length; i++){
            if(inMST[i]) {
                if(isFirstElem){
                    result.append(i);
                    isFirstElem = false;
                }
                else
                    result.append(", ").append(i);
            }
        }
        result.append("; ");
        return result.toString();
    }

    private boolean isAllVertexesInMST(boolean[] inMST){
        for(boolean cur : inMST)
            if(!cur)
                return false;
        return true;
    }

    @Override
    public Boolean isGraphFullyConnected(Graph graph) {
        return null;
    }
}

