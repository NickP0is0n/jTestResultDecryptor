package me.NickP0is0n;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.NickP0is0n.FormAPI.Form;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Form mainForm = new Form("ResultsDecryptor.fxml", "jTest Result Decryptor", 524, 415);
        java.awt.Image logo = SwingFXUtils.fromFXImage(new Image(Main.class.getClassLoader().getResourceAsStream("logo.png")), null);
        if(System.getProperty("os.name").equals("Mac OS X")) com.apple.eawt.Application.getApplication().setDockIconImage(logo);
        mainForm.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
