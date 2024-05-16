package org.suai.graphPracticum.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.suai.graphPracticum.service.interfaces.ICommandHandlerService;
import org.suai.graphPracticum.service.interfaces.IGraphCommandHandlerService;
import org.suai.graphPracticum.service.interfaces.IUserInterfaceService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommandHandlerService implements ICommandHandlerService {

    @Autowired
    private IUserInterfaceService userInterfaceService; // отвечает за вывод текста в консоль

    @Autowired
    private IGraphCommandHandlerService graphCommandHandlerService; // отвечает за обработку команд с графами

    private List<String> filePaths = new ArrayList<>();

    private Map<Integer, String> algNumberToAlgNameMap = Map.of(
            1, "Поиск в ширину (BFS)",
            2, "Поиск в глубину (DFS)",
            3, "Алгоритм Прима (Нахождение минимального остовного дерева)",
            4, "Алгоритм Крускала (Нахождение минимального остовного дерева)",
            5, "Алгоритм Дейкстры (Поиск кратчайшего пути)",
            6, "Алгоритм поиска двусвязных комонент"
    );

    private static final String GENERATE_COMMAND = "generate";

    private static final String FILES_SHOW_COMMAND = "files -show";

    private static final String FILES_ADD_COMMAND = "files -add";

    private static final String HELP_COMMAND = "help";

    private static final String CLEAR_COMMAND = "clear";

    private static final String EXIT_COMMAND = "exit";

    @Override
    public void handle() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.print("\nВведите команду: ");
            String userInput = scanner.nextLine();

            switch (userInput){
                case GENERATE_COMMAND:
                    if(filePaths.isEmpty()) {
                        System.out.println("Прежде чем перейти к генерации вариантов, необходимо указать файлы для записи вариантов заданий и ответов.\n" +
                                "Для этого воспользуйтесь командой files -add");
                        break;
                    }
                    userInterfaceService.showFilesPaths(filePaths);
                    Integer amountOfVariants = handleAmountOfVariantsInput(scanner);
                    userInterfaceService.showGraphMenu();
                    List<Integer> algorithmNumbers = handleAlgorithmNumberInput(scanner);
                    System.out.print("\nОтлично! Ваш выбор: ");
                    for(int i = 0; i < algorithmNumbers.size(); i++){
                        if(i + 1 == algorithmNumbers.size())
                            System.out.print(algNumberToAlgNameMap.get(algorithmNumbers.get(i)) + ".");
                        else
                            System.out.print(algNumberToAlgNameMap.get(algorithmNumbers.get(i)) + ", ");
                    }
                    Integer amountOfVertex = handleVertexInput(scanner);

                    graphCommandHandlerService.updateState(filePaths, amountOfVariants, algorithmNumbers, amountOfVertex);
                    graphCommandHandlerService.handle();
                    break;
                case FILES_ADD_COMMAND:
                    userInterfaceService.showFilesPaths(filePaths);

                    System.out.print("\nВведите путь к файлу для записи вариантов заданий: ");
                    String variantsFilePath;
                    while (!isValidFilePath(variantsFilePath = scanner.nextLine()))
                        System.out.print("Путь к файлу указан неверно. Попробуйте снова: ");
                    System.out.println("Путь к файлу для записи вариантов заданий успешно сохранен!");

                    System.out.print("\nВведите путь к файлу для записи ответов: ");
                    String answersFilePath;
                    while (!isValidFilePath(answersFilePath = scanner.nextLine()))
                        System.out.print("Путь к файлу указан неверно. Попробуйте снова: ");
                    System.out.println("Путь к файлу для записи ответов успешно сохранен!\n");

                    filePaths.clear();
                    filePaths.add(variantsFilePath);
                    filePaths.add(answersFilePath);
                    break;

                case FILES_SHOW_COMMAND:
                    userInterfaceService.showFilesPaths(filePaths);
                    break;

                case HELP_COMMAND:
                    userInterfaceService.showCommands();
                    break;

                case EXIT_COMMAND:
                    userInterfaceService.showGoodbye();
                    scanner.close();
                    return;

                default:
                    userInterfaceService.showIncorrectInputWarning();
                    break;
            }
        }
    }

    private Integer handleVertexInput(Scanner scanner){
        System.out.print("\nТеперь укажите количество вершин графа: ");
        String amountOfVertex = scanner.nextLine();
        Integer amountOfVertexAsInt;
        while(true){
            if(amountOfVertex.matches("-?\\d+")){
                amountOfVertexAsInt = Integer.parseInt(amountOfVertex);
                if(amountOfVertexAsInt < 1){
                    System.out.print("Необходимо ввести значение больше единицы. Попробуйте еще раз: ");
                    amountOfVertex = scanner.nextLine();
                }
                else
                    break;
            }
            else {
                System.out.print("Необходимо ввести целое число. Попробуйте еще раз: ");
                amountOfVertex = scanner.nextLine();
            }
        }
        return amountOfVertexAsInt;
    }

   /* private Integer handleGraphRepresentationInput(Scanner scanner){
        System.out.print("\nДля выбора представления графа введите его порядковый номер в списке: ");
        String graphRepresentationNumber = scanner.nextLine();
        Integer graphRepresentationNumberAsInt;
        while(true){
            if(graphRepresentationNumber.matches("-?\\d+")){
                graphRepresentationNumberAsInt = Integer.parseInt(graphRepresentationNumber);
                if(graphRepresentationNumberAsInt < 1 || graphRepresentationNumberAsInt > 2){
                    System.out.print("Представления графа с таким номером не существует. Попробуйте еще раз: ");
                    graphRepresentationNumber = scanner.nextLine();
                }
                else
                    break;
            }
            else {
                System.out.print("Порядковый номер представления графа должен быть целым числом. Попробуйте еще раз: ");
                graphRepresentationNumber = scanner.nextLine();
            }
        }
        return graphRepresentationNumberAsInt;
    }*/

    private Integer handleAmountOfVariantsInput(Scanner scanner) {
        System.out.println("\n\nКаждый созданный вариант включает в себя 3 алгоритма для 3-х разных графов!");
        System.out.print("Введите необходимое количество вариантов: ");
        String amountOfVariants = scanner.nextLine();
        Integer amountOfVariantsAsInt;
        while (true) {
            if (amountOfVariants.matches("-?\\d+")) {
                amountOfVariantsAsInt = Integer.parseInt(amountOfVariants);
                if (amountOfVariantsAsInt < 1) {
                    System.out.print("Количество вариантов должно быть больше единицы. Попробуйте еще раз: ");
                    amountOfVariants = scanner.nextLine();
                } else
                    break;
            } else {
                System.out.print("Порядковый номер алгоритма должен быть целым числом. Попробуйте еще раз: ");
                amountOfVariants = scanner.nextLine();
            }
        }
        return amountOfVariantsAsInt;
    }

    private List<Integer> handleAlgorithmNumberInput(Scanner scanner){
        System.out.print("\nНеобходимо выбрать 3 алгоритма из представленных выше. Введите порядковые номера алгоритмов через пробел (пример: 1 4 3): ");
        String algorithmNumbers = scanner.nextLine();
        List<Integer> algorithmNumberAsIntegers;
        while(true){
            if(isAlgorithmInputStringCorrect(algorithmNumbers)){
                algorithmNumberAsIntegers = Arrays.stream(algorithmNumbers.split(" ")).map(Integer::valueOf).toList();
                break;
            }
            else {
                System.out.print("Некорректный ввод. Попробуйте еще раз: ");
                algorithmNumbers = scanner.nextLine();
            }
        }
        return algorithmNumberAsIntegers;
    }

    private boolean isAlgorithmInputStringCorrect(String algorithmNumbers){
        String[] algAsArray = algorithmNumbers.split(" ");
        for(String curAlg : algAsArray){
            String curAlgWithoutSpaces = curAlg.trim();
            if(!curAlgWithoutSpaces.matches("-?\\d+"))
                return false;
            Integer curAlgInt = Integer.valueOf(curAlgWithoutSpaces);
            if(curAlgInt < 1 || curAlgInt > 6)
                return false;
        }
        return true;
    }

    private boolean isValidFilePath(String path){
        if (path == null || path.isEmpty())
            return false;
        if (!path.endsWith(".txt"))
            return false;

        File file = new File(path);
        if(file.exists())
            return true;
        try {
            if (file.createNewFile())
                return true;
        } catch (IOException e) {
            return false;
        }
        return false;
    }

}
