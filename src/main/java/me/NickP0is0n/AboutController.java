package me.NickP0is0n;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AboutController {

    @FXML
    private AnchorPane appPane;

    @FXML
    private Label versionLabel;

    @FXML
    private Label buildLabel;

    @FXML
    void initialize() {
        AppInfo info = AppInfo.getInstance();
        versionLabel.setText("jTest Result Decryptor " + info.getVersion());
        buildLabel.setText("Built on " + info.getBuild());
    }

    @FXML
    void onCloseForm() {
        ((Stage)appPane.getScene().getWindow()).close();
    }

}
