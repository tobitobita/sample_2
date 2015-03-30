package dsk.samplecanvas;

import dsk.samplecanvas.control.DrawControl;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    private Pane mainCanvas;

    private static final double LINE_WIDTH = 0.5d;

    private List<DrawControl> selectedControls;
    private boolean pressSelected;

    private DrawControlFactory controlFactory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ghostCanvas.widthProperty().bind(anchorPane.widthProperty());
        ghostCanvas.heightProperty().bind(anchorPane.heightProperty());

        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        context.setStroke(Color.LIGHTSEAGREEN);
        context.setLineWidth(LINE_WIDTH);
        ghostCanvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println("GHOST -> OnMousePressed: " + event);
//            mainCanvas.fireEvent(event);
            x = event.getX();
            y = event.getY();
            draggedX = x;
            draggedY = y;
            draggedW = 1d;
            draggedH = 1d;
            // 
            Rectangle source = new Rectangle(draggedX, draggedY, draggedW, draggedH);
            pressSelected = getDrawControlStream().anyMatch((DrawControl c) -> {
                return hitTest(
                        source,
                        new Rectangle(c.getCanvasX(), c.getCanvasY(), c.getCanvasWidth(), c.getCanvasHeight()));
            });
            if (pressSelected) {
                System.out.println("HIT");
            } else {
                DrawControl control = controlFactory.createControl();
                // 挿入
                if (control != null) {
                    control.setCanvasX(event.getX());
                    control.setCanvasY(event.getY());
                    mainCanvas.getChildren().add(control);
                }
            }
        });
        ghostCanvas.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("GHOST -> OnMouseReleased: " + event);
//            mainCanvas.fireEvent(event);
            this.clearRect();
            // 選択
            Rectangle source = new Rectangle(draggedX, draggedY, draggedW, draggedH);
            if (pressSelected && !selectedControls.isEmpty()) {

            } else {
                selectedControls = getDrawControlStream().map((DrawControl c) -> {
                    c.setSelected(hitTest(
                            source,
                            new Rectangle(c.getCanvasX(), c.getCanvasY(), c.getCanvasWidth(), c.getCanvasHeight())));
                    return c;
                }).filter((DrawControl c) -> {
                    return c.isSelected();
                }).collect(Collectors.toList());
            }
        });
        ghostCanvas.setOnMouseDragged((MouseEvent event) -> {
            this.clearRect();
            if (pressSelected) {

            } else {
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
            }
        });
//        mainCanvas.setOnMousePressed((MouseEvent event) -> {
//            System.out.println("PANE -> OnMousePressed: " + event);
//        });
//        mainCanvas.setOnMouseReleased((MouseEvent event) -> {
//            System.out.println("PANE -> OnMouseReleased: " + event);            
//        });
    }

    private boolean hitTest(Rectangle source, Rectangle target) {
        return ((source.getX() + source.getWidth() >= target.getX()) && (source.getX() <= target.getX() + target.getWidth())
                && (source.getY() + source.getHeight() >= target.getY()) && (source.getY() <= target.getY() + target.getHeight()));
    }

    private Stream<DrawControl> getDrawControlStream() {
        return mainCanvas.getChildren().stream().filter((Node node) -> {
            return node instanceof DrawControl;
        }).map((Node node) -> (DrawControl) node);
    }

    private void clearRect() {
        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        double halfLineWidth = LINE_WIDTH / 2;
        context.clearRect(draggedX - halfLineWidth, draggedY - halfLineWidth, draggedW + LINE_WIDTH, draggedH + LINE_WIDTH);
    }

    public void setDiagramHandler(DrawControlFactory diagramHandler) {
        this.controlFactory = diagramHandler;
    }
}
