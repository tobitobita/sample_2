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
            ParentInstance ins1 = new ParentInstance(1, "NAME1");
            ChildInstance childIns1 = new ChildInstance(3, "C-NAME1", ins1.getName());
            ins1.addPropertyChangeListener(childIns1);
            ParentInstance ins2 = new ParentInstance(2, "NAME2");
            ChildInstance childIns2 = new ChildInstance(4, "C-NAME2", ins2.getName());
            ins2.addPropertyChangeListener(childIns2);
            ctx.addModel(ins2);
            ctx.addModel(childIns2);

            ins1.setName("NAME1-CHANGE");
            childIns1.setName("C-NAME1-CHANGE");
            ins2.setName("NAME2-CHANGE");
            childIns2.setName("C-NAME2-CHANGE");

            tx.commit();

            System.out.println(ins1);
            System.out.println(childIns1);
            System.out.println(ins2);
            System.out.println(childIns2);
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
            ParentInstance ins1 = new ParentInstance(1, "NAME1");
            ParentInstance ins2 = new ParentInstance(2, "NAME2");
            ChildInstance childIns2 = new ChildInstance(4, "C-NAME2", ins2.getName());
            ins2.addPropertyChangeListener(childIns2);
            ctx.addModel(ins2);
            ctx.addModel(childIns2);

            ins1.setName("NAME1-CHANGE");
            ins2.setName("NAME2-CHANGE-1");
            ins2.setName("NAME2-CHANGE-2");
            ins2.setName("NAME2-CHANGE-3");
            childIns2.setName("C-NAME2-CHANGE-1");
            childIns2.setName("C-NAME2-CHANGE-2");
            childIns2.setName("C-NAME2-CHANGE-3");
            ins2.setName("NAME2-CHANGE-4");

            tx.rollback();

            System.out.println(ins1);
            System.out.println(ins2);
            System.out.println(childIns2);
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
