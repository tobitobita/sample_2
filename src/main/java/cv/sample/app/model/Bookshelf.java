package cv.sample.app.model;

import cv.sample.model.ListModel;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;

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
    public Class<?> getListTemplateType() {
        return Book.class;
    }

    @Override
    public ReadOnlyListProperty<Book> listProperty() {
        return list;
    }
}
