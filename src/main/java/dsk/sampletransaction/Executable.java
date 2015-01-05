package dsk.sampletransaction;

@FunctionalInterface
public interface Executable<R> {

    R execute();
}
