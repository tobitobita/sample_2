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

public class Launch {

    public void start(String[] args) throws BundleException {
        System.out.printf("LAUNCH: %s\n", Arrays.toString(args));
        FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
        Framework framework = frameworkFactory.newFramework(null);
        framework.start();

        BundleContext context = framework.getBundleContext();
        List<Bundle> bundles = new ArrayList<>();
        bundles.add(context.installBundle("file:///Users/makoto/git/SampleOsgi/BookshelfInventoryApi/target/BookshelfInventoryApi-1.0-SNAPSHOT.jar"));
        bundles.add(context.installBundle("file:///Users/makoto/git/SampleOsgi/BookshelfInventoryMock/target/BookshelfInventoryMock-1.0-SNAPSHOT.jar"));
        bundles.add(context.installBundle("file:///Users/makoto/git/SampleOsgi/BookshelfService/target/BookshelfService-1.0-SNAPSHOT.jar"));
        for (Bundle bundle : bundles) {
            bundle.start();
        }
        framework.stop();
    }

    public static void main(String[] args) {
        try {
            new Launch().start(args);
        } catch (BundleException e) {
            e.printStackTrace();
        }
    }
}
