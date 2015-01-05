package dsk.sampleundoredo;

public class CommandInvoker {

    public Command<Void> CreateUpdateCommand(Foo foo, int x, int y) {
        int prevX = foo.getX();
        int prevY = foo.getY();
        Command<Void> cmd = new Command<>();
        cmd.invoke = () -> {
            System.out.println("invoke");
            foo.setX(x);
            foo.setY(y);
            return null;
        };
        cmd.redo = () -> {
            System.out.println("redo");
            foo.setX(x);
            foo.setY(y);
            return null;
        };
        cmd.undo = () -> {
            System.out.println("undo");
            foo.setX(prevX);
            foo.setY(prevY);
            return null;
        };
        return cmd;
    }
}
