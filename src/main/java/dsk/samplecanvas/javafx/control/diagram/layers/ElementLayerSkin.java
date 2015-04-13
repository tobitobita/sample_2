package dsk.samplecanvas.javafx.control.diagram.layers;

import dsk.samplecanvas.Mode;
import dsk.samplecanvas.MouseEventDispatcher;
import dsk.samplecanvas.javafx.control.diagram.elements.ElementControl;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class ElementLayerSkin implements Skin<ElementLayerControl>, LayerBehaviour {

    private final ElementLayerControl control;

    private double x;
    private double y;
    private double draggedX;
    private double draggedY;
    private double draggedW;
    private double draggedH;

    private final DoubleProperty moveX = new SimpleDoubleProperty(this, "moveX");
    private final DoubleProperty moveY = new SimpleDoubleProperty(this, "moveY");

    private Pane mainCanvas;

    private Set<ElementControl> selectedControls;
    private ElementControl pressSelected;

    private MouseEventDispatcher dispatcher;

    public ElementLayerSkin(ElementLayerControl control) {
        this.control = control;
        this.initializa();
    }

    private void initializa() {
        mainCanvas = new Pane();
        selectedControls = new HashSet<>();
    }

    @Override
    public ElementLayerControl getSkinnable() {
        return this.control;
    }

    @Override
    public Node getNode() {
        return this.mainCanvas;
    }

    @Override
    public void dispose() {
    }

    @Override
    public LayerBehaviour getLayerBehaviour() {
        return this;
    }

    @Override
    public void mouseEvent(EventType<MouseEvent> eventType, MouseEvent event) {
        if (eventType == MouseEvent.MOUSE_PRESSED) {
            this.mousePressed(event);
        } else if (eventType == MouseEvent.MOUSE_DRAGGED) {
            this.mouseDragged(event);
        } else if (eventType == MouseEvent.MOUSE_RELEASED) {
            this.mouseReleased(event);
        }
    }

    @Override
    public void mouseDragEvent(EventType<MouseDragEvent> eventType, MouseDragEvent event) {
        this.mainCanvas.getChildren().forEach(node -> ((ElementControl) node).mouseDragEvent(eventType, event));
    }

    private void mousePressed(MouseEvent event) {
        System.out.printf("PANE -> OnMousePressed: \n");
        x = event.getSceneX();
        y = event.getSceneY();
        draggedX = event.getSceneX();
        draggedY = event.getSceneY();
        draggedW = 1d;
        draggedH = 1d;
        if (this.control.getMode() == Mode.EDIT) {
            this.dispatcher.mousePressed(event.getSceneX(), event.getSceneY());
            return;
        }
        Rectangle source = new Rectangle(draggedX, draggedY, draggedW, draggedH);
        Optional<ElementControl> nowSelected = getDrawControlStream().filter((ElementControl c) -> {
            return hitTest(
                    source,
                    new Rectangle(c.getCanvasX(), c.getCanvasY(), c.getCanvasWidth(), c.getCanvasHeight()));
        }).findFirst();
        if (nowSelected.isPresent()) {
            System.out.println("HIT");
            this.pressSelected = nowSelected.get();
            if (!selectedControls.contains(this.pressSelected)) {
                selectedControls.forEach((ElementControl ctrl) -> {
                    ctrl.setSelected(false);
                });
                selectedControls = new HashSet<>();
            }
            moveX.set(event.getSceneX());
            moveY.set(event.getSceneY());
            this.pressSelected.calcRelative(event.getSceneX(), event.getSceneY());
            this.pressSelected.bindMove(moveX, moveY);
            selectedControls.forEach((ElementControl ctrl) -> {
                ctrl.calcRelative(event.getSceneX(), event.getSceneY());
                ctrl.bindMove(moveX, moveY);
            });
            this.control.setMode(Mode.MOVE);
        }
    }

    private void mouseDragged(MouseEvent event) {
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
        if (this.control.getMode() == Mode.MOVE) {
            this.moveX.set(event.getSceneX());
            this.moveY.set(event.getSceneY());
        }
    }

    private void mouseReleased(MouseEvent event) {
        System.out.println("PANE -> OnMouseReleased: " + event);
        if (this.control.getMode() == Mode.EDIT) {
            this.dispatcher.mouseReleased(event.getSceneX(), event.getSceneY());
            Optional<ElementControl> opt = this.dispatcher.getControl();
            if (opt.isPresent()) {
                mainCanvas.getChildren().add(opt.get());
            }
            this.control.setMode(Mode.SELECT);
        }
        if (this.control.getMode() == Mode.MOVE) {
            this.selectedControls.forEach((ElementControl ctrl) -> {
                ctrl.unbindMove();
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
            selectedControls = getDrawControlStream().map((ElementControl c) -> {
                c.setSelected(hitTest(
                        source,
                        new Rectangle(c.getCanvasX(), c.getCanvasY(), c.getCanvasWidth(), c.getCanvasHeight())));
                return c;
            }).filter((ElementControl c) -> {
                return c.isSelected();
            }).collect(Collectors.toSet());
        }
        this.pressSelected = null;
        this.control.setMode(Mode.SELECT);
    }

    private boolean hitTest(Rectangle source, Rectangle target) {
        return ((source.getX() + source.getWidth() >= target.getX()) && (source.getX() <= target.getX() + target.getWidth())
                && (source.getY() + source.getHeight() >= target.getY()) && (source.getY() <= target.getY() + target.getHeight()));
    }

    private Stream<ElementControl> getDrawControlStream() {
        return mainCanvas.getChildren().stream().filter((Node node) -> {
            return node instanceof ElementControl;
        }).map((Node node) -> (ElementControl) node);
    }

    public void setMouseEventDispatcher(MouseEventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
