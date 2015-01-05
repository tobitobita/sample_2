package dsk.sampletransaction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import javax.transaction.UserTransaction;

public class ModelContext implements PropertyChangeListener, CommitDelegate, RollbackDelegate {

    private final ModelUserTransaction transaction;

    private final Map<Long, ParentInstance> modelPool = new HashMap<>();

    private final Deque<Command<Void>> commandStack = new ArrayDeque<>();

    private final RollbackInvoker rollbackInvoker = new RollbackInvoker();

    public ModelContext() {
        this.transaction = new ModelUserTransaction(this, this);
    }

    public void addModel(ParentInstance objectInstance) {
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
        commandStack.push(rollbackInvoker.CreateRollbackCommand(evt));
    }

    @Override
    public void commit() {
        this.commandStack.clear();
    }

    @Override
    public void rollback() {
        while (true) {
            Command<Void> cmd = this.commandStack.poll();
            if (cmd == null) {
                return;
            }
            cmd.undo.execute();
        }
    }
}
