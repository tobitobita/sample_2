package dsk.samplejavafx8;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

public class SubWindowController implements Initializable {

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.printf("close button.\n");
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
