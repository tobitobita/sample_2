package dsk.metamodel.kernel;

public interface Relationship extends Element {

    Iterable<Element> getRelatedElements();
}
