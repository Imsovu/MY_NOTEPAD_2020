package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML TextArea textArea;
    @FXML MenuItem newFile;
    @FXML MenuItem openFile;
    @FXML MenuItem saveFile;
    @FXML MenuItem exit;
    @FXML MenuItem about;
    @FXML MenuItem delete;
    @FXML MenuItem selectAll;
    @FXML MenuItem copy;

    private Stage stage;

    private FileChooser fileChooser=new FileChooser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters()
                .addAll(
                        new FileChooser.ExtensionFilter("Text","*.txt"),
                        new FileChooser.ExtensionFilter("All Files","*.*")
                );
    }


    public void newFile(ActionEvent actionEvent) {
        textArea.clear();
    }

    public void openFile(ActionEvent actionEvent) {
        fileChooser.setTitle("Open");
        File file=fileChooser.showOpenDialog(stage);

        if (file!=null){
            textArea.clear();
            readText(file);
        }
    }

    private void readText(File file) {
        String text;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while ((text=bufferedReader.readLine())!=null){
                textArea.appendText(text + "\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        try {
            fileChooser.setTitle("Save as");
            File file=fileChooser.showSaveDialog(stage);

            if (file!=null){

                PrintWriter savedText=new PrintWriter(file);
                BufferedWriter save=new BufferedWriter(savedText);
                save.write(textArea.getText());
                save.close();
            }} catch (FileNotFoundException e) {
            System.out.println("ERROR"+ e);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void exit(ActionEvent actionEvent) {
        if(textArea.getText().isEmpty()){
            Platform.exit();
            return;
        }
        Alert alert=new Alert
                (Alert.AlertType.CONFIRMATION,
                        "Exit Without saving?",
                        ButtonType.YES,
                        ButtonType.NO,
                        ButtonType.CANCEL);

        alert.setTitle("Confirm");
        alert.showAndWait();

        if(alert.getResult()==(ButtonType.YES)){
            Platform.exit();
        }
        if (alert.getResult()==ButtonType.NO){
            saveFile();
        }
    }

    public void about(ActionEvent actionEvent) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("About");
        alert.setHeaderText("Hi ! I am Souvik Adhikary");
        alert.showAndWait();
    }

    public void delete(ActionEvent actionEvent) {
        textArea.deleteText(textArea.getSelection());
    }

    public void selectALl(ActionEvent actionEvent) {
        textArea.selectAll();
    }

    public void copy(ActionEvent actionEvent) {
        textArea.getSelectedText();
        textArea.copy();
    }
}
