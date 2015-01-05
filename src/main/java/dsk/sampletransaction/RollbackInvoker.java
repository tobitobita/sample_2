package dsk.sampletransaction;

import dsk.sampleundoredo.Command;
import java.beans.PropertyChangeEvent;

public class RollbackInvoker {

    public Command<Void> CreateRollbackCommand(PropertyChangeEvent event) {
        Object oldValue = event.getOldValue();
        Command<Void> cmd = new Command();
        cmd.invoke = () -> {
            return null;
        };
        cmd.redo = () -> {
            return null;
        };
        cmd.undo = () -> {
//            event.getSource().
            return null;
        };
        return cmd;
    }
}
