package dsk.samplecanvas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class CanvasController implements Initializable {

    private double x;
    private double y;
    private double draggedX;
    private double draggedY;
    private double draggedW;
    private double draggedH;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Canvas ghostCanvas;
    @FXML
    private Group mainCanvas;

    private static final double LINE_WIDTH = 0.5d;

    private ClickHandler diagramHandler;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ghostCanvas.widthProperty().bind(anchorPane.widthProperty());
        ghostCanvas.heightProperty().bind(anchorPane.heightProperty());

        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        context.setStroke(Color.LIGHTSEAGREEN);
        context.setLineWidth(LINE_WIDTH);
        ghostCanvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println("OnMousePressed: " + event);
            x = event.getX();
            y = event.getY();
        });
        ghostCanvas.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("OnMouseReleased: " + event);
            this.clearRect();
            if (diagramHandler != null) {
                diagramHandler.onClickDiagram(mainCanvas, event);
            }
        });
        ghostCanvas.setOnMouseDragged((MouseEvent event) -> {
            this.clearRect();
            if (this.x < event.getX()) {
                draggedX = this.x;
                draggedW = event.getX() - this.x;
            } else {
                draggedX = event.getX();
                draggedW = this.x - event.getX();
            }
            if (this.y < event.getY()) {
                draggedY = this.y;
                draggedH = event.getY() - this.y;
            } else {
                draggedY = event.getY();
                draggedH = this.y - event.getY();
            }
            context.strokeRect(draggedX, draggedY, draggedW, draggedH);
        });
    }

    private void clearRect() {
        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        double halfLineWidth = LINE_WIDTH / 2;
        context.clearRect(draggedX - halfLineWidth, draggedY - halfLineWidth, draggedW + LINE_WIDTH, draggedH + LINE_WIDTH);
    }

    public void setDiagramHandler(ClickHandler diagramHandler) {
        this.diagramHandler = diagramHandler;
    }
}
