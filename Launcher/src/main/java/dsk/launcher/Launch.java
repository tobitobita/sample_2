package dsk.launcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;

public class Launch {

    public void start(String[] args) {
        System.out.printf("LAUNCH: %s\n", Arrays.toString(args));
        FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
        Framework framework = frameworkFactory.newFramework(null);
        try {
            framework.start();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        framework.adapt(FrameworkStartLevel.class).setStartLevel(3);

        BundleContext context = framework.getBundleContext();
        List<Bundle> bundles = new ArrayList<>();
        try {
            {
                Bundle b = context.installBundle("file:///Users/PAPRIKA/git/SampleOsgi/BookshelfInventoryApi/target/BookshelfInventoryApi-1.0-SNAPSHOT.jar");
                b.adapt(BundleStartLevel.class).setStartLevel(2);
                bundles.add(b);
            }

            {
                Bundle b = context.installBundle("file:///Users/PAPRIKA/git/SampleOsgi/BookshelfInventoryMock/target/BookshelfInventoryMock-1.0-SNAPSHOT.jar");
                b.adapt(BundleStartLevel.class).setStartLevel(2);
                bundles.add(b);
            }

            {
                Bundle b = context.installBundle("file:///Users/PAPRIKA/git/SampleOsgi/BookshelfService/target/BookshelfService-1.0-SNAPSHOT.jar");
                b.adapt(BundleStartLevel.class).setStartLevel(3);
                bundles.add(b);
            }
            for (Bundle bundle : bundles) {
                System.out.printf("START LEVEL: %d\n", bundle.adapt(BundleStartLevel.class).getStartLevel());
                bundle.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                framework.stop();
            } catch (BundleException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Launch().start(args);
    }
}
