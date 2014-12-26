package dsk.sampleundoredo;

import java.util.function.Supplier;

class Command<R> {

    public Supplier<R> undo;

    public Supplier<R> redo;

    public Supplier<R> invoke;
}
