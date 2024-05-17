package org.suai.graphPracticum.service.interfaces;

import java.util.List;

public interface IGraphCommandHandlerService {

    void updateState(List<String> filePaths, Integer algorithmNumber, Integer amountOfVertex, Integer graphRepresentation);

    void updateTaskCounterOnFileChanging();

    void handle();

}
