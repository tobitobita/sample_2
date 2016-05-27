package cv.sample.app.model;

import cv.sample.model.ListModel;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import cv.sample.metaModel.Class;

public class Bookshelf implements ListModel<Book> {

    private final ReadOnlyListProperty<Book> list = new ReadOnlyListWrapper<>(this, "list", FXCollections.observableArrayList());

    @Override
    public void add(final Book book) {
        this.list.add(book);
    }

    @Override
    public void remove(final Book book) {
        this.list.remove(book);
    }

    @Override
    public Class getListTemplateType() {
        return Book.META_MODEL;
    }

    @Override
    public Class getType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReadOnlyListProperty<Book> listProperty() {
        return list;
    }
}
