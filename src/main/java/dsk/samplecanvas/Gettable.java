package dsk.samplecanvas;

import java.util.Optional;

public interface Gettable<T> {

    Optional<T> get();
}
