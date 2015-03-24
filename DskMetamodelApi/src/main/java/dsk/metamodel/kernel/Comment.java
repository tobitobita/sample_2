package dsk.metamodel.kernel;

public interface Comment extends Element {

    String getBody();

    void setBody(String body);

    Iterable<Element> getAnnotatedElements();
}
