package dsk.samplecanvas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class FXMLController implements Initializable {

    private double x;
    private double y;
    private GraphicsContext context;

    @FXML
    private Canvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        context = canvas.getGraphicsContext2D();
        context.setStroke(Color.LIGHTSEAGREEN);
        canvas.setOnMousePressed((MouseEvent event) -> {
            System.out.println("OnMousePressed: " + event);
            x = event.getX();
            y = event.getY();
        });
        canvas.setOnMouseReleased((MouseEvent event) -> {
            System.out.println("OnMouseReleased: " + event);
            context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });
        canvas.setOnMouseDragged((MouseEvent event) -> {
//            System.out.println("OnMouseDragged: " + event);
            context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            double x;
            double y;
            double w;
            if (this.x < event.getX()) {
                x = this.x;
                w = event.getX() - this.x;
            } else {
                x = event.getX();
                w = this.x - event.getX();
            }
            double h;
            if (this.y < event.getY()) {
                y = this.y;
                h = event.getY() - this.y;
            } else {
                y = event.getY();
                h = this.y - event.getY();
            }
            System.out.printf("x:%f, y:%f, w:%f, h:%f\n", x, y, w, h);
            context.strokeRect(x, y, w, h);
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
    }
}
