package dsk.samplecanvas.javafx.control.canvas.layer;

import dsk.samplecanvas.Mode;
import dsk.samplecanvas.javafx.control.canvas.drawer.DrawControl;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class LayerSkin implements Skin<LayerControl> {

    private final LayerControl control;

    private Canvas canvas;

    private double draggedX;
    private double draggedY;
    private double draggedW;
    private double draggedH;

    private final DoubleProperty moveX = new SimpleDoubleProperty(this, "moveX");
    private final DoubleProperty moveY = new SimpleDoubleProperty(this, "moveY");

    private Pane mainCanvas;

    private Mode mode = Mode.SELECT;

    private Set<DrawControl> selectedControls;
    private DrawControl pressSelected;

//    private DrawControlFactory controlFactory;

    public LayerSkin(LayerControl control) {
        this.control = control;
        this.initializa();
    }

    private void initializa() {
        this.canvas = new Canvas();
        this.canvas.widthProperty().bind(this.control.widthProperty());
        this.canvas.heightProperty().bind(this.control.heightProperty());

        selectedControls = new HashSet<>();
        mainCanvas.setOnMousePressed((MouseEvent event) -> {
            System.out.printf("PANE -> OnMousePressed: \n");
//            ghostCanvas.fireEvent(event);
            if (mode == Mode.EDIT) {
//                controlFactory.setByPressed(event.getSceneX(), event.getSceneY());
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
//            ghostCanvas.fireEvent(event);
            if (mode == Mode.MOVE) {
                this.moveX.set(event.getSceneX());
                this.moveY.set(event.getSceneY());
            }
//            event.consume();
        });
        mainCanvas.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("PANE -> OnMouseReleased: " + event);
//            ghostCanvas.fireEvent(event);
            if (mode == Mode.EDIT) {
//                controlFactory.setByReleased(event.getSceneX(), event.getSceneY());
//                Optional<DrawControl> opt = controlFactory.getControl();
//                if (opt.isPresent()) {
//                    DrawControl control = opt.get();
//                    mainCanvas.getChildren().add(control);
//                }
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

    @Override
    public LayerControl getSkinnable() {
        return this.control;
    }

    @Override
    public Node getNode() {
        return this.canvas;
    }

    @Override
    public void dispose() {
    }
}
