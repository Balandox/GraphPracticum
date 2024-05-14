package org.suai.graphPracticum.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.service.interfaces.IGraphBaseCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphAlgorithms.utils.GraphModelMapper;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.service.interfaces.IGraphGeneratorService;
import org.suai.graphPracticum.service.interfaces.IGraphCommandHandlerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class GraphCommandHandlerService implements IGraphCommandHandlerService {

    @Autowired
    @Qualifier("graphCalculatorService")
    private IGraphBaseCalculatorService baseCalculatorService;

    @Autowired
    private IGraphGeneratorService graphGeneratorService;

    @Autowired
    @Qualifier("bfsGraphCalculatorService")
    private IGraphCalculatorService calculatorService;

    private List<String> filePaths;

    private Integer amountOfVariants;

    private Integer algorithmNumber;

    private Integer graphRepresentationNumber;

    private Integer amountOfVertex;

    private Integer maxWeight;

    private Map<Integer, String> algNumberToAlgNameMap = Map.of(
            1, "Поиск в ширину (BFS)",
            2, "Поиск в глубину (DFS)",
            3, "Алгоритм Прима (Нахождение минимального остовного дерева)",
            4, "Алгоритм Крускала (Нахождение минимального остовного дерева)",
            5, "Алгоритм Дейкстры (Поиск кратчайшего пути)",
            6, "Алгоритм поиска двусвязных комонент"
    );

    //	@PostConstruct
//	public void init(){
//		AdjacencyListGraph sourceGraph = null;
//		Boolean isGraphFullyConnected = false;
//		do {
//			sourceGraph = graphGeneratorService.generateAdjacencyListGraph(6, false, 0);
//			// convertForChecking
//			BfsGraph graphForChecking = GraphModelMapper.convertGeneratedGraphToBfsGraph(sourceGraph);
//			//checking that generated graph is fully connected
//			isGraphFullyConnected = calculatorService.isGraphFullyConnected(graphForChecking);
//		}
//		while (!isGraphFullyConnected);
//
//		graphGeneratorService.printAdjacencyListGraph(sourceGraph);
//		// перевод в любой другой граф в зависимости от алгоритма
//		DfsGraph graphForCalculation = GraphModelMapper.convertGeneratedGraphToDfsGraph(sourceGraph);
//		String solution = baseCalculatorService.calculate(graphForCalculation);
//		System.out.println(solution);
//	}

    @Override
    public void handle() {
        handleVertexAndWeightInput(algorithmNumber);

        List<AdjacencyListGraph> generatedGraphs = new ArrayList<>();
        Boolean isGraphFullyConnected;
        for(int i = 0; i < amountOfVariants; i++) {
            AdjacencyListGraph sourceGraph;
            do {
                sourceGraph = graphGeneratorService.generateAdjacencyListGraph(amountOfVertex, maxWeight != null, maxWeight != null ? maxWeight : 0);
                // convertForChecking
                BfsGraph graphForChecking = GraphModelMapper.convertGeneratedGraphToBfsGraph(sourceGraph);
                //checking that generated graph is fully connected
                isGraphFullyConnected = calculatorService.isGraphFullyConnected(graphForChecking);
            }
            while (!isGraphFullyConnected);
            generatedGraphs.add(sourceGraph);
        }
        for (AdjacencyListGraph graph : generatedGraphs){
            if(graphRepresentationNumber == 1)
                graphGeneratorService.printAdjacencyMatrixGraph(graph, maxWeight != null ? maxWeight : 0);
            else
                graphGeneratorService.printAdjacencyListGraph(graph);
            System.out.println();
        }
    }

    private void handleVertexAndWeightInput(Integer algorithmNumber){
        System.out.println("Отлично! Ваш выбор: " + algNumberToAlgNameMap.get(algorithmNumber));
        System.out.print("\nТеперь укажите количество вершин графа: ");
        amountOfVertex = getUserIntegerInput();
        if(algorithmNumber == 3 || algorithmNumber == 4 || algorithmNumber == 5){
            System.out.print("\nТеперь укажите максимально возможный вес ребра: ");
            maxWeight = getUserIntegerInput();
        }
    }

    private Integer getUserIntegerInput(){
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        Integer userInputAsInt;
        while(true){
            if(userInput.matches("-?\\d+")){
                userInputAsInt = Integer.parseInt(userInput);
                if(userInputAsInt < 1){
                    System.out.print("Необходимо ввести значение больше единицы. Попробуйте еще раз: ");
                    userInput = scanner.nextLine();
                }
                else
                    break;
            }
            else {
                System.out.print("Необходимо ввести целое число. Попробуйте еще раз: ");
                userInput = scanner.nextLine();
            }
        }
        return userInputAsInt;
    }

    @Override
    public void updateState(List<String> filePaths, Integer amountOfVariants, Integer algorithmNumber, Integer graphRepresentationNumber) {
        this.filePaths = filePaths;
        this.amountOfVariants = amountOfVariants;
        this.algorithmNumber = algorithmNumber;
        this.graphRepresentationNumber = graphRepresentationNumber;
    }
}
