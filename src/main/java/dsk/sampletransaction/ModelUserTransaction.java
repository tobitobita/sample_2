package dsk.sampletransaction;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class ModelUserTransaction implements UserTransaction {

    private boolean transactioning;

    boolean hasTransaction() {
        return transactioning;
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
        this.transactioning = false;
    }

    @Override
    public void rollback() throws IllegalStateException, SecurityException, SystemException {
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
