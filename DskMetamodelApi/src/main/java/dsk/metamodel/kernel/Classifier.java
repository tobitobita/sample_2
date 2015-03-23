package dsk.metamodel.kernel;

import java.util.List;

/**
 * TODO
 */
public interface Classifier extends Type, NamedElement {

    List<Property> getAttributes();
}
