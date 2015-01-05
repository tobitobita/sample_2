package dsk.sampletransaction;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.PropertyUtils;

public class RollbackInvoker {

    public Command<Void> CreateRollbackCommand(PropertyChangeEvent event) {
        Object source = event.getSource();
        String propertyName = event.getPropertyName();
        Object prevValue = event.getOldValue();
        Command<Void> cmd = new Command<>();
        cmd.undo = () -> {
            try {
                System.out.printf("Rollback -> source: %s, proertyName: %s, prevValue: %s\n", source.toString(), propertyName, prevValue);
                PropertyUtils.setSimpleProperty(source, propertyName, prevValue);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        };
        return cmd;
    }
}
