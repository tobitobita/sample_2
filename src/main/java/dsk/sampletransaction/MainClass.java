/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsk.sampletransaction;

import javax.transaction.UserTransaction;

/**
 *
 * @author makoto
 */
public class MainClass {

    public void start() {
        UserTransaction tx = null;
        try {
            ModelContext ctx = new ModelContext();
            tx = ctx.getUserTransaction();
            tx.begin();
            ObjectInstance ins1 = new ObjectInstance(1, "NAME1");
            ObjectInstance ins2 = new ObjectInstance(2, "NAME2");
            ctx.addModel(ins2);

            ins1.setName("NAME1-CHANGE");
            ins2.setName("NAME2-CHANGE");

            tx.commit();

            System.out.println(ins1);
            System.out.println(ins2);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                tx.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("******************");
        try {
            ModelContext ctx = new ModelContext();
            tx = ctx.getUserTransaction();
            tx.begin();
            ObjectInstance ins1 = new ObjectInstance(1, "NAME1");
            ObjectInstance ins2 = new ObjectInstance(2, "NAME2");
            ctx.addModel(ins2);

            ins1.setName("NAME1-CHANGE");
            ins2.setName("NAME2-CHANGE");
            ins2.setName("NAME3-CHANGE");
            ins2.setName("NAME4-CHANGE");

            tx.rollback();

            System.out.println(ins1);
            System.out.println(ins2);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                tx.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new MainClass().start();
    }
}
