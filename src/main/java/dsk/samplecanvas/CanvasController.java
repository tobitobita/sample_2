package dsk.samplecanvas;

import dsk.samplecanvas.control.DrawControl;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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

public class CanvasController implements Initializable, ModeChanged {

    // 共通
    private double x;
    private double y;
    private Mode mode = Mode.SELECT;
    // ghost
    private static final double LINE_WIDTH = 0.5d;
    private double draggedX;
    private double draggedY;
    private double draggedW;
    private double draggedH;
    @FXML
    private Canvas ghostCanvas;
    @FXML
    private AnchorPane anchorPane;
    // main
    private final DoubleProperty moveX = new SimpleDoubleProperty(this, "moveX");
    private final DoubleProperty moveY = new SimpleDoubleProperty(this, "moveY");
    @FXML
    private Pane mainCanvas;

    private Set<DrawControl> selectedControls;
    private DrawControl pressSelected;

    private DrawControlFactory controlFactory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ghost
        ghostCanvas.widthProperty().bind(anchorPane.widthProperty());
        ghostCanvas.heightProperty().bind(anchorPane.heightProperty());
        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        context.setStroke(Color.LIGHTSEAGREEN);
        context.setLineWidth(LINE_WIDTH);
        ghostCanvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println("GHOST -> OnMousePressed: " + event);
            x = event.getSceneX();
            y = event.getSceneY();
            draggedX = event.getSceneX();
            draggedY = event.getSceneY();
            draggedW = 1d;
            draggedH = 1d;
            // 
            event.consume();
        });
        ghostCanvas.setOnMouseDragged((MouseEvent event) -> {
            this.clearRect();
            if (this.x < event.getSceneX()) {
                draggedX = this.x;
                draggedW = event.getSceneX() - this.x;
            } else {
                draggedX = event.getSceneX();
                draggedW = this.x - event.getSceneX();
            }
            if (this.y < event.getSceneY()) {
                draggedY = this.y;
                draggedH = event.getSceneY() - this.y;
            } else {
                draggedY = event.getSceneY();
                draggedH = this.y - event.getSceneY();
            }
            if (mode == Mode.SELECT) {
                context.strokeRect(draggedX, draggedY, draggedW, draggedH);
            }
            event.consume();
        });
        ghostCanvas.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("GHOST -> OnMouseReleased: " + event);
            this.clearRect();
            event.consume();
        });
        // main
        selectedControls = new HashSet<>();
        mainCanvas.setOnMousePressed((MouseEvent event) -> {
            System.out.printf("PANE -> OnMousePressed: %s\n", mode);
            ghostCanvas.fireEvent(event);
            if (mode == Mode.EDIT) {
                controlFactory.setByPressed(event.getSceneX(), event.getSceneY());
                event.consume();
                return;
            }
            Rectangle source = new Rectangle(draggedX, draggedY, draggedW, draggedH);
            Optional<DrawControl> nowSelected = getDrawControlStream().filter((DrawControl c) -> {
                return hitTest(
                        source,
                        new Rectangle(c.getCanvasX(), c.getCanvasY(), c.getCanvasWidth(), c.getCanvasHeight()));
            }).findFirst();
            if (nowSelected.isPresent()) {
                System.out.println("HIT");
                this.pressSelected = nowSelected.get();
                if (!selectedControls.contains(this.pressSelected)) {
                    selectedControls.forEach((DrawControl control) -> {
                        control.setSelected(false);
                    });
                    selectedControls = new HashSet<>();
                }
                moveX.set(event.getSceneX());
                moveY.set(event.getSceneY());
                this.pressSelected.calcRelative(event.getSceneX(), event.getSceneY());
                this.pressSelected.bindMove(moveX, moveY);
                selectedControls.forEach((DrawControl control) -> {
                    control.calcRelative(event.getSceneX(), event.getSceneY());
                    control.bindMove(moveX, moveY);
                });
                mode = Mode.MOVE;
            }
            event.consume();
        });
        mainCanvas.setOnMouseDragged((MouseEvent event) -> {
            ghostCanvas.fireEvent(event);
            if (mode == Mode.MOVE) {
                this.moveX.set(event.getSceneX());
                this.moveY.set(event.getSceneY());
            }
            event.consume();
        });
        mainCanvas.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("PANE -> OnMouseReleased: " + event);
            ghostCanvas.fireEvent(event);
            if (mode == Mode.EDIT) {
                controlFactory.setByReleased(event.getSceneX(), event.getSceneY());
                Optional<DrawControl> opt = controlFactory.getControl();
                if (opt.isPresent()) {
                    DrawControl control = opt.get();
                    mainCanvas.getChildren().add(control);
                }
                mode = Mode.SELECT;
            }
            if (mode == Mode.MOVE) {
                this.selectedControls.forEach((DrawControl control) -> {
                    control.unbindMove();
                });
                if (this.pressSelected != null) {
                    this.pressSelected.unbindMove();
                    this.pressSelected.setSelected(true);
                }
                draggedX = event.getSceneX();
                draggedY = event.getSceneY();
                draggedW = 1d;
                draggedH = 1d;
            }
            if (this.pressSelected == null || selectedControls.isEmpty()) {
                Rectangle source = new Rectangle(draggedX, draggedY, draggedW, draggedH);
                selectedControls = getDrawControlStream().map((DrawControl c) -> {
                    c.setSelected(hitTest(
                            source,
                            new Rectangle(c.getCanvasX(), c.getCanvasY(), c.getCanvasWidth(), c.getCanvasHeight())));
                    return c;
                }).filter((DrawControl c) -> {
                    return c.isSelected();
                }).collect(Collectors.toSet());
            }
            this.pressSelected = null;
            mode = Mode.SELECT;
            event.consume();
        });
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

    @Override
    public void modeChanged(Mode mode) {
        this.mode = mode;
    }
}
