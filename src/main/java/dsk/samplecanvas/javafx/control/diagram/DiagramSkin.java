package dsk.samplecanvas.javafx.control.diagram;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import dsk.samplecanvas.javafx.control.diagram.elements.ElementControl;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DiagramSkin extends BehaviorSkinBase<DiagramControl, DiagramBehavior> {

    /**
     * 範囲選択時に表示するラインの幅
     */
    private static final double LINE_WIDTH = 0.5d;

    private final Canvas ghostCanvas;

    public DiagramSkin(DiagramControl control) {
        super(control, new DiagramBehavior(control));
        ghostCanvas = new Canvas();
        ghostCanvas.widthProperty().bind(control.prefWidthProperty());
        ghostCanvas.heightProperty().bind(control.prefHeightProperty());
        // 本当はlayoutChildrenで行うべきことだが、CanvasのNodeは変更ないためここでのみ追加処理を行う。
        this.getChildren().add(ghostCanvas);
        // ghostCanvasの装飾。
        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        context.setStroke(Color.rgb(103, 176, 255));
        context.setFill(Color.rgb(103, 176, 255, 0.67d));
        context.setLineWidth(LINE_WIDTH);
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        this.layoutInArea(ghostCanvas, contentX, contentY, contentWidth, contentHeight, -1, HPos.LEFT, VPos.TOP);
    }

    void drawDraggedRect(double x, double y, double width, double height) {
        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        context.fillRect(x, y, width, height);
        context.strokeRect(x, y, width, height);
    }

    void clearRect(double x, double y, double width, double height) {
        GraphicsContext context = ghostCanvas.getGraphicsContext2D();
        double halfLineWidth = LINE_WIDTH / 2;
        context.clearRect(x - halfLineWidth, y - halfLineWidth, width + LINE_WIDTH, height + LINE_WIDTH);
    }

    Stream<ElementControl> getDrawControlStream() {
        Iterator<?> it = this.getChildren().stream().collect(Collectors.toCollection(LinkedList::new)).descendingIterator();
        Spliterator<?> spliterator = Spliterators.spliteratorUnknownSize(it, Spliterator.IMMUTABLE);
        return StreamSupport.stream(spliterator, false).filter((Object node) -> {
            return node instanceof ElementControl;
        }).map((Object node) -> (ElementControl) node);
    }
}
