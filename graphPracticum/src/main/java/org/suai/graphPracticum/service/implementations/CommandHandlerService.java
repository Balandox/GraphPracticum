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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class CommandHandlerService implements ICommandHandlerService {

    @Autowired
    private IUserInterfaceService userInterfaceService; // отвечает за вывод текста в консоль

    @Autowired
    private IGraphCommandHandlerService graphCommandHandlerService; // отвечает за обработку команд с графами

    private List<String> filePaths = new ArrayList<>();

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
                    userInterfaceService.showGraphRepresentationOptions();
                    Integer graphRepresentationNumber = handleGraphRepresentationInput(scanner);
                    userInterfaceService.showGraphMenu();
                    Integer algorithmNumber = handleAlgorithmNumberInput(scanner);

                    graphCommandHandlerService.updateState(filePaths, amountOfVariants, algorithmNumber, graphRepresentationNumber);
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

    private Integer handleGraphRepresentationInput(Scanner scanner){
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
    }

    private Integer handleAmountOfVariantsInput(Scanner scanner) {
        System.out.print("\nВведите необходимое количество вариантов: ");
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

    private Integer handleAlgorithmNumberInput(Scanner scanner){
        System.out.print("\nДля выбора алгоритма введите его порядковый номер в списке: ");
        String algorithmNumber = scanner.nextLine();
        Integer algorithmNumberAsInt;
        while(true){
            if(algorithmNumber.matches("-?\\d+")){
                algorithmNumberAsInt = Integer.parseInt(algorithmNumber);
                if(algorithmNumberAsInt < 1 || algorithmNumberAsInt > 6){
                    System.out.print("Алгоритма с таким номером не существует. Попробуйте еще раз: ");
                    algorithmNumber = scanner.nextLine();
                }
                else
                    break;
            }
            else {
                System.out.print("Порядковый номер алгоритма должен быть целым числом. Попробуйте еще раз: ");
                algorithmNumber = scanner.nextLine();
            }
        }
        return algorithmNumberAsInt;
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
