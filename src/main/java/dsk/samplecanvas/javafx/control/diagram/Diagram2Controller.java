package dsk.samplecanvas.javafx.control.diagram;

import dsk.samplecanvas.javafx.control.diagram.elements.RectControl;
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

        double xx = 50d;
        double yy = 50d;
        for (int i = 1; i <= 500; ++i) {
            RectControl rc = new RectControl(i);
            rc.setLayoutX(xx);
            rc.setLayoutY(yy);
            this.diagram.addElement(rc);
            xx += 25d;
            if (i % 25 == 0) {
                yy += 25d;
                xx = 50d;
            }
        }
    }
}
