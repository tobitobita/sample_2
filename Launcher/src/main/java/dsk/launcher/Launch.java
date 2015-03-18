package dsk.launcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;

public class Launch {

    public void start(String[] args) {
        System.out.printf("LAUNCH: %s\n", Arrays.toString(args));
        FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
        Map<String, String> conf = new HashMap<>();
        conf.put(Constants.FRAMEWORK_STORAGE_CLEAN, Constants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT);
        conf.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, "javafx.application,javafx.event,javafx.fxml,javafx.scene,javafx.scene.control,javafx.stage");
        Framework framework = frameworkFactory.newFramework(conf);
        framework.adapt(FrameworkStartLevel.class).setInitialBundleStartLevel(4);
        try {
            framework.start();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        framework.adapt(FrameworkStartLevel.class).setStartLevel(4);
        System.out.printf("InitialBundleStartLevel: %d\n", framework.adapt(FrameworkStartLevel.class).getInitialBundleStartLevel());
        System.out.printf("FRAMEWORK START LEVEL: %d\n", framework.adapt(FrameworkStartLevel.class).getStartLevel());

        BundleContext context = framework.getBundleContext();
        List<Bundle> bundles = new LinkedList<>();
        try {
            Bundle b1 = context.installBundle("file:///Users/makoto/git/SampleOsgi/BookshelfService/target/BookshelfService-1.0-SNAPSHOT.jar");
            b1.adapt(BundleStartLevel.class).setStartLevel(3);
            bundles.add(b1);
            {
                Bundle b = context.installBundle("file:///Users/makoto/git/SampleOsgi/BookshelfInventoryApi/target/BookshelfInventoryApi-1.0-SNAPSHOT.jar");
                b.adapt(BundleStartLevel.class).setStartLevel(2);
                bundles.add(b);
            }
            {
                Bundle b = context.installBundle("file:///Users/makoto/git/SampleOsgi/BookshelfInventoryMock/target/BookshelfInventoryMock-1.0-SNAPSHOT.jar");
                b.adapt(BundleStartLevel.class).setStartLevel(2);
                bundles.add(b);
            }
            {
                Bundle b = context.installBundle("file:///Users/makoto/git/SampleOsgi/BookshelfInventoryGui/target/BookshelfInventoryGui-1.0-SNAPSHOT.jar");
                b.adapt(BundleStartLevel.class).setStartLevel(4);
                bundles.add(b);
            }
            bundles.sort((Bundle o1, Bundle o2) -> {
                return o1.adapt(BundleStartLevel.class).getStartLevel() - o2.adapt(BundleStartLevel.class).getStartLevel();
            });
            for (Bundle bundle : bundles) {
                System.out.printf("ID: %d, BUNDLE: %S, START LEVEL: %d\n", bundle.getBundleId(), bundle.getHeaders().get(Constants.BUNDLE_NAME), bundle.adapt(BundleStartLevel.class).getStartLevel());
                bundle.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("STOP FRAMEWORK.");
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
