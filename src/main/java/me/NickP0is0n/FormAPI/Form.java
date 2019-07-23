package me.NickP0is0n.FormAPI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro8.JMetro;

import java.io.IOException;

public class Form {

    private final String FXML_FORM_FILE;
    private final FXMLLoader LOADER;

    private String title;
    private int width;
    private int height;
    private boolean resizable;

    private StageStyle stageStyle;

    public Form(String fxmlFile, String title, int width, int height) {
        FXML_FORM_FILE = fxmlFile;
        this.title = title;
        this.width = width;
        this.height = height;
        this.resizable = false;
        this.stageStyle = StageStyle.DECORATED;
        LOADER = makeFXMLLoader();
    }

    public Form(String fxmlFile, String title, int width, int height, boolean resizable) {
        FXML_FORM_FILE = fxmlFile;
        this.title = title;
        this.width = width;
        this.height = height;
        this.resizable = resizable;
        this.stageStyle = StageStyle.DECORATED;
        LOADER = makeFXMLLoader();
    }

    public void show() throws IOException {
        Stage stage = makeStage();
        stage.initStyle(stageStyle);
        stage.show();
    }

    private Stage makeStage() throws IOException {
        Stage stage = new Stage();
        Parent root = LOADER.load();
        stage.setTitle(title);
        stage.getIcons().add(new Image("file:/resources/logo.png"));
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(resizable);
        return stage;
    }

    public void setStageStyle(StageStyle style) {
        this.stageStyle = style;
    }

    private FXMLLoader makeFXMLLoader() {
        return new FXMLLoader(Form.class.getClassLoader().getResource(FXML_FORM_FILE));
    }
}
