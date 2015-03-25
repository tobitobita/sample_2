package dsk.samplecanvas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class CanvasController implements Initializable {

    private double x;
    private double y;
    private double draggedX;
    private double draggedY;
    private double draggedW;
    private double draggedH;
    private GraphicsContext context;

    @FXML
    private Canvas canvas;

    private static final double LINE_WIDTH = 0.5d;

    private Stage toolbox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        context = canvas.getGraphicsContext2D();
        context.setStroke(Color.LIGHTSEAGREEN);
        context.setLineWidth(LINE_WIDTH);
        canvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println("OnMousePressed: " + event);
            x = event.getX();
            y = event.getY();
        });
        canvas.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("OnMouseReleased: " + event);
            this.clearRect();
        });
        canvas.setOnMouseDragged((MouseEvent event) -> {
//            System.out.println("OnMouseDragged: " + event);
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
        canvas.setOnMouseDragEntered((MouseEvent event) -> {
            System.out.println("OnMouseDragEntered: " + event);
        });
        canvas.setOnMouseDragOver((MouseEvent event) -> {
            System.out.println("OnMouseDragOver: " + event);
        });
        canvas.setOnMouseDragExited((MouseEvent event) -> {
            System.out.println("OnMouseDragExited: " + event);
        });
        // menu
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/toolbox.fxml"));
            Scene scene = new Scene(loader.load());
            toolbox = new Stage();
            toolbox.setScene(scene);
            //toolbox.initModality(Modality.WINDOW_MODAL);
            toolbox.setResizable(false);
            toolbox.initStyle(StageStyle.TRANSPARENT);
            ToolboxController controller = loader.getController();
            controller.postInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearRect() {
        double halfLineWidth = LINE_WIDTH / 2;
        context.clearRect(draggedX - halfLineWidth, draggedY - halfLineWidth, draggedW + LINE_WIDTH, draggedH + LINE_WIDTH);
    }

    public void show(Window owner) {
        this.toolbox.initOwner(owner);
        toolbox.setX(owner.getX() - 30d);
        toolbox.setY(owner.getY() + 30d);
        toolbox.show();
    }
}
