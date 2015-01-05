package dsk.sampletransaction;

public class Command<R> {

    public Executable<R> undo;

    public Executable<R> redo;

    public Executable<R> invoke;
}
