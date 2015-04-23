package dsk.samplejavafx8;

import dsk.samplejavafx8.controls.SampleControl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class FXMLDocumentController implements Initializable {

    private double labelX;
    private double labelY;
    private double selectedObjX;
    private double selectedObjY;

    private DoubleProperty moveX = new SimpleDoubleProperty(this, "moveX");
    private DoubleProperty moveY = new SimpleDoubleProperty(this, "moveY");

    @FXML
    private Pane pane;

    @FXML
    private Label label;

    @FXML
    private Circle circle;
    @FXML
    private Circle selectedObj;
    @FXML
    private Canvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SampleControl c = new SampleControl();
        c.setPrefWidth(40d);
        c.setPrefHeight(30d);
        c.setLayoutX(100d);
        c.setLayoutY(100d);
        this.pane.getChildren().add(c);
    }

    @FXML
    protected void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
//        event.consume();
    }

    @FXML
    protected void onLabelDragDetected(MouseEvent event) {
        System.out.println("onLabelDragDetected");
        label.startFullDrag();
//        event.consume();
    }

    @FXML
    protected void onLabelMousePressed(MouseEvent event) {
        System.out.println("onLabelMousePressed");
        label.setMouseTransparent(true);
        moveX.set(event.getSceneX());
        moveY.set(event.getSceneY());
        labelX = label.getLayoutX() - event.getSceneX();
        labelY = label.getLayoutY() - event.getSceneY();
        selectedObjX = selectedObj.getLayoutX() - event.getSceneX();
        selectedObjY = selectedObj.getLayoutY() - event.getSceneY();
        System.out.printf("selected, X: %f, Y: %f\n", selectedObjX, selectedObjY);

//        label.layoutXProperty().bind(moveX);
//        label.layoutYProperty().bind(moveY);
//        event.consume();
    }

    @FXML
    protected void onLabelMouseDragged(MouseEvent event) {
        System.out.printf("x: %f, y: %f\n", event.getSceneX(), event.getSceneY());
        moveX.set(event.getSceneX());
        moveY.set(event.getSceneY());
        label.setLayoutX(event.getSceneX() + labelX);
        label.setLayoutY(event.getSceneY() + labelY);
        selectedObj.setLayoutX(event.getSceneX() + selectedObjX);
        selectedObj.setLayoutY(event.getSceneY() + selectedObjY);
//        event.consume();
    }

    @FXML
    protected void onLabelMouseReleased(MouseEvent event) {
        System.out.println("onLabelMouseReleased");
        label.setMouseTransparent(false);
//        label.layoutXProperty().unbind();
//        label.layoutYProperty().unbind();
//        event.consume();
    }

    @FXML
    protected void onCircleMouseDragOver(MouseDragEvent event) {
        System.out.println("onCircleMouseDragOver");
//        event.consume();
    }

    @FXML
    protected void onCanvasMousePressed(MouseEvent event) {
        System.out.println("onCanvasMousePressed");
        pane.fireEvent(event);
//        event.consume();
    }

    @FXML
    protected void onCanvasMouseReleased(MouseEvent event) {
        System.out.println("onCanvasMouseReleased");
        pane.fireEvent(event);
//        event.consume();
    }

}
