package org.suai.graphPracticum.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.service.interfaces.IGraphBaseCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphAlgorithms.utils.GraphModelMapper;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.model.graphGenerated.GeneratedGraphElement;
import org.suai.graphGeneration.service.interfaces.IGraphGeneratorService;
import org.suai.graphPracticum.service.interfaces.IGraphCommandHandlerService;

import java.util.*;
import java.util.stream.Stream;

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

    private List<Integer> algorithmNumbers;

    private Integer amountOfVertex;

    private final Random random = new Random();

    private final static Integer MAX_WEIGHT = 10;


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
        List<AdjacencyListGraph> generatedGraphs = new ArrayList<>();
        List<String> graphsVariantsForFile = new ArrayList<>();
        List<Integer> graphRepresentationPerAlg = new ArrayList<>(); // 1 - adjacencyList; 2 - adjacencyMatrix
        Boolean isGraphFullyConnected;
        for(int i = 0; i < amountOfVariants; i++) {
            for(Integer algNum : algorithmNumbers) {
                boolean withWeight = Stream.of(3, 4, 5).anyMatch(cur -> (int) algNum == cur);
                AdjacencyListGraph sourceGraph;
                do {
                    sourceGraph = withWeight ? graphGeneratorService.generateAdjacencyListGraph(amountOfVertex, true, MAX_WEIGHT) :
                            graphGeneratorService.generateAdjacencyListGraph(amountOfVertex, false, 0);
                    // convertForChecking
                    BfsGraph graphForChecking = GraphModelMapper.convertGeneratedGraphToBfsGraph(sourceGraph);
                    //checking that generated graph is fully connected
                    isGraphFullyConnected = calculatorService.isGraphFullyConnected(graphForChecking);
                }
                while (!isGraphFullyConnected);
                addGraphToVariant(sourceGraph, generatedGraphs, graphsVariantsForFile, graphRepresentationPerAlg, withWeight);
            }
            generatedGraphs.clear();
            graphsVariantsForFile.clear();
            graphRepresentationPerAlg.clear();
        }
    }

    private void addGraphToVariant(AdjacencyListGraph sourceGraph, List<AdjacencyListGraph> generatedGraphs,
                                   List<String> graphsVariantsForFile, List<Integer> graphRepresentationPerAlg, boolean withWeight){
        int randomGraphRepresentation = random.nextInt(2) + 1;
        if(graphRepresentationPerAlg.size() == 2){
            int firstRep = graphRepresentationPerAlg.get(0);
            int secondRep = graphRepresentationPerAlg.get(1);
            if(firstRep == secondRep && secondRep == randomGraphRepresentation){ // generated all the same representation, need to change one to another
                randomGraphRepresentation = randomGraphRepresentation == 1 ? 2 : 1;
            }
        }

        String graphVariant;
        if(randomGraphRepresentation == 1)
            graphVariant = graphGeneratorService.printAdjacencyListGraph(sourceGraph);
        else
            graphVariant = withWeight ? graphGeneratorService.printAdjacencyMatrixGraph(sourceGraph, MAX_WEIGHT) : graphGeneratorService.printAdjacencyMatrixGraph(sourceGraph, 0);

        graphsVariantsForFile.add(graphVariant);
        generatedGraphs.add(sourceGraph);
        graphRepresentationPerAlg.add(randomGraphRepresentation);
    }

    private void printVariantToFile(List<String> graphVariantsForFile){

    }

    @Override
    public void updateState(List<String> filePaths, Integer amountOfVariants, List<Integer> algorithmNumbers, Integer amountOfVertex) {
        this.filePaths = filePaths;
        this.amountOfVariants = amountOfVariants;
        this.algorithmNumbers = algorithmNumbers;
        this.amountOfVertex = amountOfVertex;
    }
}
