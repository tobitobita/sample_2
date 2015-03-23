package dsk.metamodel.kernel;

import java.util.List;

public interface Relationship extends Element {

    List<Element> getRelatedElements();
}
