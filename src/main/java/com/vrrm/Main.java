package com.vrrm;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vrrm.loginpanel.HomePane;
import com.vrrm.loginpanel.LoginPane;
import com.vrrm.util.AlertUtil;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    //Event management
    private final EventBus childrenBus = new EventBus();

    //View management
    private PaneStacker paneStacker;

    @Override
    public void start(Stage stage) {
        paneStacker = new PaneStacker(stage);
        paneStacker.pushPane(new HomePane(childrenBus));
        addHandlers();

        stage.setTitle("JavaFx Trial");
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    private void addHandlers() {
        childrenBus.register(this);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void handle(HomePane.HomeGoLoginEvent event) {
        paneStacker.pushPane(new LoginPane(childrenBus));
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void handle(LoginPane.LoginProceedEvent event) {
        paneStacker.popPane();
        AlertUtil.showInfo("Login success", null, "Will proceed to Authenticate with server..");
    }
    @SuppressWarnings("unused")
    @Subscribe
    public void handle(LoginPane.LoginCancelEvent event) {
        paneStacker.popPane();
        AlertUtil.showInfo("Login aborted", null, "Going back..");
    }

}