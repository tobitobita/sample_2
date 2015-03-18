package dsk.bookshelf.inventory.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LoadedSceneController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    protected void handleButtonAction(ActionEvent event) {
        System.out.println("ACTION.");
    }
}
