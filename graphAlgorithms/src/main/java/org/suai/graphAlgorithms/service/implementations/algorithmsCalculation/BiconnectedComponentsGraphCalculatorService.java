package org.suai.graphAlgorithms.service.implementations.algorithmsCalculation;

import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.BiconnectedComponentsGraph;
import org.suai.graphAlgorithms.model.kruskal.Edge;
import org.suai.graphAlgorithms.service.implementations.base.GraphCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.model.baseGraph.Graph;
import org.suai.graphGeneration.model.graphGenerated.GeneratedGraphElement;

import java.util.*;

@Service
public class BiconnectedComponentsGraphCalculatorService extends GraphCalculatorService implements IGraphCalculatorService {

    private static int time = 0;
    private static int iteration = 0;
    private static List<Integer> articulationPoints = new ArrayList<>();

    private static StringBuilder biconnectedComponents = new StringBuilder();

    @Override
    public String makeAlgorithmCalculation(Graph graph) {
        BiconnectedComponentsGraph sourceGraph = (BiconnectedComponentsGraph) graph;
        StringBuilder trackLogger = new StringBuilder();
        trackLogger.append("\nНачало алгоритма Поиска Двусвязных Компонент. Стартовая вершина - ").append(0).append("\n");

        this.biconnectedComponentsFinder(sourceGraph, trackLogger);

        return trackLogger.toString();
    }

    private void biconnectedComponentsFinder(BiconnectedComponentsGraph graph, StringBuilder trackLogger){
        int[] disc = new int[graph.getAmountOfVertex()];
        int[] low = new int[graph.getAmountOfVertex()];
        int[] parent = new int[graph.getAmountOfVertex()];
        LinkedList<Edge> stack = new LinkedList<>();

        for (int i = 0; i < graph.getAmountOfVertex(); i++) {
            disc[i] = -1;
            low[i] = -1;
            parent[i] = -1;
        }

        trackLogger.append("\nНачинаем обход графа при помощи DFS");
        for (int i = 0; i < graph.getAmountOfVertex(); i++) {
            if (disc[i] == -1)
                BCCUtil(i, disc, low, stack, parent, graph.getAdjacencyList(), trackLogger);

            int j = 0;

            // Может быть такая ситуация, когда в графе есть несколько несвязных между собой двусвязных компонент
            // тогда после алгоритма в стеке останутся ребра, нужно их тоже вывести
            while (!stack.isEmpty()) {
                j = 1;
                biconnectedComponents.append(stack.getLast().getSource()).append("<->").append(stack.getLast().getDestination()).append(" ");
                stack.removeLast();
            }
            if (j == 1) {
                biconnectedComponents.append("\n");
                //count++;
            }
        }

        trackLogger.append("\nТочки сочленения: ");
        articulationPoints.forEach(value -> trackLogger.append(value).append(" "));
        if(articulationPoints.isEmpty())
            trackLogger.append("Отсутствуют");
        trackLogger.append("\n\nДвусвязные компоненты:\n").append(biconnectedComponents.toString());
        time = 0;
        iteration = 0;
        articulationPoints.clear();
        biconnectedComponents.setLength(0);
    }

    // в эту функцию заходим при нахождении новой не посещенной вершины u (углубляемся в dfs)

    private void BCCUtil(int u, int[] disc, int[] low, LinkedList<Edge> stack, int[] parent,
                         List<List<GeneratedGraphElement>> adjacencyList, StringBuilder trackLogger) {
        // По сути в данном подходе все вершины, кроме листьев являются корнями своих собственных поддеревьев
        // disc[u] - номер вершины u в порядке dfs обхода
        // low[u] - самая верхняя вершина в dfs tree, которая может быть достигнута из u через обратные ребра
        disc[u] = low[u] = ++time;
        trackLogger.append("\nШаг ").append(iteration++).append(": Текущая вершина - ").append(u).append(";  ");
        int children = 0;

        List<Integer> neighbors = adjacencyList.get(u).stream()
                .map(GeneratedGraphElement::getVertex)
                .toList();
        Iterator<Integer> it = neighbors.iterator();
        while (it.hasNext()) {
            int v = it.next();
            // u - родитель на текущем шаге, а v - ребенок. При углублении v - станет родителем другой вершины
            // u - может быть родителем не только для своих соседей, но и для вершин в его поддереве
            if (disc[v] == -1) { // Нашли не посещенного соседа
                children++;
                parent[v] = u;

                // store the edge in stack
                stack.add(new Edge(u, v));
                BCCUtil(v, disc, low, stack, parent, adjacencyList, trackLogger);

                if (low[u] > low[v]) // есть ли обратное ребро в поддереве v, если да, то обновляем
                    low[u] = low[v];

                // u - точка сочленения:
                // 1.если оно является корнем поддерева и при это имеет как минимум двух соседей в порядке обхода dfs (для вершины 0 - корня всего дерева)
                // 2. если оно не является корнем поддерева, но имеет соседа v, который тоже образует свое поддерево, но при этом
                // ни одна вершина из поддерева v не имеет обратного ребра с поддеревом u.
                // low[v] >= disc[u] значит что минимальная вершина, которая может быть достигнута из v через обратные ребра она стоит позже в порядке обхода dfs, чем родитель u
                if ((disc[u] == 1 && children > 1) || (disc[u] > 1 && low[v] >= disc[u])) {
                    // как только мы нашли точку сочленения, то достаем все ребра из стека, пока не дойдем до ребра u--v
                    // все эти ребра + ребро u--v будут образовывать компонент двусвязности
                    articulationPoints.add(u);
                    while (stack.getLast().getSource() != u || stack.getLast().getDestination() != v) {
                        biconnectedComponents.append(stack.getLast().getSource()).append("<->").append(stack.getLast().getDestination()).append(" ");
                        stack.removeLast();
                    }
                    biconnectedComponents.append(stack.getLast().getSource()).append("<->").append(stack.getLast().getDestination()).append("\n");
                    stack.removeLast();
                    //count++;
                }
            }

            // Вершина v - уже была посещена и при этом она не родитель u и к тому же v в порядке обхода dfs стоит раньше u, следовательно u--v это обратное ребро
            else if (v != parent[u] && disc[v] < disc[u] ) {
                if (low[u] > disc[v])
                    low[u] = disc[v]; // теперь мы знаем, что в вершину u можно добраться через обратные ребра с disc[v] шагов
                stack.add(new Edge(u, v));
            }
        }
    }

    @Override
    public Boolean isGraphFullyConnected(Graph graph) {
        return null;
    }


}
