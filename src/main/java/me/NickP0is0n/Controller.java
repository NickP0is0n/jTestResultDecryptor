package me.NickP0is0n;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Date;

public class Controller {
    @FXML
    private TextField nameField;

    @FXML
    private TextField surNameField;

    @FXML
    private TextField totalTaskField;

    @FXML
    private TextField totalTestField;

    @FXML
    private TextField doneTestField;

    @FXML
    private TextField gradeField;

    @FXML
    private TextField startDate;

    @FXML
    private TextField endDate;

    @FXML
    private Button exportBtn;

    @FXML
    void about(ActionEvent event) {
        showInformationAboutApplication();
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void export(ActionEvent event) {
        final File targetFile = getFileFromSaveDialog("Save text file with results", "*.txt", "Text files (.txt)");
        saveResultsToFile(targetFile);
    }

    @FXML
    void open(ActionEvent event) {
        FileChooser chooser = new FileChooser(); //діалог збереження
        chooser.setTitle("Choose task file");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jTest Result files (.jres)", "*.jres")); //фильтр файлов
        File taskFile = chooser.showOpenDialog(new Stage()); //показ диалога на отдельной сцене
        if(taskFile != null)
        {
            exportBtn.setDisable(false);
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(taskFile)))
            {
                currentStudent = (Student) ois.readObject();
                nameField.setText(currentStudent.getName());
                surNameField.setText(currentStudent.getSurName());
                gradeField.setText(currentStudent.getGrade());
                totalTaskField.setText(String.valueOf(currentStudent.getDoneTasks().length));
                totalTestField.setText(String.valueOf(currentStudent.getDoneTasks().length * 5));
                int doneTest = 0;
                for (int i = 0; i < currentStudent.getTasksResults().size(); i++) doneTest += currentStudent.getTasksResults().get(i)[1];
                doneTestField.setText(String.valueOf(doneTest));
                startDate.setText(currentStudent.getStartTime().toString());
                endDate.setText(currentStudent.getFinishTime().toString());
            }
            catch(Exception ex){
                ex.printStackTrace();
                showError("An error occurred while reading the file!");
            }
        }
    }


    private Student currentStudent;
    private AppInfo appInfo = AppInfo.getInstance();

    private void showInformationAboutApplication()
    {
        final Alert.AlertType alertType = Alert.AlertType.INFORMATION;
        final String title = "About jTest Result Decryptor";
        final Node graphic = new ImageView(new Image(Controller.class.getClassLoader().getResourceAsStream("logo.png")));
        final String headerText = "jTest Result Decryptor";
        final String contentText = appInfo.getVersion() + "\n\n" +
                "jTest Result Decryptor is a program to decrypt the results of users tested in jTest Student.\n\n" +
                "jTest Result Decryptor is a part of jTest software package.\n"+
                "Source code licensed under BSD-3 Clause license. Feel free to use/copy/modify this package as long as you specifying the name of the author.\n" +
                "Copyright (c) 2019, Nickolay Chaykovskyi All rights reserved.";

        Alert infoAlert = makeGraphicalAlert(alertType, title, headerText, contentText, graphic);
        showAlert(infoAlert);
    }

    private Alert makeAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert targetAlert = new Alert(alertType);
        targetAlert.setTitle(title);
        targetAlert.setHeaderText(headerText);
        targetAlert.setContentText(contentText);
        return targetAlert;
    }

    private Alert makeGraphicalAlert(Alert.AlertType alertType, String title, String headerText, String contentText, Node graphic) {
        Alert targetAlert = makeAlert(alertType, title, headerText, contentText);
        targetAlert.setGraphic(graphic);
        return targetAlert;
    }

    private void showAlert(Alert alert)
    {
        alert.showAndWait();
    }

    private FileChooser makeChooser(String title, String extension, String extensionDescription) {
        FileChooser chooser = new FileChooser(); //діалог збереження
        chooser.setTitle(title);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extensionDescription, extension)); //фильтр файлов
        return chooser;
    }

    private File getFileFromSaveDialog(String title, String extension, String extensionDescription) //extension in *.ext format
    {
        FileChooser chooser = makeChooser(title, extension, extensionDescription);
        return chooser.showSaveDialog(new Stage()); //показ диалога на отдельной сцене
    }

    private void printResults(File targetFile) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(targetFile)) {
            out.println("jTest Exported Result");
            out.println("jRes Generation 1");
            out.println();
            out.println("GENERAL INFO");
            out.println("User surname: " + currentStudent.getSurName());
            out.println("User name: " + currentStudent.getName());
            out.println("Additional info: " + currentStudent.getGrade());
            out.println();
            out.println("TASK COMPLETION INFO");
            out.println("Start time: " + currentStudent.getStartTime());
            out.println("End time: " + currentStudent.getFinishTime());
            out.println("Number of completed tasks: " + currentStudent.getTasksResults().size());
            out.println("Number of passed tests: " + Integer.parseInt(doneTestField.getText()) + " out of " + currentStudent.getTasksResults().size() * 5);
            for (int i = 0; i < currentStudent.getTasksResults().size(); i++) {
                out.println();
                out.println("TASK #" + (i + 1));
                out.println("Tests passed: " + currentStudent.getDoneTasks()[i] + " out of 5");
            }
            out.println();
            out.println("User results exported: " + new Date().toString());
        }
    }

    private void saveResultsToFile(File targetFile) {
        boolean isFileOpened = true;
        if (targetFile != null) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                isFileOpened = false;
                showError("An error occurred while creating the file!");
            }
            if (isFileOpened)
            {
                try {
                    printResults(targetFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    showError("An error occurred while exporting the results!");
                }
            }
        }
    }

    private void showError(String text)
    {
        Alert error = new Alert(Alert.AlertType.ERROR); //Создание окна ошибки
        error.setTitle("Error");
        error.setContentText(text);
        error.showAndWait();
    }

}
