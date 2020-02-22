package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

public class CloseRequestHandler<T extends Event> implements EventHandler<T> {

    private Controller controller;

    CloseRequestHandler(Controller c) {
        controller = c;
    }

    @Override
    public void handle(Event event) throws UncheckedIOException {
        System.exit(0);
    }
}
