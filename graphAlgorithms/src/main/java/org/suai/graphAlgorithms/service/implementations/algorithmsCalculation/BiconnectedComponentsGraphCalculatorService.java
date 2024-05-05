package org.suai.graphAlgorithms.service.implementations.algorithmsCalculation;

import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.BiconnectedComponentsGraph;
import org.suai.graphAlgorithms.model.kruskal.Edge;
import org.suai.graphAlgorithms.service.implementations.base.GraphCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;
import org.suai.graphGeneration.model.graphGenerated.GeneratedGraphElement;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class BiconnectedComponentsGraphCalculatorService extends GraphCalculatorService implements IGraphCalculatorService {

    private static int time = 0; // TODO don't forget assign 0 after calculation

    @Override
    public String makeAlgorithmCalculation(Graph graph) {
        BiconnectedComponentsGraph sourceGraph = (BiconnectedComponentsGraph) graph;
        StringBuilder trackLogger = new StringBuilder();
        trackLogger.append("\nНачало алгоритма Поиска Двусвязных Компонент\n");

        this.biconnectedComponentsFinder(sourceGraph, trackLogger);

        return trackLogger.toString();
    }

    private void biconnectedComponentsFinder(BiconnectedComponentsGraph graph, StringBuilder trackLogger){
        int[] disc = new int[graph.getAmountOfVertex()];
        int[] low = new int[graph.getAmountOfVertex()];
        int[] parent = new int[graph.getAmountOfVertex()];
        LinkedList<Edge> stack = new LinkedList<>();
        int iteration = 1;

        for (int i = 0; i < graph.getAmountOfVertex(); i++) {
            disc[i] = -1;
            low[i] = -1;
            parent[i] = -1;
        }

        for (int i = 0; i < graph.getAmountOfVertex(); i++) {
            if (disc[i] == -1)
                BCCUtil(i, disc, low, stack, parent, graph.getAdjacencyList());

            int j = 0;

            // If stack is not empty, pop all edges from stack
            while (!stack.isEmpty()) {
                j = 1;
                System.out.print(stack.getLast().getSource() + "--" + stack.getLast().getDestination() + " ");
                stack.removeLast();
            }
            if (j == 1) {
                System.out.println();
                //count++;
            }
        }


    }

    private void BCCUtil(int u, int[] disc, int[] low, LinkedList<Edge> stack, int[] parent, List<List<GeneratedGraphElement>> adjacencyList) {

        // Initialize discovery time and low value
        disc[u] = low[u] = ++time;
        int children = 0;

        List<Integer> neighbors = adjacencyList.get(u).stream()
                .map(GeneratedGraphElement::getVertex)
                .toList();
        // Go through all vertices adjacent to this
        Iterator<Integer> it = neighbors.iterator();
        while (it.hasNext()) {
            int v = it.next(); // v is current adjacent of 'u'

            // If v is not visited yet, then recur for it
            if (disc[v] == -1) {
                children++;
                parent[v] = u;

                // store the edge in stack
                stack.add(new Edge(u, v));
                BCCUtil(v, disc, low, stack, parent, adjacencyList);

                // Check if the subtree rooted with 'v' has a
                // connection to one of the ancestors of 'u'
                // Case 1 -- per Strongly Connected Components Article
                if (low[u] > low[v])
                    low[u] = low[v];

                // If u is an articulation point,
                // pop all edges from stack till u -- v
                if ((disc[u] == 1 && children > 1) || (disc[u] > 1 && low[v] >= disc[u])) {
                    while (stack.getLast().getSource() != u || stack.getLast().getDestination() != v) {
                        System.out.print(stack.getLast().getSource() + "--" + stack.getLast().getDestination() + " ");
                        stack.removeLast();
                    }
                    System.out.println(stack.getLast().getSource() + "--" + stack.getLast().getDestination() + " ");
                    stack.removeLast();

                    //count++;
                }
            }

            // Update low value of 'u' only if 'v' is still in stack
            // (i.e. it's a back edge, not cross edge).
            // Case 2 -- per Strongly Connected Components Article
            else if (v != parent[u] && disc[v] < disc[u] ) {
                if (low[u] > disc[v])
                    low[u] = disc[v];

                stack.add(new Edge(u, v));
            }
        }
    }

    @Override
    public Boolean isGraphFullyConnected(Graph graph) {
        return null;
    }


}
