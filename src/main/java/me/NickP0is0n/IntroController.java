package me.NickP0is0n;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.NickP0is0n.FormAPI.Form;

import java.io.File;
import java.io.IOException;

public class IntroController {

    public static File selectedFile;

    @FXML
    void onAboutBtn(ActionEvent event) {
        showInformationAboutApplication();
    }

    @FXML
    void onExitBtn(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onOpenBtn(ActionEvent event) {
        final File INPUT_FILE = getFileFromOpenDialog("Choose task file", "*.jres", "jTest Result files (.jres)");
        if (INPUT_FILE != null) {
            selectedFile = INPUT_FILE;
            Form mainForm = new Form("ResultsDecryptor.fxml", "jTest Result Decryptor", 524, 415);
            try {
                mainForm.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showInformationAboutApplication() {
        Form aboutForm = new Form("about.fxml", "About jTest Student", 600, 400, false);
        aboutForm.setStageStyle(StageStyle.UNDECORATED);
        try {
            aboutForm.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFileFromOpenDialog(String title, String extension, String extensionDescription) //extension in *.ext format
    {
        FileChooser chooser = makeChooser(title, extension, extensionDescription);
        return chooser.showOpenDialog(new Stage()); //показ диалога на отдельной сцене
    }

    private FileChooser makeChooser(String title, String extension, String extensionDescription) {
        FileChooser chooser = new FileChooser(); //діалог збереження
        chooser.setTitle(title);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extensionDescription, extension)); //фильтр файлов
        return chooser;
    }

}
