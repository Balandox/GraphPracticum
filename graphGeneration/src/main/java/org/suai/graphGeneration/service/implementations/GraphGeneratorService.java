package org.suai.graphGeneration.service.implementations;

import org.springframework.stereotype.Service;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.model.graphGenerated.GeneratedGraphElement;
import org.suai.graphGeneration.service.interfaces.IGraphGeneratorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GraphGeneratorService implements IGraphGeneratorService {

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
        AdjacencyListGraph resultGraph = new AdjacencyListGraph(amountOfVertex, amountOfEdges, adjacencyList);
        return resultGraph;
    }

    @Override
    public AdjacencyListGraph generateAdjacencyMatrixGraph(int amountOfVertex, boolean withWeights, int maxWeight) {
        // generation based on an adjacency list, but graph shows on screen like adjacency matrix
        AdjacencyListGraph resultGraph = this.generateAdjacencyListGraph(amountOfVertex, withWeights, maxWeight);
        return resultGraph;
    }

    int computeMaxEdges(int numOfVertices) {
        return numOfVertices * ((numOfVertices - 1) / 2);
    }

    public void printAdjacencyListGraph(AdjacencyListGraph adjacencyListGraph){
        List<List<GeneratedGraphElement>> graph = adjacencyListGraph.getAdjacencyList();
        for (int i = 0; i < graph.size(); i++) {
            System.out.print("Вершина " + i + " -> { ");
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

    public void printAdjacencyMatrixGraph(AdjacencyListGraph adjacencyListGraph, int maxWeight){
        List<List<GeneratedGraphElement>> graph = adjacencyListGraph.getAdjacencyList();
        int lengthOfElem = calculateMaxStringLengthOfMatrixElem(adjacencyListGraph.getAmountOfVertex(), maxWeight);

        for(int i = 0; i < graph.size(); i++)
            printMatrixRow(graph.get(i), adjacencyListGraph.getAmountOfVertex(), lengthOfElem, i);
    }

    private void printMatrixRow(List<GeneratedGraphElement> row, int amountOfVertex, int maxLengthOfElem, int currentRow){
        String format = "%" + maxLengthOfElem + "s\t";
        if(row.isEmpty()){
            for(int i = 0; i < amountOfVertex; i++)
                System.out.printf(format, "0");
            System.out.println();
            return;
        }

        for(int i = 0; i < amountOfVertex; i++){
            GeneratedGraphElement tmp = new GeneratedGraphElement(i);
            int tmpIndex = row.indexOf(tmp);
            if(tmpIndex != -1){
                GeneratedGraphElement elemToPrint = row.get(tmpIndex);
                if(elemToPrint.getWeight() != null)
                    System.out.printf(format, "1(" + elemToPrint.getWeight() + ")");
                else
                    System.out.printf(format, "1");
            }
            else
                System.out.printf(format, "0");
        }
        System.out.println();
    }

    private int calculateMaxStringLengthOfMatrixElem(int amountOfVertex, int maxWeight){
        int lengthForVertex = countDigits(amountOfVertex);
        int lengthForWeight = 0;
        if(maxWeight != 0)
             lengthForWeight = countDigits(maxWeight);
        return lengthForVertex + lengthForWeight + 5;
    }

    private int countDigits(int number) {
        if (number == 0)
            return 1;
        return (int) (Math.log10(Math.abs(number)) + 1);
    }

}
