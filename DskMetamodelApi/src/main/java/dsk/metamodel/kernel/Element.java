package dsk.metamodel.kernel;

import java.util.List;

public interface Element {

    String getId();

    Element getOwner();

    List<Element> getOwnedElements();

    List<Comment> getOwnedComments();

    List<Relationship> getRelationships();
}
