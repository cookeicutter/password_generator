package group.password_generator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {


    @FXML
    public Button copypasswordtoclipboardButton = new Button();
    @FXML
    public Label passwordLabel = new Label();
    @FXML
    public CheckBox showpasswordCheckbox = new CheckBox();
    @FXML
    public Label digitLabel = new Label();
    @FXML
    public Button passwordButton = new Button();
    FileChooser fileChooser = new FileChooser();
    @FXML
    Slider digitslider = new Slider();
    int digit;
    @FXML
    Button savepasswordtotxtButton = new Button();
    private String generatedpassword = "";
    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        digitslider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                digit = (int) digitslider.getValue();
                digitLabel.setText(digit + " karakter");
            }
        });
    }

    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("App.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }


    public void printpassword(ActionEvent event) throws IOException {
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            saveSystem(file, generatedpassword);
        }
    }

    public void saveSystem(File file, String content) {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(content);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generatePassword(ActionEvent event) throws IOException {

        savepasswordtotxtButton.setVisible(true);
        int digit = (int) digitslider.getValue();
        String lower_cases = "qwertzuiopasdfghjklyxcvbnm";
        String upper_cases = "QWERTZUIOPASDFGHJKLYXCVBNM";

        String password = "";

        for (int x = 0; x < digit; x++) {
            int rand = (int) (3 * Math.random());
            switch (rand) {
                case 0:
                    password += String.valueOf((int) (10 * Math.random()));
                    break;
                case 1:
                    rand = (int) (lower_cases.length() * Math.random());
                    password += String.valueOf(lower_cases.charAt(rand));
                    break;
                case 2:
                    rand = (int) (upper_cases.length() * Math.random());
                    password += String.valueOf(upper_cases.charAt(rand));
                    break;
            }
        }
        generatedpassword = password;
        passwordLabel.setText(generatedpassword);
        copypasswordtoclipboardButton.setVisible(true);
    }

    public void checkBoxAction(ActionEvent event) throws IOException {
        passwordLabel.setVisible(showpasswordCheckbox.isSelected());
    }

    public void copyPasspowrdToClipboard(ActionEvent event) throws IOException {
        Clipboard clip = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(generatedpassword);
        clip.setContent(content);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Másolva");
        alert.setHeaderText("Sikeres művelet.");
        alert.setContentText("A generált jelszó sikeresen a vágólapra másolva.");
        alert.showAndWait();

    }


}