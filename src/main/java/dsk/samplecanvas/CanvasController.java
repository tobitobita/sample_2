package dsk.samplecanvas;

import dsk.samplecanvas.control.DrawControl;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CanvasController implements Initializable, ControlAdder {

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
    private Pane mainCanvas;

    private static final double LINE_WIDTH = 0.5d;

    private ClickHandler diagramHandler;

    private List<DrawControl> controls = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ghostCanvas.widthProperty().bind(anchorPane.widthProperty());
        ghostCanvas.heightProperty().bind(anchorPane.heightProperty());

        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        context.setStroke(Color.LIGHTSEAGREEN);
        context.setLineWidth(LINE_WIDTH);
        ghostCanvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println("GHOST -> OnMousePressed: " + event);
            mainCanvas.fireEvent(event);
            x = event.getX();
            y = event.getY();
            draggedX = x;
            draggedY = y;
            draggedW = 1d;
            draggedH = 1d;
        });
        ghostCanvas.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("GHOST -> OnMouseReleased: " + event);
            mainCanvas.fireEvent(event);
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
        mainCanvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println("PANE -> OnMousePressed: " + event);
        });
        mainCanvas.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("PANE -> OnMouseReleased: " + event);
            for (DrawControl c : controls) {
                c.setSelected(hitTest(new Rectangle(draggedX, draggedY, draggedW, draggedH), new Rectangle(c.getLayoutX(), c.getLayoutY(), c.getWidth(), c.getHeight())));
                //
            }
        });
    }

    private boolean hitTest(Rectangle source, Rectangle target) {
        return ((source.getX() + source.getWidth() >= target.getX()) && (source.getX() <= target.getX() + target.getWidth())
                && (source.getY() + source.getHeight() >= target.getY()) && (source.getY() <= target.getY() + target.getHeight()));
    }

    private void clearRect() {
        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        double halfLineWidth = LINE_WIDTH / 2;
        context.clearRect(draggedX - halfLineWidth, draggedY - halfLineWidth, draggedW + LINE_WIDTH, draggedH + LINE_WIDTH);
    }

    public void setDiagramHandler(ClickHandler diagramHandler) {
        this.diagramHandler = diagramHandler;
    }

    @Override
    public void onAdded(DrawControl control) {
        this.controls.add(control);
    }
}
