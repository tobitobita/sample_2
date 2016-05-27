package cv.sample.app.controller;

import cv.sample.app.model.Book;
import cv.sample.model.ListModel;
import cv.sample.model.Model;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
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
        listModel.listProperty().bindContentBidirectional(this.table.getItems());

        this.table.getColumns().add(this.createNumberColumn());
        Arrays.stream(listModel.getListTemplateType().getProperties())
                .map(p -> {
                    final TableColumn col = new TableColumn(p.getDisplayName());
                    col.setCellValueFactory(new PropertyValueFactory(p.getName()));
                    return col;
                }).forEach(column -> {
            this.table.getColumns().add(column);
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
