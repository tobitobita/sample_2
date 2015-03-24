package dsk.metamodel.kernel.impl;

import dsk.metamodel.kernel.Comment;
import dsk.metamodel.kernel.Element;
import dsk.metamodel.kernel.Relationship;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ElementImpl implements Element {

    private final StringProperty id = new SimpleStringProperty(this, "id");

    private final ObjectProperty<Element> owner = new SimpleObjectProperty<>(this, "owner");

    private final ObservableList<Element> ownedElements = FXCollections.observableArrayList();

    private final ObservableList<Comment> ownedComments = FXCollections.observableArrayList();

    private final ObservableList<Relationship> relationships = FXCollections.observableArrayList();

    @Override
    public String getId() {
        return this.id.get();
    }

    @Override
    public Element getOwner() {
        return this.owner.get();
    }

    @Override
    public Iterable<Element> getOwnedElements() {
        return this.ownedElements;
    }

    @Override
    public Iterable<Comment> getOwnedComments() {
        return this.ownedComments;
    }

    @Override
    public Iterable<Relationship> getRelationships() {
        return this.relationships;
    }

    public StringProperty idProperty() {
        return this.id;
    }

    public ObjectProperty<Element> ownerProperty() {
        return this.owner;
    }

    public ObservableList<Element> ownedElementsProperty() {
        return this.ownedElements;
    }

    public ObservableList<Comment> ownedCommentsProperty() {
        return this.ownedComments;
    }

    public ObservableList<Relationship> relationshipsProperty() {
        return this.relationships;
    }
}
