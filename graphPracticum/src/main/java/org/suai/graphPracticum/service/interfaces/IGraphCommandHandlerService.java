package org.suai.graphPracticum.service.interfaces;

import java.util.List;

public interface IGraphCommandHandlerService {

    void updateState(List<String> filePaths, Integer amountOfVariants, List<Integer> algorithmNumbers, Integer amountOfVertex);

    void handle();

}
