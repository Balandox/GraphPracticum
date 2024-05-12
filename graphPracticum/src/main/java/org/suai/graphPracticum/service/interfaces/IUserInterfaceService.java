package org.suai.graphPracticum.service.interfaces;

import java.util.List;

public interface IUserInterfaceService {

    void showGreeting();

    void showCommands();

    void showGoodbye();

    void showIncorrectInputWarning();

    void showFilesPaths(List<String> filePaths);

}
