package me.NickP0is0n;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.NickP0is0n.FormAPI.Form;

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
    private AnchorPane appPane;

    @FXML
    void initialize() {
        exportBtn.setDisable(false);
        assert IntroController.selectedFile != null;
        getStudentInfoFromFile(IntroController.selectedFile);
    }

    @FXML
    void about() {
        showInformationAboutApplication();
    }

    @FXML
    void exit() {
        System.exit(0);
    }

    @FXML
    void export() {
        final File targetFile = getFileFromSaveDialog("Save text file with results", "*.txt", "Text files (.txt)");
        saveResultsToFile(targetFile);
    }

    @FXML
    void open() {
        final File inputFile = getFileFromOpenDialog("Choose task file", "*.jres", "jTest Result files (.jres)");
        importStudentInfoFromFile(inputFile);
    }

    private Student currentStudent;

    private void importStudentInfoFromFile (File inputFile) {
        if(inputFile != null)
        {
            exportBtn.setDisable(false);
            getStudentInfoFromFile(inputFile);
        }
    }

    private void getStudentInfoFromFile(File inputFile) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inputFile)))
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
            Platform.runLater(() -> {
                Stage currentStage = (Stage) appPane.getScene().getWindow();
                currentStage.setTitle(inputFile.getAbsolutePath() + " [jTest Result Decryptor]");
            });
        }
        catch(Exception ex){
            ex.printStackTrace();
            showError("An error occurred while reading the file!");
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

    private Alert makeAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert targetAlert = new Alert(alertType);
        targetAlert.setTitle(title);
        targetAlert.setHeaderText(headerText);
        targetAlert.setContentText(contentText);
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

    private File getFileFromOpenDialog(String title, String extension, String extensionDescription) //extension in *.ext format
    {
        FileChooser chooser = makeChooser(title, extension, extensionDescription);
        return chooser.showOpenDialog(new Stage()); //показ диалога на отдельной сцене
    }

    private void printResults(File targetFile) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(targetFile)) {
            out.println("jTest Exported Result");
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
        Alert error = makeAlert(Alert.AlertType.ERROR, "Error", "Error", text);
        showAlert(error);
    }

}
