package dsk.samplejavafx8;

import dsk.samplejavafx8.controls.SampleControl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SampleControl c = new SampleControl();
        c.setPrefWidth(40d);
        c.setPrefHeight(30d);
        c.setLayoutX(100d);
        c.setLayoutY(100d);
        this.pane.getChildren().add(c);
    }
}
