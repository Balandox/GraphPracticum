package org.suai.graphGeneration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.service.implementations.GraphGeneratorService;
import org.suai.graphGeneration.service.interfaces.IGraphGeneratorService;

import java.util.LinkedList;
import java.util.Queue;

@SpringBootApplication
public class GraphGenerationApplication {


	public static void main(String[] args) {
		SpringApplication.run(GraphGenerationApplication.class, args);
	}

}
