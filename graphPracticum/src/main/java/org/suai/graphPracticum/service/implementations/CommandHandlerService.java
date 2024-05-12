package org.suai.graphPracticum.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.suai.graphPracticum.service.interfaces.ICommandHandlerService;
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

    private List<String> filePaths = new ArrayList<>();

    private static final String GENERATE_COMMAND = "generate";

    private static final String FILES_SHOW_COMMAND = "files -show";

    private static final String FILES_ADD_COMMAND = "files -addgg";

    private static final String HELP_COMMAND = "help";

    private static final String CLEAR_COMMAND = "clear";

    private static final String EXIT_COMMAND = "exit";

    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.print("\nВведите команду: ");
            String userInput = scanner.nextLine();

            switch (userInput){
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
