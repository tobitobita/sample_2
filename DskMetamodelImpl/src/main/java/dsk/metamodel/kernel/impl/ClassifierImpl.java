package dsk.metamodel.kernel.impl;

import dsk.metamodel.kernel.Classifier;
import dsk.metamodel.kernel.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClassifierImpl extends TypeImpl implements Classifier {

    private final ObservableList<Property> attributes = FXCollections.observableArrayList();

    @Override
    public Iterable<Property> getAttributes() {
        return attributes;
    }
}
