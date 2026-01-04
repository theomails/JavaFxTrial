package com.vrrm.loginpanel;

import com.google.common.eventbus.EventBus;
import com.vrrm.util.FormValidationUtil;
import com.vrrm.util.FormValidationUtil.ValidationMessage;
import com.vrrm.util.FormValidationUtil.ValidationMessageType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Data;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginPane extends MigPane {

    @Data
    public static class LoginProceedEvent {
        private final String username;
        private final String password;
    }
    public static class LoginCancelEvent { }

    private final EventBus parentBus;
    public LoginPane(EventBus parentBus) {
        super("insets 20 20 10 20, hidemode 3","[fill][300::,grow,fill]","[]10[]10[]0[]");
        this.parentBus = parentBus;
        init();
    }

    private final List<ValidationMessage> messages = new ArrayList<>();

    private final TextField txtUsername = new TextField();
    private final PasswordField txtPassword = new PasswordField();
    private final MigPane pnlButtons = new MigPane("","[grow][][]","[]");
    private final Button btnLogin = new Button("Login");
    private final Button btnCancel = new Button("Cancel");
    private final MigPane pnlMessages = new MigPane("hidemode 3","[grow, fill]","[]");
    private void init(){
        add(new Label("Username"));
        add(txtUsername, "wrap");

        add(new Label("Password"));
        add(txtPassword, "wrap");

        add(pnlButtons, "span 2, wrap");
        add(pnlMessages, "span 2");

        pnlButtons.add(btnLogin, "skip 1");
        pnlButtons.add(btnCancel);

        addHandlers();
    }

    private void addHandlers() {
        btnLogin.setOnAction(e -> {
            validateFields();
            if(messages.isEmpty()){
                parentBus.post(new LoginProceedEvent(txtUsername.getText(), txtPassword.getText()));
            } else {
                txtUsername.requestFocus();
            }
        });
        btnCancel.setOnAction(e -> {
            parentBus.post(new LoginCancelEvent());
        });
    }

    private void renderMessages(){
        pnlMessages.getChildren().clear();
        for(int i=0; i<messages.size(); i++){
            Label lblMessage = new Label(messages.get(i).getMessage());
            if(messages.get(i).getType() == ValidationMessageType.INFO) {
                lblMessage.setTextFill(Color.BLUE);
            } else if(messages.get(i).getType() == ValidationMessageType.ERROR) {
                lblMessage.setTextFill(Color.RED);
            }
            pnlMessages.add(lblMessage, i==messages.size()-1?"":"wrap");
        }

        Stage stage = (Stage) getScene().getWindow();
        stage.sizeToScene();
    }

    private void validateFields() {
        List<ValidationMessage> messages = new ArrayList<>();
        messages.add(FormValidationUtil.checkStringMinLength(txtUsername.getText(), 3, "Username is invalid"));
        messages.add(FormValidationUtil.checkStringMinLength(txtPassword.getText(), 3, "Password is invalid"));
        this.messages.clear();
        this.messages.addAll(messages.stream().filter(Objects::nonNull).toList());
        renderMessages();
    }
}
