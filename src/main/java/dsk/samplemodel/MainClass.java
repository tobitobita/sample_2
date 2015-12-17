/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsk.samplemodel;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

/**
 *
 * @author makoto
 */
public class MainClass {

	public static void main(String[] args) {
		EcoreFactory coreFactory = EcoreFactory.eINSTANCE;
		EcorePackage corePackage = EcorePackage.eINSTANCE;

		EClass userType = coreFactory.createEClass();
		userType.setName("ユーザー");

		EAttribute userName = coreFactory.createEAttribute();
		userName.setName("名前");
		userName.setEType(corePackage.getEString());

		userType.getEStructuralFeatures().add(userName);

		EClass orgType = coreFactory.createEClass();
		orgType.setName("組織");

		EReference orgUser = coreFactory.createEReference();
		orgUser.setEType(userType);
		orgUser.setName("社員");
		System.out.println(orgUser);

		orgType.getEStructuralFeatures().add(orgUser);

		EPackage pkg = coreFactory.createEPackage();
		pkg.setName("sample");
		pkg.setNsPrefix("sample");
		pkg.setNsURI("http://sample.sample/core");
		pkg.getEClassifiers().add(orgType);
		pkg.getEClassifiers().add(userType);

		EFactory factory = pkg.getEFactoryInstance();

		EObject user = factory.create(userType);
		user.eAdapters().add(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				System.out.printf("user instance, %s\n", msg.getFeature());
				System.out.printf("user instance, %s\n", msg.getOldStringValue());
				System.out.printf("user instance, %s\n", msg.getNewStringValue());
			}
		});
		user.eSet(userName, "🍣太郎");
		user.eSet(userName, "🍣次郎");

		EObject org = factory.create(orgType);
		org.eAdapters().add(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				System.out.printf("org instance, %s\n", msg.getFeature());
				System.out.printf("org instance, %s\n", msg.getOldStringValue());
				System.out.printf("org instance, %s\n", msg.getNewStringValue());
			}
		});
		org.eSet(orgUser, user);

		AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(new EcoreAdapterFactory(), new BasicCommandStack());

		Command cmd = SetCommand.create(domain, user, userName, "ユーザー名２");

		System.out.println("execute");
		domain.getCommandStack().execute(cmd);
		System.out.println("undo");
		domain.getCommandStack().undo();
		System.out.println("redo");
		domain.getCommandStack().redo();
		
//		domain.
	}
}
