package dsk.metamodel.kernel;

/**
 * TODO
 */
public interface Classifier extends Type, NamedElement {

    Iterable<Property> getAttributes();
}
