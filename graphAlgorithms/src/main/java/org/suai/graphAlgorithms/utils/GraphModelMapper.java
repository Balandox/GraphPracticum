package org.suai.graphAlgorithms.utils;

import org.suai.graphAlgorithms.model.*;
import org.suai.graphAlgorithms.model.dijkstra.DijkstraGraph;
import org.suai.graphAlgorithms.model.kruskal.Edge;
import org.suai.graphAlgorithms.model.kruskal.KruskalGraph;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.model.graphGenerated.GeneratedGraphElement;

import java.util.ArrayList;
import java.util.List;

public class GraphModelMapper {

    public static BfsGraph convertGeneratedGraphToBfsGraph(AdjacencyListGraph sourceGraph) {
        return new BfsGraph(sourceGraph);
    }

    public static PrimaGraph convertGeneratedGraphToPrimaGraph(AdjacencyListGraph sourceGraph){
        return new PrimaGraph(sourceGraph);
    }

    public static DfsGraph convertGeneratedGraphToDfsGraph(AdjacencyListGraph sourceGraph){
        return new DfsGraph(sourceGraph);
    }

    // convert AdjacencyList to Edges list representation
    public static KruskalGraph convertGeneratedGraphToKruskalGraph(AdjacencyListGraph sourceGraph){
        KruskalGraph kruskalGraph = new KruskalGraph(sourceGraph.getAmountOfVertex(), sourceGraph.getAmountOfEdges());
        List<Edge> edges = new ArrayList<>();
        for(int srcVertex = 0; srcVertex < sourceGraph.getAdjacencyList().size(); srcVertex++){
            for(GeneratedGraphElement neighbor : sourceGraph.getAdjacencyList().get(srcVertex)){
                Edge edge = new Edge(srcVertex, neighbor.getVertex());
                if(neighbor.getWeight() != null)
                    edge.setWeight(neighbor.getWeight());
                if(!edges.contains(edge))
                    edges.add(edge);
            }
        }
        kruskalGraph.setEdges(edges);
        return kruskalGraph;
    }

    // convert AdjacencyList to Edges list representation
    public static DijkstraGraph convertGeneratedGraphToDijkstraGraph(AdjacencyListGraph sourceGraph){
        DijkstraGraph dijkstraGraph = new DijkstraGraph(sourceGraph.getAmountOfVertex(), sourceGraph.getAmountOfEdges());
        List<Edge>[] graph = new ArrayList[dijkstraGraph.getAmountOfVertex()];

        for(int i = 0; i < dijkstraGraph.getAmountOfVertex(); i++)
            graph[i] = new ArrayList<>();

        for(int srcVertex = 0; srcVertex < sourceGraph.getAdjacencyList().size(); srcVertex++){
            for(GeneratedGraphElement neighbor : sourceGraph.getAdjacencyList().get(srcVertex)){
                int dstVertex = neighbor.getVertex();
                graph[srcVertex].add(new Edge(srcVertex, dstVertex, neighbor.getWeight()));
            }
        }
        dijkstraGraph.setGraph(graph);
        return dijkstraGraph;
    }

    public static BiconnectedComponentsGraph convertGeneratedGraphToBiconnectedComponentsGraph(AdjacencyListGraph sourceGraph){
        return new BiconnectedComponentsGraph(sourceGraph);
    }

    public static TopologicalSortGraph convertGeneratedGraphToTopologicalSortGraph(AdjacencyListGraph sourceGraph){
        return new TopologicalSortGraph(sourceGraph);
    }

}
