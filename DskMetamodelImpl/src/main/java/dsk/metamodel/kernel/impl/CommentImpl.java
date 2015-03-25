package dsk.metamodel.kernel.impl;

import dsk.metamodel.kernel.Comment;
import dsk.metamodel.kernel.Element;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommentImpl extends ElementImpl implements Comment {

    private String body;

    private final ObservableList<Element> annotatedElements = FXCollections.observableArrayList();

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public Iterable<Element> getAnnotatedElements() {
        return annotatedElements;
    }
}
