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

    private Integer algorithmNumber;

    private Integer amountOfVertex;

    private Integer graphRepresentation;

    private Integer taskCounter = 1;

    private final Random random = new Random();

    private final static Integer MAX_WEIGHT = 10;

    private static final String introductionAnswers = "Ниже приведен верный результаты выполнения алгоритма в соответствии с условием задания.";


    private Map<Integer, String> algNumberToAlgNameMap = Map.of(
            1, "Поиск в ширину (BFS)",
            2, "Поиск в глубину (DFS)",
            3, "Алгоритм Прима (Нахождение минимального остовного дерева)",
            4, "Алгоритм Крускала (Нахождение минимального остовного дерева)",
            5, "Алгоритм Дейкстры (Поиск кратчайшего пути)",
            6, "Алгоритм поиска двусвязных комонент",
            7, "Топологическую сортировку (на основе DFS)"
    );

    @Override
    public void handle() {
        Boolean isGraphFullyConnected;
        boolean withWeight = Stream.of(3, 4, 5).anyMatch(cur -> (int) algorithmNumber == cur);
        AdjacencyListGraph generatedGraph;
        do {
            if(algorithmNumber == 7)
                generatedGraph = graphGeneratorService.generateAcyclicDirectedGraph(amountOfVertex, false, 0);
            else
                generatedGraph = withWeight ? graphGeneratorService.generateAdjacencyListGraph(amountOfVertex, true, MAX_WEIGHT) :
                        graphGeneratorService.generateAdjacencyListGraph(amountOfVertex, false, 0);
            // convertForChecking
            BfsGraph graphForChecking = GraphModelMapper.convertGeneratedGraphToBfsGraph(generatedGraph);
            //checking that generated graph is fully connected
            isGraphFullyConnected = calculatorService.isGraphFullyConnected(graphForChecking);
        }
        while (!isGraphFullyConnected);

        String generatedGraphForFile;
        if(graphRepresentation == 1)
            generatedGraphForFile = graphGeneratorService.printAdjacencyListGraph(generatedGraph);
        else
            generatedGraphForFile = withWeight ? graphGeneratorService.printAdjacencyMatrixGraph(generatedGraph, MAX_WEIGHT) : graphGeneratorService.printAdjacencyMatrixGraph(generatedGraph, 0);

        Graph graphForCalculation = getGraphModelByAlgNumber(algorithmNumber, generatedGraph);
        String solution = baseCalculatorService.calculate(graphForCalculation);
        printGraphToFile(generatedGraphForFile);
        taskCounter--;
        printAnswersToFile(solution);
        System.out.println("\nОтлично. Условие задания и ответ на него успешно сохранены!");
        updateStateToDefault();

    }

    private void makeCalculationAndSaveItToFile(List<String> solutions, AdjacencyListGraph graph, Integer algNumber){
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
            case 7 -> GraphModelMapper.convertGeneratedGraphToTopologicalSortGraph(generatedGraph);
            default -> null;
        };
    }

    private void printAnswersToFile(String solution){
        String fileWithAnswers = filePaths.get(1);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileWithAnswers, true))) {
            writer.write("\n\nЗадание " + taskCounter++ + ". " + introductionAnswers + "\n\n");
            writer.write(solution);
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private void printGraphToFile(String graph){
        String fileWithVariants = filePaths.get(0);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileWithVariants, true))) {
            StringBuilder taskIntroduction = new StringBuilder();
            taskIntroduction.append("\n\n").append("Задание ").append(taskCounter++).append(". Изобразить граф представленный в виде ");
            if(graphRepresentation == 1)
                taskIntroduction.append("списка смежности. Выполнить для него ");
            else
                taskIntroduction.append("матрицы смежности. Выполнить для него ");
            taskIntroduction.append(algNumberToAlgNameMap.get(algorithmNumber)).append(".");
            if(algorithmNumber == 7)
                taskIntroduction.append(" В ходе Топологической сортировки при нескольких не посещенных соседях необходимо выбирать соседа с наименьшим значением.\n\n");
            else
                taskIntroduction.append("\n\n");
            writer.write(taskIntroduction.toString());
            writer.write(graph);
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }


    private void updateStateToDefault(){
        this.filePaths = null;
        this.algorithmNumber = null;
        graphRepresentation = null;
        this.amountOfVertex = null;
    }

    @Override
    public void updateState(List<String> filePaths, Integer algorithmNumber, Integer amountOfVertex, Integer graphRepresentation) {
        this.filePaths = filePaths;
        this.algorithmNumber = algorithmNumber;
        this.amountOfVertex = amountOfVertex;
        this.graphRepresentation = graphRepresentation;
    }

    @Override
    public void updateTaskCounterOnFileChanging() {
        this.taskCounter = 1;
    }
}
