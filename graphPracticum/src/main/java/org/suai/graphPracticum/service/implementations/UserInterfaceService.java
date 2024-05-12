package org.suai.graphPracticum.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.suai.graphAlgorithms.model.BfsGraph;
import org.suai.graphAlgorithms.model.DfsGraph;
import org.suai.graphAlgorithms.service.interfaces.IGraphBaseCalculatorService;
import org.suai.graphAlgorithms.service.interfaces.IGraphCalculatorService;
import org.suai.graphAlgorithms.utils.GraphModelMapper;
import org.suai.graphGeneration.model.graphGenerated.AdjacencyListGraph;
import org.suai.graphGeneration.service.interfaces.IGraphGeneratorService;
import org.suai.graphPracticum.service.interfaces.IUserInterfaceService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        try (InputStream inputStream = new ClassPathResource("interface/greeting.txt").getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null)
                System.out.println(line);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

}
