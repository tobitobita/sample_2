package dsk.samplecanvas;

import dsk.samplecanvas.javafx.control.diagram.DiagramControl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class CanvasController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    private DiagramControl diagram;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.diagram = new DiagramControl();
        AnchorPane.setTopAnchor(this.diagram, 0d);
        AnchorPane.setRightAnchor(this.diagram, 0d);
        AnchorPane.setBottomAnchor(this.diagram, 0d);
        AnchorPane.setLeftAnchor(this.diagram, 0d);
        this.anchorPane.getChildren().add(this.diagram);
    }

    public DiagramControl getDiagramControl() {
        return this.diagram;
    }
}
