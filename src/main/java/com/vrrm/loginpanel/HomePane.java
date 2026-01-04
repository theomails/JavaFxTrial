package com.vrrm.loginpanel;

import com.google.common.eventbus.EventBus;
import javafx.scene.control.Button;
import org.tbee.javafx.scene.layout.MigPane;

public class HomePane extends MigPane {

    public static class HomeGoLoginEvent {}

    private final EventBus parentBus;
    public HomePane(EventBus parentBus) {
        super("insets 100 30 100 30", "[200, fill]", "[50, fill]");
        this.parentBus = parentBus;
        init();
        addHandlers();
    }

    private final Button btnLogin = new Button("Login");
    private void init(){
        add(btnLogin);
    }

    private void addHandlers() {
        btnLogin.setOnAction(e -> {
            parentBus.post(new HomeGoLoginEvent());
        });
    }
}
