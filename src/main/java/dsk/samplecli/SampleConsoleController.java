package dsk.samplecli;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SampleConsoleController implements Initializable {

    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    protected void handleTextFieldAction(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        this.textArea.appendText(textField.getText());
        this.textArea.appendText("\n");
        textField.clear();
    }

    @FXML
    protected void handleButtonAction(ActionEvent event) {
        this.textArea.clear();
    }
}
