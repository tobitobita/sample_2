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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DiagramControl diagram = new DiagramControl();
        AnchorPane.setTopAnchor(diagram, 0d);
        AnchorPane.setRightAnchor(diagram, 0d);
        AnchorPane.setBottomAnchor(diagram, 0d);
        AnchorPane.setLeftAnchor(diagram, 0d);
        anchorPane.getChildren().add(diagram);
//        anchorPane.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
//            System.out.println(event);
//            //ghost.fireEvent(event);
//            event.consume();
//        });
    }
    /*
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

     private Set<ElementControl> selectedControls;
     private ElementControl pressSelected;

     private MouseEventDispatcher controlFactory;

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
     controlFactory.MousePressed(event.getSceneX(), event.getSceneY());
     event.consume();
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
     selectedControls.forEach((ElementControl control) -> {
     control.setSelected(false);
     });
     selectedControls = new HashSet<>();
     }
     moveX.set(event.getSceneX());
     moveY.set(event.getSceneY());
     this.pressSelected.calcRelative(event.getSceneX(), event.getSceneY());
     this.pressSelected.bindMove(moveX, moveY);
     selectedControls.forEach((ElementControl control) -> {
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
     controlFactory.MouseReleased(event.getSceneX(), event.getSceneY());
     Optional<ElementControl> opt = controlFactory.getControl();
     if (opt.isPresent()) {
     ElementControl control = opt.get();
     mainCanvas.getChildren().add(control);
     }
     mode = Mode.SELECT;
     }
     if (mode == Mode.MOVE) {
     this.selectedControls.forEach((ElementControl control) -> {
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
     mode = Mode.SELECT;
     event.consume();
     });
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

     private void clearRect() {
     GraphicsContext context = ghostCanvas.getGraphicsContext2D();
     double halfLineWidth = LINE_WIDTH / 2;
     context.clearRect(draggedX - halfLineWidth, draggedY - halfLineWidth, draggedW + LINE_WIDTH, draggedH + LINE_WIDTH);
     }

     public void setDiagramHandler(MouseEventDispatcher diagramHandler) {
     this.controlFactory = diagramHandler;
     }

     @Override
     public void modeChanged(Mode mode) {
     this.mode = mode;
     }
     */
}
