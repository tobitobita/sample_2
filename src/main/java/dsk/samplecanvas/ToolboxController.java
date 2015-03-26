package dsk.samplecanvas;

import dsk.samplecanvas.control.OvalControl;
import dsk.samplecanvas.control.RectControl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

public class ToolboxController implements Initializable, ClickHandler {

    @FXML
    private Pane titlebar;

    private double clickX;

    private double clickY;

    private int selected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    protected void handleSeeAction(ActionEvent event) {
        selected = 1;
    }

    @FXML
    protected void handleActionAction(ActionEvent event) {
        selected = 2;
    }

    @FXML
    protected void handleNextAction(ActionEvent event) {
        selected = 3;
    }

    public void postInit() {
        final Window window = getWindow();
        this.titlebar.setOnMousePressed((MouseEvent event) -> {
            clickX = event.getX();
            clickY = event.getY();
        });
        this.titlebar.setOnMouseDragged((MouseEvent event) -> {
            window.setX(event.getScreenX() - clickX);
            window.setY(event.getScreenY() - clickY);
        });
    }

    private Window getWindow() {
        return this.titlebar.getScene().getWindow();
    }

    @Override
    public void onClickDiagram(Pane pane, MouseEvent event) {
        System.out.printf("onClickDiagram, %d\n", selected);
        switch (selected) {
            case 1:
                addSee(pane, event);
                break;
            case 2:
                addAction(pane, event);
                break;
            case 3:
//                addNext(pane, event);
                break;
            default:
                break;
        }
    }

    private void addSee(Pane pane, MouseEvent event) {
        RectControl rect = new RectControl();
        System.out.printf("ADD x:%f, y::%f\n", event.getX(), event.getY());
        rect.setLayoutX(event.getX());
        rect.setLayoutY(event.getY());
        pane.getChildren().add(rect);
        this.clearSelect();
    }

    private void addAction(Pane pane, MouseEvent event) {
        OvalControl oval = new OvalControl();
        System.out.printf("ADD x:%f, y::%f\n", event.getX(), event.getY());
        oval.setLayoutX(event.getX());
        oval.setLayoutY(event.getY());
        pane.getChildren().add(oval);
        this.clearSelect();
    }

    private void addNext(Pane pane, MouseEvent event) {
        this.clearSelect();
    }

    private void clearSelect() {
        selected = 0;
    }
}
