package dsk.samplecanvas.javafx.control.diagram;

import dsk.samplecanvas.Mode;
import dsk.samplecanvas.ModeChangeable;
import dsk.samplecanvas.MouseEventDispatcher;
import dsk.samplecanvas.Settable;
import dsk.samplecanvas.javafx.control.diagram.elements.ElementControl;
import dsk.samplecanvas.javafx.control.diagram.elements.RectControl;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DiagramController implements Initializable, ModeChangeable, Settable<MouseEventDispatcher> {

    private static final double LINE_WIDTH = 0.5d;

    private enum SelectType {

        DRAG,
        CLICK
    }

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Canvas ghostCanvas;
    @FXML
    private Pane elementsPane;

    private double x;
    private double y;
    private double draggedX;
    private double draggedY;
    private double draggedW;
    private double draggedH;
    private final DoubleProperty moveX = new SimpleDoubleProperty(this, "moveX");
    private final DoubleProperty moveY = new SimpleDoubleProperty(this, "moveY");

    private Set<ElementControl> selectedControls = new HashSet<>();
    private ElementControl pressSelectedContorl;

    private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(this, "mode", Mode.SELECT);

    private SelectType selectType = SelectType.CLICK;

    private MouseEventDispatcher dispatcher;

    /* Initializable */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        context.setStroke(Color.rgb(103, 176, 255));
        context.setFill(Color.rgb(103, 176, 255, 0.67d));
        context.setLineWidth(LINE_WIDTH);
        this.ghostCanvas.widthProperty().bind(this.anchorPane.widthProperty());
        this.ghostCanvas.heightProperty().bind(this.anchorPane.heightProperty());

//        this.ghostCanvas.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
//            Optional<ElementControl> overControl = getDrawControlStream().filter((ElementControl c) -> {
//                return hitTest(event.getSceneX(), event.getSceneY(), 1d, 1d, c.getCanvasX() - 3d, c.getCanvasY() - 3d, c.getCanvasWidth() + 6d, c.getCanvasHeight() + 6d);
//            }).findFirst();
//            if (overControl.isPresent()) {
//                System.out.printf("OVER: %s\n", overControl.get().toString());
//                ((ElementSkin) overControl.get().getSkin()).mouseOver(event);
//            }
//        });

        double xx = 0d;
        double yy = 0d;
        for (int i = 0; i < 500; ++i) {
            RectControl rc = new RectControl(i);
            rc.setLayoutX(xx);
            rc.setLayoutY(yy);
            this.elementsPane.getChildren().add(rc);
            if (i % 25 == 0) {
                yy += 25d;
                xx = 0;
            }
            xx += 25d;
        }
    }

    /* ModeChangeable */
    @Override
    public ObjectProperty<Mode> modeProperty() {
        return this.mode;
    }

    @Override
    public void setMode(Mode mode) {
        this.mode.set(mode);
    }

    @Override
    public Mode getMode() {
        return this.mode.get();
    }

    /* Settable<MouseEventDispatcher> */
    @Override
    public void set(MouseEventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    /* ghostCanvas */
    @FXML
    protected void onGhostCanvasMousePressed(MouseEvent event) {
        System.out.println("GHOST -> OnMousePressed: " + event);
        x = event.getSceneX();
        y = event.getSceneY();
        draggedX = event.getSceneX();
        draggedY = event.getSceneY();
        draggedW = 1d;
        draggedH = 1d;
        this.elementsPane.fireEvent(event);
    }

    @FXML
    protected void onGhostCanvasMouseDragged(MouseEvent event) {
        this.clearRect();
        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
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
        if (this.getMode() == Mode.SELECT) {
            context.fillRect(draggedX, draggedY, draggedW, draggedH);
            context.strokeRect(draggedX, draggedY, draggedW, draggedH);
            selectType = SelectType.DRAG;
        }
        this.elementsPane.fireEvent(event);
    }

    @FXML
    protected void onGhostCanvasMouseReleased(MouseEvent event) {
        System.out.println("GHOST -> OnMouseReleased: " + event);
        this.clearRect();
        this.elementsPane.fireEvent(event);
    }

    private void clearRect() {
        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        double halfLineWidth = LINE_WIDTH / 2;
        context.clearRect(draggedX - halfLineWidth, draggedY - halfLineWidth, draggedW + LINE_WIDTH, draggedH + LINE_WIDTH);
    }

    /* elementsPane */
    @FXML
    protected void onElementsPaneMousePressed(MouseEvent event) {
        System.out.printf("PANE -> OnMousePressed: \n");
        if (this.getMode() == Mode.EDIT) {
            this.dispatcher.mouseEvent(MouseEvent.MOUSE_PRESSED, event);
            return;
        }
        Optional<ElementControl> nowSelected = getDrawControlStream().filter((ElementControl c) -> {
            return hitTest(draggedX, draggedY, draggedW, draggedH, c.getCanvasX(), c.getCanvasY(), c.getCanvasWidth(), c.getCanvasHeight());
        }).findFirst();
        if (nowSelected.isPresent()) {
            System.out.println("HIT");
            this.pressSelectedContorl = nowSelected.get();
            if (!selectedControls.contains(this.pressSelectedContorl)) {
                selectedControls.forEach((ElementControl ctrl) -> {
                    ctrl.setSelected(false);
                });
                selectedControls = new HashSet<>();
            }
            moveX.set(event.getSceneX());
            moveY.set(event.getSceneY());
            this.pressSelectedContorl.calcRelative(event.getSceneX(), event.getSceneY());
            this.pressSelectedContorl.bindMove(moveX, moveY);
            selectedControls.forEach((ElementControl ctrl) -> {
                ctrl.calcRelative(event.getSceneX(), event.getSceneY());
                ctrl.bindMove(moveX, moveY);
            });
            this.setMode(Mode.MOVE);
        }
    }

    @FXML
    protected void onElementsPaneMouseDragged(MouseEvent event) {
        if (this.getMode() == Mode.MOVE) {
            this.moveX.set(event.getSceneX());
            this.moveY.set(event.getSceneY());
        }
    }

    @FXML
    protected void onElementsPaneMouseReleased(MouseEvent event) {
        System.out.println("PANE -> OnMouseReleased: " + event);
        if (this.getMode() == Mode.EDIT) {
            this.dispatcher.mouseEvent(MouseEvent.MOUSE_RELEASED, event);
            Optional<ElementControl> elementContorl = this.dispatcher.get();
            if (elementContorl.isPresent()) {
                elementsPane.getChildren().add(elementContorl.get());
            }
            this.setMode(Mode.SELECT);
        }
        if (this.getMode() == Mode.MOVE) {
            this.selectedControls.forEach((ElementControl ctrl) -> {
                ctrl.unbindMove();
            });
            if (this.pressSelectedContorl != null) {
                this.pressSelectedContorl.unbindMove();
                this.pressSelectedContorl.setSelected(true);
            }
            draggedX = event.getSceneX();
            draggedY = event.getSceneY();
            draggedW = 1d;
            draggedH = 1d;
        }
        if (this.pressSelectedContorl == null || selectedControls.isEmpty()) {
            if (selectType == SelectType.DRAG) {
                this.selectedControls = getDrawControlStream().map((ElementControl c) -> {
                    c.setSelected(hitTest(draggedX, draggedY, draggedW, draggedH, c.getCanvasX(), c.getCanvasY(), c.getCanvasWidth(), c.getCanvasHeight()));
                    return c;
                }).filter((ElementControl c) -> {
                    return c.isSelected();
                }).collect(Collectors.toSet());
            } else if (selectType == SelectType.CLICK) {
                getDrawControlStream().forEach(c -> {
                    c.setSelected(false);
                });
                Optional<ElementControl> overControl = getDrawControlStream().filter((ElementControl c) -> {
                    return hitTest(draggedX, draggedY, draggedW, draggedH, c.getCanvasX(), c.getCanvasY(), c.getCanvasWidth(), c.getCanvasHeight());
                }).findFirst();
                if (overControl.isPresent()) {
                    this.selectedControls = new HashSet<>();
                    ElementControl ec = overControl.get();
                    ec.setSelected(true);
                    this.selectedControls.add(overControl.get());
                }
            }
        }
        this.pressSelectedContorl = null;
        this.setMode(Mode.SELECT);
        selectType = SelectType.CLICK;
    }

    private boolean hitTest(double sourceX, double sourceY, double sourceWidth, double sourceHeight, double targetX, double targetY, double targetWidth, double targetHeight) {
        return ((sourceX + sourceWidth > targetX) && (sourceX <= targetX + targetWidth)
                && (sourceY + sourceHeight > targetY) && (sourceY <= targetY + targetHeight));
    }

    private Stream<ElementControl> getDrawControlStream() {
        Iterator<?> it = elementsPane.getChildren().stream().collect(Collectors.toCollection(LinkedList::new)).descendingIterator();
        Spliterator<?> spliterator = Spliterators.spliteratorUnknownSize(it, Spliterator.IMMUTABLE);
        return StreamSupport.stream(spliterator, false).filter((Object node) -> {
            return node instanceof ElementControl;
        }).map((Object node) -> (ElementControl) node);
    }
}
