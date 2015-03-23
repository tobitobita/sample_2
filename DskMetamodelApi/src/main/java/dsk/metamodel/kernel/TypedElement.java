package dsk.metamodel.kernel;

public interface TypedElement extends NamedElement {

    Type getType();

    void setType(Type type);
}
