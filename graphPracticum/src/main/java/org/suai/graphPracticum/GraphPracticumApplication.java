package org.suai.graphPracticum;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.model.PrimaGraph;
import org.suai.graphAlgorithms.service.interfaces.IGraphBaseCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphAlgorithms.utils.GraphModelMapper;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.service.interfaces.IGraphGeneratorService;

@SpringBootApplication
@ComponentScan({"org.suai.graphAlgorithms", "org.suai.graphGeneration",
		"org.suai.graphPracticum"})
@EnableConfigurationProperties
public class GraphPracticumApplication {

	@Autowired
	@Qualifier("graphCalculatorService")
	private IGraphBaseCalculatorService baseCalculatorService;

	@Autowired
	private IGraphGeneratorService graphGeneratorService;

	@Autowired
	@Qualifier("bfsGraphCalculatorService")
	private IGraphCalculatorService calculatorService;

	public static void main(String[] args) {
		SpringApplication.run(GraphPracticumApplication.class, args);
	}

	@PostConstruct
	public void init(){
		AdjacencyListGraph sourceGraph = null;
		Boolean isGraphFullyConnected = false;
		do {
			sourceGraph = graphGeneratorService.generateAdjacencyListGraph(40, true, 10);
			// convertForChecking
			BfsGraph graphForChecking = GraphModelMapper.convertGeneratedGraphToBfsGraph(sourceGraph);
			//checking that generated graph is fully connected
			isGraphFullyConnected = calculatorService.isGraphFullyConnected(graphForChecking);
		}
		while (!isGraphFullyConnected);

		graphGeneratorService.printAdjacencyListGraph(sourceGraph);
		// перевод в любой другой граф в зависимости от алгоритма
		PrimaGraph graphForCalculation = GraphModelMapper.convertGeneratedGraphToPrimaGraph(sourceGraph);
		String solution = baseCalculatorService.calculate(graphForCalculation);
		System.out.println(solution);
	}

}
