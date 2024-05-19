package org.suai.graphPracticum.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.service.interfaces.IGraphBaseCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphAlgorithms.utils.GraphModelMapper;
import org.suai.graphGeneration.model.baseGraph.Graph;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.model.graphGenerated.GeneratedGraphElement;
import org.suai.graphGeneration.service.interfaces.IGraphGeneratorService;
import org.suai.graphPracticum.service.interfaces.IGraphCommandHandlerService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

    private Integer currentVariant = 1;

    private List<Integer> algorithmNumbers;

    private Integer amountOfVertex;

    private final Random random = new Random();

    private final static Integer MAX_WEIGHT = 10;

    private static final String introductionVariants = "Изобразить графы, представленные списком/матрицей смежности. Выполнить для них ";

    private static final String introductionAnswers = "Ниже приведены верные результаты выполнения алгоритмов в соответствии с условием варианта.";


    private Map<Integer, String> algNumberToAlgNameMap = Map.of(
            1, "Поиск в ширину (BFS)",
            2, "Поиск в глубину (DFS)",
            3, "Алгоритм Прима (Нахождение минимального остовного дерева)",
            4, "Алгоритм Крускала (Нахождение минимального остовного дерева)",
            5, "Алгоритм Дейкстры (Поиск кратчайшего пути)",
            6, "Алгоритм поиска двусвязных комонент",
            7, "Топологическая сортировка"
    );

    @Override
    public void handle() {
        List<AdjacencyListGraph> generatedGraphs = new ArrayList<>();
        List<String> solutions = new ArrayList<>();
        List<String> graphsVariantsForFile = new ArrayList<>(); // variants info
        List<Integer> graphRepresentationPerAlg = new ArrayList<>(); // 1 - adjacencyList; 2 - adjacencyMatrix
        Boolean isGraphFullyConnected;
        for(int i = 0; i < amountOfVariants; i++) {
            for(Integer algNum : algorithmNumbers) {
                boolean withWeight = Stream.of(3, 4, 5).anyMatch(cur -> (int) algNum == cur);
                AdjacencyListGraph sourceGraph;
                do {
                    if(algNum == 7)
                        sourceGraph = graphGeneratorService.generateAcyclicDirectedGraph(amountOfVertex, false, 0);
                    else
                        sourceGraph = withWeight ? graphGeneratorService.generateAdjacencyListGraph(amountOfVertex, true, MAX_WEIGHT) :
                                graphGeneratorService.generateAdjacencyListGraph(amountOfVertex, false, 0);
                    // convertForChecking
                    BfsGraph graphForChecking = GraphModelMapper.convertGeneratedGraphToBfsGraph(sourceGraph);
                    //checking that generated graph is fully connected
                    isGraphFullyConnected = calculatorService.isGraphFullyConnected(graphForChecking);
                }
                while (!isGraphFullyConnected);
                addGraphToVariant(sourceGraph, generatedGraphs, graphsVariantsForFile, graphRepresentationPerAlg, withWeight);
                makeCalculationAndSaveIt(solutions, sourceGraph, algNum);
            }
            printVariantToFile(graphsVariantsForFile);
            currentVariant = currentVariant - 1;
            printAnswersToFile(solutions);

            generatedGraphs.clear();
            graphsVariantsForFile.clear();
            graphRepresentationPerAlg.clear();
            solutions.clear();
        }
        System.out.println("\nОтлично. Варианты заданий и ответы на них успешно сохранены!");
        updateStateToDefault();
    }

    private void makeCalculationAndSaveIt(List<String> solutions, AdjacencyListGraph graph, Integer algNumber){
        Graph graphForCalculation = getGraphModelByAlgNumber(algNumber, graph);
        String solution = baseCalculatorService.calculate(graphForCalculation);
        solutions.add(solution);
    }

    private Graph getGraphModelByAlgNumber(Integer algNumber, AdjacencyListGraph generatedGraph){
        return switch (algNumber) {
            case 1 -> GraphModelMapper.convertGeneratedGraphToBfsGraph(generatedGraph);
            case 2 -> GraphModelMapper.convertGeneratedGraphToDfsGraph(generatedGraph);
            case 3 -> GraphModelMapper.convertGeneratedGraphToPrimaGraph(generatedGraph);
            case 4 -> GraphModelMapper.convertGeneratedGraphToKruskalGraph(generatedGraph);
            case 5 -> GraphModelMapper.convertGeneratedGraphToDijkstraGraph(generatedGraph);
            case 6 -> GraphModelMapper.convertGeneratedGraphToBiconnectedComponentsGraph(generatedGraph);
            default -> null;
        };
    }

    private void printAnswersToFile(List<String> solutions){
        String fileWithAnswers = filePaths.get(1);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileWithAnswers, true))) {
            writer.write("\n\nВАРИАНТ " + currentVariant++ + ". " + introductionAnswers + "\n\n");
            for (int i = 0; i < solutions.size(); i++) {
                String graph = solutions.get(i);
                writer.write(graph);
                if(i + 1 != solutions.size()) {
                    writer.newLine();
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private void printVariantToFile(List<String> graphVariantsForFile){
        String fileWithVariants = filePaths.get(0);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileWithVariants, true))) {
            writer.write("\n\nВАРИАНТ " + currentVariant++ + ". " + introductionVariants);
            for(int i = 0; i < algorithmNumbers.size(); i++){
                Integer algNum = algorithmNumbers.get(i);
                if(i + 1 == algorithmNumbers.size())
                    writer.write(algNumberToAlgNameMap.get(algNum) + " соответственно.\n\n");
                else
                    writer.write(algNumberToAlgNameMap.get(algNum) + ", ");
            }
            for (int i = 0; i < graphVariantsForFile.size(); i++) {
                String graph = graphVariantsForFile.get(i);
                writer.write(graph);
                if(i + 1 != graphVariantsForFile.size()) {
                    writer.newLine();
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
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

    private void updateStateToDefault(){
        this.filePaths = null;
        this.amountOfVariants = null;
        this.algorithmNumbers = null;
        this.amountOfVertex = null;
    }

    @Override
    public void updateState(List<String> filePaths, Integer amountOfVariants, List<Integer> algorithmNumbers, Integer amountOfVertex) {
        this.filePaths = filePaths;
        this.amountOfVariants = amountOfVariants;
        this.algorithmNumbers = algorithmNumbers;
        this.amountOfVertex = amountOfVertex;
    }

    @Override
    public void updateVariantCounterOnFileChanging() {
        this.currentVariant = 1;
    }
}
