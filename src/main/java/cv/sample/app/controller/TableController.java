package cv.sample.app.controller;

import cv.sample.app.model.Book;
import cv.sample.metaModel.Property;
import cv.sample.model.ListModel;
import cv.sample.model.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
        this.listModel.getListTemplateType().propertiesProperty().addListener((ListChangeListener.Change<? extends Property> c) -> {
            while (c.next()) {
                if (c.wasPermutated()) {
                } else if (c.wasUpdated()) {
                } else {
                    if (c.wasAdded()) {
                        c.getAddedSubList().stream().map(p -> {
                            final TableColumn col = new TableColumn(p.getDisplayName());
                            col.setCellValueFactory(new PropertyValueFactory(p.getName()));
                            return col;
                        }).forEach(col -> {
                            table.getColumns().add(col);
                        });
                    }
                    if (c.wasRemoved()) {
                    }
                }
            }
        });
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
