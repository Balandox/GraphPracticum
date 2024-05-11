package org.suai.graphPracticum.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.model.DfsGraph;
import org.suai.graphAlgorithms.service.interfaces.IGraphBaseCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphAlgorithms.utils.GraphModelMapper;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.service.interfaces.IGraphGeneratorService;
import org.suai.graphPracticum.service.interfaces.IUserInterfaceService;

import java.util.Scanner;

@Service
public class UserInterfaceService implements IUserInterfaceService {

    @Autowired
    @Qualifier("graphCalculatorService")
    private IGraphBaseCalculatorService baseCalculatorService;

    @Autowired
    private IGraphGeneratorService graphGeneratorService;

    @Autowired
    @Qualifier("bfsGraphCalculatorService")
    private IGraphCalculatorService calculatorService;

    @Override
    public void showGreeting() {

        AdjacencyListGraph sourceGraph = null;
		Boolean isGraphFullyConnected = false;
		do {
			sourceGraph = graphGeneratorService.generateAdjacencyListGraph(6, false, 0);
			// convertForChecking
			BfsGraph graphForChecking = GraphModelMapper.convertGeneratedGraphToBfsGraph(sourceGraph);
			//checking that generated graph is fully connected
			isGraphFullyConnected = calculatorService.isGraphFullyConnected(graphForChecking);
		}
		while (!isGraphFullyConnected);

		graphGeneratorService.printAdjacencyListGraph(sourceGraph);
		// перевод в любой другой граф в зависимости от алгоритма
		DfsGraph graphForCalculation = GraphModelMapper.convertGeneratedGraphToDfsGraph(sourceGraph);
		String solution = baseCalculatorService.calculate(graphForCalculation);
		System.out.println(solution);

        System.out.println("Привет, Семен!");
        Scanner scanner = new Scanner(System.in);

        for(;;){
            if(scanner.nextInt() == 0)
                break;
        }
        System.out.println("\n\n\n\n\n\n\n\n");
    }

}
