package org.suai.graphAlgorithms.utils;

import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.model.DfsGraph;
import org.suai.graphAlgorithms.model.kruskal.Edge;
import org.suai.graphAlgorithms.model.kruskal.KruskalGraph;
import org.suai.graphAlgorithms.model.PrimaGraph;
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

    public static KruskalGraph convertGeneratedGraphToKruskalGraph(AdjacencyListGraph sourceGraph){
        KruskalGraph kruskalGraph = new KruskalGraph(sourceGraph.getAmountOfVertex(), sourceGraph.getAmountOfEdges());
        List<Edge> edges = new ArrayList<>();
        for(List<GeneratedGraphElement> vertex : sourceGraph.getAdjacencyList()){
            for(GeneratedGraphElement neighbor : vertex){
                Edge edge = new Edge(sourceGraph.getAdjacencyList().indexOf(vertex), neighbor.getVertex());
                if(neighbor.getWeight() != null)
                    edge.setWeight(neighbor.getWeight());
                if(!edges.contains(edge))
                    edges.add(edge);
            }
        }
        kruskalGraph.setEdges(edges);
        return kruskalGraph;
    }


}
