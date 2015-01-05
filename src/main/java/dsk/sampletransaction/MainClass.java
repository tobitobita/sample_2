/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsk.sampletransaction;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

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
