package me.NickP0is0n;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    private Student currentStudent;

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
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION); //Создание окна ошибки
        aboutAlert.setTitle("About jTest Result Decryptor");
        aboutAlert.setGraphic(new ImageView(new Image(Controller.class.getClassLoader().getResourceAsStream("logo.png"))));
        aboutAlert.setHeaderText("jTest Result Decryptor");
        aboutAlert.setContentText("Version 1.1 Beta 1\n\n" +
                "jTest Result Decryptor is a program to decrypt the results of users tested in jTest Student.\n\n" +
                "jTest Result Decryptor is a part of jTest software package.\n"+
                "Source code licensed under BSD-3 Clause license. Feel free to use/copy/modify this package as long as you specifying the name of the author.\n" +
                "Copyright (c) 2019, Nickolay Chaykovskyi All rights reserved.");
        aboutAlert.showAndWait();
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void export(ActionEvent event) throws FileNotFoundException {
        FileChooser chooser = new FileChooser(); //діалог збереження
        chooser.setTitle("Збережіть файл із завданнями");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстові файли (.txt)", "*.txt")); //фильтр файлов
        File taskFile = chooser.showSaveDialog(new Stage()); //показ диалога на отдельной сцене
        boolean succesfullOpen = true;
        if (taskFile != null) {
            try {
                taskFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                succesfullOpen = false;
                showError("Виникла помилка при створенні файлу!");
            }
            if (succesfullOpen)
            {
                PrintWriter out = new PrintWriter(taskFile);
                out.println("jTest Exported Result");
                out.println();
                out.println("ЗАГАЛЬНА ІНФОРМАЦІЯ");
                out.println("Прізвище учня: " + currentStudent.getSurName());
                out.println("Ім'я учня: " + currentStudent.getName());
                out.println("Клас учня: " + currentStudent.getGrade());
                out.println();
                out.println("ІНФОРМАЦІЯ ПРО ВИКОНАННЯ ЗАВДАНЬ");
                out.println("Учень почав виконання: " + currentStudent.getStartTime());
                out.println("Учень закінчив виконання: " + currentStudent.getFinishTime());
                out.println("Всього виконано завдань: " + currentStudent.getTasksResults().size());
                out.println("Всього пройдено тестів: " + Integer.parseInt(doneTestField.getText()) + " з " + currentStudent.getTasksResults().size() * 5);
                for(int i = 0; i < currentStudent.getTasksResults().size(); i++)
                {
                    out.println();
                    out.println("ЗАДАЧА " + (i + 1));
                    out.println("Тестів пройдено: " + currentStudent.getDoneTasks()[i] + " з 5");
                }
                out.println();
                out.println("Результати роботи учня експортовано " + new Date().toString());
                out.close();
            }
        }

    }

    @FXML
    void open(ActionEvent event) {
        FileChooser chooser = new FileChooser(); //діалог збереження
        chooser.setTitle("Оберіть файл із завданнями");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файли результатів (.jres)", "*.jres")); //фильтр файлов
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
                showError("Виникла помилка при читанні файлу!");
            }
        }
    }

    private void showError(String text)
    {
        Alert error = new Alert(Alert.AlertType.ERROR); //Создание окна ошибки
        error.setTitle("Помилка");
        error.setContentText(text);
        error.showAndWait();
    }

}
