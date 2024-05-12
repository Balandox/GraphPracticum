package org.suai.graphPracticum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.suai.graphPracticum.service.interfaces.ICommandHandlerService;
import org.suai.graphPracticum.service.interfaces.IUserInterfaceService;

@Component
public class ApplicationRunner {

    @Autowired
    private IUserInterfaceService userInterfaceService; // отвечает за вывод текста в консоль

    @Autowired
    private ICommandHandlerService commandHandlerService; // обработчик команд пользователя

    @EventListener(ApplicationReadyEvent.class)
    public void startApplication() {
        userInterfaceService.showGreeting();
        userInterfaceService.showCommands();
        commandHandlerService.start();
    }
}
