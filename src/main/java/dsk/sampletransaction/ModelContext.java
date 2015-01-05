package dsk.sampletransaction;

import dsk.sampleundoredo.Command;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.transaction.UserTransaction;

public class ModelContext implements PropertyChangeListener, CommitListener, RollbackListener {

    private final ModelUserTransaction transaction;

    private final Map<Long, ObjectInstance> modelPool = new HashMap<>();

    private final Deque<Command<Void>> commandStack = new LinkedList<>();

    private final RollbackInvoker rollbackInvoker = new RollbackInvoker();

    public ModelContext() {
        this.transaction = new ModelUserTransaction(this, this);
    }

    public void addModel(ObjectInstance objectInstance) {
        this.modelPool.put(objectInstance.getId(), objectInstance);
        objectInstance.addPropertyChangeListener(this);
    }

    public UserTransaction getUserTransaction() {
        return transaction;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!this.transaction.hasTransaction()) {
            throw new RuntimeException("トランザクションが開始されていないのに変更されました。");
        }
        if (this.transaction.hasRollback()) {
            return;
        }
        System.out.println(evt);
//        commandStack.addLast(rollbackInvoker.CreateRollbackCommand((ObjectInstance) evt.getSource()));
    }

    @Override
    public void commit() {
        this.commandStack.clear();
    }

    @Override
    public void rollback() {
        this.commandStack.stream().forEach((cmd) -> {
            cmd.undo.execute();
        });
    }
}
