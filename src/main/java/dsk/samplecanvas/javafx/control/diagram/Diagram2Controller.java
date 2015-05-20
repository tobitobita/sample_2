package dsk.samplecanvas.javafx.control.diagram;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class Diagram2Controller implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private DiagramControl diagram;

    /* Initializable */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        diagram.prefWidthProperty().bind(anchorPane.widthProperty());
        diagram.prefHeightProperty().bind(anchorPane.heightProperty());
    }
}
