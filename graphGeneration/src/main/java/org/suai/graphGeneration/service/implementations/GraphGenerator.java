package org.suai.graphGeneration.service.implementations;

import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.model.graphGenerated.AdjencencyMatrixGraph;
import org.suai.graphGeneration.model.graphGenerated.GeneratedGraphElement;
import org.suai.graphGeneration.service.interfaces.IGraphGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphGenerator implements IGraphGenerator {

    private final Random random = new Random();

    @Override
    public AdjacencyListGraph generateAdjacencyListGraph(int amountOfVertex, boolean withWeights, int maxWeight) {
        int amountOfEdges = random.nextInt(computeMaxEdges(amountOfVertex)) + 1;
        List<List<GeneratedGraphElement>> adjacencyList = new ArrayList<>(amountOfVertex);
        for (int i = 0; i < amountOfVertex; i++)
            adjacencyList.add(new ArrayList<>());

        for (int i = 0; i < amountOfEdges; i++) {
            GeneratedGraphElement source;
            GeneratedGraphElement destination;
            if(withWeights){
                int weight = random.nextInt(maxWeight) + 1;
                source = new GeneratedGraphElement(random.nextInt(amountOfVertex), weight);
                destination = new GeneratedGraphElement(random.nextInt(amountOfVertex), weight);
            }
            else {
                source = new GeneratedGraphElement(random.nextInt(amountOfVertex));
                destination = new GeneratedGraphElement(random.nextInt(amountOfVertex));
            }
            if (!source.equals(destination) && !adjacencyList.get(source.getVertex()).contains(destination)) {
                adjacencyList.get(source.getVertex()).add(destination);
                adjacencyList.get(destination.getVertex()).add(source);
            }
        }
        printAdjacencyListGraph(adjacencyList);
        return new AdjacencyListGraph(amountOfVertex, amountOfEdges, adjacencyList);
    }

    @Override
    public AdjencencyMatrixGraph generateAdjencencyMatrixGraph(int amountOfVertex, boolean withWeights, int maxWeight) {
        return null;
    }

    int computeMaxEdges(int numOfVertices) {
        return numOfVertices * ((numOfVertices - 1) / 2);
    }

    public void printAdjacencyListGraph(List<List<GeneratedGraphElement>> graph){
        for (int i = 0; i < graph.size(); i++) {
            System.out.print("Vertex " + i + " -> { ");
            for (int j = 0; j < graph.get(i).size(); j++) {
                GeneratedGraphElement current = graph.get(i).get(j);
                if(j + 1 == graph.get(i).size())
                    System.out.print(current);
                else
                    System.out.print(current + ", ");
            }
            System.out.println(" }");
        }
    }


}
