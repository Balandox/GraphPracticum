package org.suai.graphGeneration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.suai.graphGeneration.service.implementations.GraphGenerator;

@SpringBootApplication
public class GraphGenerationApplication {


	public static void main(String[] args) {
		GraphGenerator graphGenerator = new GraphGenerator();
		graphGenerator.generateAdjacencyMatrixGraph(10, false, 10);
		SpringApplication.run(GraphGenerationApplication.class, args);
	}

}
