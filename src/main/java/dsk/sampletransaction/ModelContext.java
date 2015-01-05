package dsk.sampletransaction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import javax.transaction.UserTransaction;

public class ModelContext implements PropertyChangeListener {

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
