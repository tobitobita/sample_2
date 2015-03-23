package dsk.metamodel.kernel;

import java.util.List;

public interface Comment extends Element {

    String getBody();

    void setBody(String body);

    List<Element> getAnnotatedElements();
}
