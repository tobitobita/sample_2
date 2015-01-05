package dsk.sampleundoredo;

public class MainClass {

    public static void main(String[] args) {
        Foo foo = new Foo();
        System.out.printf("Foo\tx: %d, y: %d\n", foo.getX(), foo.getY());
        Command updateCommand = new CommandInvoker().CreateUpdateCommand(foo, 1, 2);
        updateCommand.invoke.execute();
        System.out.printf("INVOKE\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.undo.execute();
        System.out.printf("UNDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.redo.execute();
        System.out.printf("REDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.redo.execute();
        System.out.printf("REDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.undo.execute();
        System.out.printf("UNDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.undo.execute();
        System.out.printf("UNDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.undo.execute();
        System.out.printf("UNDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
        updateCommand.redo.execute();
        System.out.printf("REDO\tx: %d, y: %d\n", foo.getX(), foo.getY());
    }
}
