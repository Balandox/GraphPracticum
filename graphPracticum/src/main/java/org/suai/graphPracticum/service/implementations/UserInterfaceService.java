package org.suai.graphPracticum.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.service.interfaces.IGraphBaseCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphGeneration.service.interfaces.IGraphGeneratorService;
import org.suai.graphPracticum.service.interfaces.IUserInterfaceService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;

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

    private void show(String filePath){
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null)
                System.out.println(line);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    @Override
    public void showGreeting() {
        show("interface/greeting.txt");
    }

    @Override
    public void showCommands(){
        show("interface/commands.txt");
    }

    @Override
    public void showGoodbye() {
        show("interface/goodbye.txt");
    }

    @Override
    public void showIncorrectInputWarning() {
        show("interface/incorrectInput.txt");
    }

    @Override
    public void showFilesPaths(List<String> filePaths) {
        if(filePaths.isEmpty()) {
            System.out.println("Вы еще не указали файлы для записи вариантов заданий и ответов.");
            return;
        }
        System.out.println("Текущий файл для записи вариантов заданий: " + filePaths.get(0));
        System.out.println("Текущий файл для записи ответов: " + filePaths.get(1));
    }

}
