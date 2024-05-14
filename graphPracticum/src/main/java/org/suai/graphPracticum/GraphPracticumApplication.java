package org.suai.graphPracticum;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.model.BiconnectedComponentsGraph;
import org.suai.graphAlgorithms.model.DfsGraph;
import org.suai.graphAlgorithms.model.PrimaGraph;
import org.suai.graphAlgorithms.model.dijkstra.DijkstraGraph;
import org.suai.graphAlgorithms.model.kruskal.KruskalGraph;
import org.suai.graphAlgorithms.service.interfaces.IGraphBaseCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphAlgorithms.utils.GraphModelMapper;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.service.interfaces.IGraphGeneratorService;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

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
		System.setProperty("file.encoding", "UTF-8");
		SpringApplication.run(GraphPracticumApplication.class, args);
	}
}
