package dsk.metamodel.kernel;

public interface Element {

    String getId();

    Element getOwner();

    Iterable<Element> getOwnedElements();

    Iterable<Comment> getOwnedComments();

    Iterable<Relationship> getRelationships();
}
