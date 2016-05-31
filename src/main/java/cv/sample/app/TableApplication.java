package cv.sample.app;

import cv.sample.app.controller.TableController;
import cv.sample.app.model.Author;
import cv.sample.app.model.Book;
import cv.sample.model.Model;
import cv.sample.app.model.Bookshelf;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TableApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(createScene());
        primaryStage.setTitle("サンプルグリッド");
        primaryStage.setWidth(800d);
        primaryStage.setHeight(600d);
        primaryStage.show();
    }

    public Scene createScene() {
        final AnchorPane anchor = new AnchorPane();

        final TableView<Model> table = new TableView<>();
        anchor.getChildren().add(table);

        AnchorPane.setTopAnchor(table, 0d);
        AnchorPane.setRightAnchor(table, 0d);
        AnchorPane.setBottomAnchor(table, 0d);
        AnchorPane.setLeftAnchor(table, 0d);

        final TableController controller = new TableController();
        controller.setAnchorPane(anchor);
        controller.setTable(table);
        final Bookshelf shelf = new Bookshelf();
        controller.setListModel(shelf);
        controller.initialize(null, null);

        initSampleModel(shelf);

        final Scene scene = new Scene(anchor);
        return scene;
    }

    private static void initSampleModel(final Bookshelf shelf) {
        shelf.add(new Book("1234abcd", "我が輩は猫だと思う", new Author("漱石", "冬目", 37), 1280));
    }

    public static void main(String[] args) {
        Application.launch(TableApplication.class, args);
    }
}
