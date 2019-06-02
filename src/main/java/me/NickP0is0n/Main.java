package me.NickP0is0n;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

import javax.swing.*;
import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ResultsDecryptor.fxml"));
        primaryStage.setTitle("jTest Result Decryptor");
        primaryStage.getIcons().add(new Image(new File("resources/logo.png").toURI().toString()));
        java.awt.Image logo = SwingFXUtils.fromFXImage(new Image(Main.class.getClassLoader().getResourceAsStream("logo.png")), null);
        if(System.getProperty("os.name").equals("Mac OS X")) com.apple.eawt.Application.getApplication().setDockIconImage(logo);
        primaryStage.setScene(new Scene(root, 730, 400));
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
