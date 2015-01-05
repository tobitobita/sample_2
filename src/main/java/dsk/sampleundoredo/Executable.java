package dsk.sampleundoredo;

@FunctionalInterface
public interface Executable<R> {

    R execute();
}
