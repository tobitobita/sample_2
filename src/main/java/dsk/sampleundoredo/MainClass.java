package dsk.sampleundoredo;

public class MainClass {

    public static void main(String[] args) {
        Foo foo = new Foo();
        System.out.printf("Foo\tx: %d, y: %d\n", foo.getX(), foo.getY());
        CommandInvoker invoker = new CommandInvoker();
        Command updateCommand = invoker.CreateUpdateCommand(foo, 1, 2);
        updateCommand.invoke.get();
        System.out.printf("INVOKE\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.undo.get();
        System.out.printf("UNDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.redo.get();
        System.out.printf("REDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.redo.get();
        System.out.printf("REDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.undo.get();
        System.out.printf("UNDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.undo.get();
        System.out.printf("UNDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.undo.get();
        System.out.printf("UNDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.redo.get();
        System.out.printf("REDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
    }
}
