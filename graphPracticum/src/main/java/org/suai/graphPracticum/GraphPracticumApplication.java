package org.suai.graphPracticum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.service.implementations.GraphCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphBaseCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphAlgorithms.utils.GraphModelMapper;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.service.implementations.GraphGeneratorService;
import org.suai.graphGeneration.service.interfaces.IGraphGeneratorService;

@SpringBootApplication
public class GraphPracticumApplication {

	public static void main(String[] args) {
		// generation
		IGraphGeneratorService graphGeneratorService = new GraphGeneratorService();
		AdjacencyListGraph sourceGraph = graphGeneratorService.generateAdjacencyListGraph(6, false, 0);
		graphGeneratorService.printAdjacencyListGraph(sourceGraph);
		// convertForCalculation
		BfsGraph graphForCalculation = GraphModelMapper.convertGeneratedGraphToBfsGraph(sourceGraph);
		// calculate
		IGraphBaseCalculatorService calculatorService = new GraphCalculatorService();
		calculatorService.calculate(graphForCalculation);

		SpringApplication.run(GraphPracticumApplication.class, args);
	}

}
