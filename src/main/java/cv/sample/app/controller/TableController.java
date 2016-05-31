package cv.sample.app.controller;

import cv.sample.app.model.Author;
import cv.sample.app.model.Book;
import cv.sample.model.ListModel;
import cv.sample.model.Model;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class TableController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Model> table;

    private ListModel listModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.listModel.listProperty().bindContentBidirectional(this.table.getItems());
        this.table.getColumns().add(this.createNumberColumn());
        Book.FIELDS.stream()
                .forEach(f -> {
                    final TableColumn col = createPropertyColumn(f);
                    this.table.getColumns().add(col);
                    if (isObjectProperty(f)) {
                        Author.FIELDS.stream()
                                .forEach(f1 -> {
                                    col.getColumns().add(createPropertyColumn(f1));
                                });
//                        p.getType().propertiesProperty().addListener(this.createChildPropertyChangeListener(col, p));
                    }
                });
//        this.listModel.getListTemplateType().propertiesProperty().addListener(this.createPropertyChangeListener());
    }

    private ListChangeListener<Field> createPropertyChangeListener() {
        return (ListChangeListener.Change<? extends Field> column) -> {
            while (column.next()) {
                if (column.wasPermutated()) {
                } else if (column.wasUpdated()) {
                } else {
                    if (column.wasAdded()) {
                        column.getAddedSubList().stream()
                                .map(f -> {
                                    return createPropertyColumn(f);
                                })
                                .forEach(col -> {
                                    table.getColumns().add(col);
                                });
                    }
                    if (column.wasRemoved()) {
                    }
                }
            }
        };
    }

    private ListChangeListener<Field> createChildPropertyChangeListener(final TableColumn parentColumn) {
        return (ListChangeListener.Change<? extends Field> column) -> {
            while (column.next()) {
                if (column.wasPermutated()) {
                } else if (column.wasUpdated()) {
                } else {
                    if (column.wasAdded()) {
                        column.getAddedSubList().stream()
                                .map(f -> {
                                    return createPropertyColumn(f);
                                })
                                .forEach(col -> {
                                    parentColumn.getColumns().add(col);
                                });
                    }
                    if (column.wasRemoved()) {
                    }
                }
            }
        };
    }

    private boolean isObjectProperty(final Field field) {
        return field.getType().getName().contains("ObjectProperty");
    }

    protected TableColumn createNumberColumn() {
        final TableColumn noCol = new TableColumn("#");
        noCol.setSortable(false);
        noCol.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
                return new ReadOnlyObjectWrapper<>(Integer.toString(1 + table.getItems().indexOf(param.getValue())));
            }
        });
        return noCol;
    }

    protected TableColumn createPropertyColumn(final Field field) {
        final TableColumn col = new TableColumn(field.getName().toUpperCase());
        col.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
                field.setAccessible(true);
                try {
                    final Optional<Field> ff = Book.FIELDS.stream().filter(f -> f.equals(field)).findFirst();
                    if (ff.isPresent()) {
                        return (ObservableValue<String>) field.get(param.getValue());
                    }
                    final Optional<Field> fff = Author.FIELDS.stream().filter(f -> f.equals(field)).findFirst();
                    if (fff.isPresent()) {
                        return (ObservableValue<String>) field.get(param.getValue().getAuthor());
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                return new ReadOnlyObjectWrapper<>("no value");
            }
        });
        return col;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public void setTable(TableView<Model> table) {
        this.table = table;
    }

    public void setListModel(ListModel listModel) {
        this.listModel = listModel;
    }
}
