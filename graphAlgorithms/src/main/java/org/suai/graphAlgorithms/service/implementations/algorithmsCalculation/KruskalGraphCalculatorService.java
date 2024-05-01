package org.suai.graphAlgorithms.service.implementations.algorithmsCalculation;

import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.PrimaGraph;
import org.suai.graphAlgorithms.model.kruskal.Edge;
import org.suai.graphAlgorithms.model.kruskal.KruskalGraph;
import org.suai.graphAlgorithms.model.kruskal.Subset;
import org.suai.graphAlgorithms.service.implementations.base.GraphCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;

import java.util.List;

@Service
public class KruskalGraphCalculatorService extends GraphCalculatorService implements IGraphCalculatorService {

    @Override
    public String makeAlgorithmCalculation(Graph graph) {
        KruskalGraph sourceGraph = (KruskalGraph) graph;
        StringBuilder trackLogger = new StringBuilder();
        trackLogger.append("\nНачало алгоритма Крускала\n");

        this.kruskalMST(sourceGraph, trackLogger);

        return trackLogger.toString();
    }

    private void kruskalMST(KruskalGraph graph, StringBuilder trackLogger){
        List<Edge> edgeLists = graph.getEdges();
        Integer amountOfVertex = graph.getAmountOfVertex();

        Edge[] finalResult = new Edge[amountOfVertex];
        Subset[] subsetArray = new Subset[amountOfVertex];
        for(int i = 0; i < amountOfVertex; i++){
            finalResult[i] = new Edge();
            subsetArray[i] = new Subset(i, 0); // Create V subsets with single elements
        }

        int iteration = 0;
        trackLogger.append("Шаг ").append(iteration++).append(" (Сортируем ребра графа по возрастанию веса). Список ребер после сортировки:\n");
        edgeLists.sort(Edge::compareTo);
        for(Edge edge : edgeLists)
            trackLogger.append(edge.getSource()).append(" <-> ").append(edge.getDestination()).append("; Вес = ").append(edge.getWeight()).append("\n");
        trackLogger.append("\n");

        int index = 0;
        int newEdge = 0;
        // use for loop to pick the smallers edge from the edges and increment the index for next iteration
        while (newEdge < amountOfVertex - 1) {
            // create an instance of Edge for next edge
            Edge nextEdge = edgeLists.get(index++);

            int nextSource = findSetOfElement(subsetArray, nextEdge.getSource()); // находим подмножество (его родителя), которому принадлежит исходная вершина ребра
            int nextDestination = findSetOfElement(subsetArray, nextEdge.getDestination()); // находим подмножество (его родителя), которому принадлежит конечная вершина ребра

            // если родители двух разных множеств, которых мы хотим соединить ребром не равны, то два этих множества не образуют цикл. Можно соединять
            if (nextSource != nextDestination) {
                finalResult[newEdge++] = nextEdge;
                String currentMSTState = getCurrentMSTStateAsString(finalResult, newEdge);
                trackLogger.append("Шаг ").append(iteration++).append(": Текущие ребра в MST - ").append(currentMSTState).append("\n");
                performUnion(subsetArray, nextSource, nextDestination);
            }
        }
        for (index = 0; index < newEdge; index++)
            System.out.println(finalResult[index].getSource() + " - " + finalResult[index].getDestination() + ": " + finalResult[index].getWeight());
        addMSTResultToLogger(finalResult, trackLogger);
    }

    // find the set to which vertex is belongs
    private int findSetOfElement(Subset[] subsetArray, int i) {
        if (subsetArray[i].getParent() != i) // Является ли элемент корневым элементов этого множества?
            subsetArray[i].setParent(findSetOfElement(subsetArray, subsetArray[i].getParent())); // если нет, то идем вверх по дереву и ищем родителя данного подмножества
        return subsetArray[i].getParent();
    }

    // create performUnion() method to perform union of two sets
    private void performUnion(Subset[] subsetArray, int sourceRoot, int destinationRoot) {

        int nextSourceRoot = findSetOfElement(subsetArray, sourceRoot);
        int nextDestinationRoot = findSetOfElement(subsetArray, destinationRoot);

        // на основе сравнения рангов двух множеств выносим решение, кого из них сделать родителем. У кого больше ранг (value), тот и родитель
        // Это помогает поддерживать баланс в высоте дерева и в дальнейшем оптимизировать поиск
        if (subsetArray[nextSourceRoot].getValue() < subsetArray[nextDestinationRoot].getValue())
            subsetArray[nextSourceRoot].setParent(nextDestinationRoot);
        else if (subsetArray[nextSourceRoot].getValue() > subsetArray[nextDestinationRoot].getValue())
            subsetArray[nextDestinationRoot].setParent(nextSourceRoot);
        else {
            subsetArray[nextDestinationRoot].setParent(nextSourceRoot);
            subsetArray[nextSourceRoot].setValue(subsetArray[nextSourceRoot].getValue() + 1);
        }
    }

    private void addMSTResultToLogger(Edge[] finalEdges, StringBuilder trackLogger) {
        trackLogger.append("\nСписок ребер в MST:\n");
        for (int index = 0; index < finalEdges.length - 1; index++)
            trackLogger.append(finalEdges[index].getSource()).append(" <-> ").append(finalEdges[index].getDestination()).append("; ").
                    append("Вес = ").append(finalEdges[index].getWeight()).append("\n");
    }

    private String getCurrentMSTStateAsString(Edge[] currentResult, int amountOfEdges){
        StringBuilder result = new StringBuilder();
        boolean isFirstElem = true;

        for(int i = 0; i < amountOfEdges; i++){
            Edge edge = currentResult[i];
            if(isFirstElem){
                result.append(edge.getSource()).append("<->").append(edge.getDestination());
                isFirstElem = false;
            }
            else
                result.append(", ").append(edge.getSource()).append("<->").append(edge.getDestination());
        }
        result.append("; ");
        return result.toString();

    }

    @Override
    public Boolean isGraphFullyConnected(Graph graph) {
        return null;
    }
}
