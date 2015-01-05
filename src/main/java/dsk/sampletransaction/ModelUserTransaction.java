package dsk.sampletransaction;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class ModelUserTransaction implements UserTransaction {

    private boolean transactioning;

    private boolean rollbacking;

    private final CommitDelegate commitDelegate;

    private final RollbackDelegate rollbackDelegate;

    public ModelUserTransaction(CommitDelegate commitDelegate, RollbackDelegate rollbackDelegate) {
        this.commitDelegate = commitDelegate;
        this.rollbackDelegate = rollbackDelegate;
    }

    boolean hasTransaction() {
        return this.transactioning;
    }

    boolean hasRollback() {
        return this.rollbacking;
    }

    @Override
    public void begin() throws NotSupportedException, SystemException {
        if (this.transactioning) {
            throw new SystemException("すでにトランザクションが開始されています。");
        }
        this.transactioning = true;
    }

    @Override
    public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
        this.commitDelegate.commit();
        this.transactioning = false;
    }

    @Override
    public void rollback() throws IllegalStateException, SecurityException, SystemException {
        this.rollbacking = true;
        this.rollbackDelegate.rollback();
        this.rollbacking = false;
        this.transactioning = false;
    }

    @Override
    public void setRollbackOnly() throws IllegalStateException, SystemException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getStatus() throws SystemException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setTransactionTimeout(int seconds) throws SystemException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
