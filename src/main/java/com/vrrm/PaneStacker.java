package com.vrrm;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.Deque;

public class PaneStacker {
    private final Deque<Parent> viewStack = new ArrayDeque<>();
    private final Stage primaryStage;

    public PaneStacker(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void pushPane(Parent newRoot) {
        Scene scene = primaryStage.getScene();
        if (scene == null) {
            primaryStage.setScene(new Scene(newRoot));
        } else {
            viewStack.push(scene.getRoot()); //push OLD root
            scene.setRoot(newRoot);
        }

        Platform.runLater(() -> {
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
        });
    }

    public void popPane() {
        if (viewStack.isEmpty()) {
            return;
        }

        Scene scene = primaryStage.getScene();
        scene.setRoot(viewStack.pop());

        Platform.runLater(() -> {
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
        });
    }
}
