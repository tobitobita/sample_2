/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsk.sampletransaction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author makoto
 */
public class MainClass {

    public void start() {
        ModelContext ctx = new ModelContext();
        try {
            ctx.getUserTransaction().begin();
        } catch (NotSupportedException | SystemException e) {
            e.printStackTrace();
        }

        ObjectInstance ins1 = new ObjectInstance(1, "NAME1");
        ObjectInstance ins2 = new ObjectInstance(2, "NAME2");
        ctx.addModel(ins2);

        ins1.setName("NAME1-CHANGE");
        ins2.setName("NAME2-CHANGE");
    }

    public static void main(String[] args) {
        new MainClass().start();
    }
}

class ObjectInstance implements Serializable {

    private static final long serialVersionUID = 1;

    private long id;
    private String name;

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public ObjectInstance() {
    }

    public ObjectInstance(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        long oldId = this.id;
        this.id = id;
        propertyChangeSupport.firePropertyChange("id", oldId, this.id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange("name", oldName, this.name);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}

class ModelContext implements PropertyChangeListener {

    private final ModelUserTransaction transaction = new ModelUserTransaction();

    private final Map<Long, ObjectInstance> modelPool = new HashMap<>();

    public ModelContext() {
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
        System.out.println(evt);
    }
}

class ModelUserTransaction implements UserTransaction {

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
