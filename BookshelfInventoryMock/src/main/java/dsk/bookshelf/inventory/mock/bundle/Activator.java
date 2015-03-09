package dsk.bookshelf.inventory.mock.bundle;

import dsk.bookshelf.inventory.api.BookInventory;
import dsk.bookshelf.inventory.mock.BookInventoryMock;
import dsk.bookshelf.inventory.api.utils.BundleUtils;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator, BundleListener {

    private ServiceRegistration<BookInventory> register;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.printf("start bundle.\n");
        this.register = context.registerService(BookInventory.class, new BookInventoryMock(), null);
        context.addBundleListener(this);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.printf("stop bundle.\n");
        context.ungetService(this.register.getReference());
        context.removeBundleListener(this);
        this.register = null;
    }

    @Override
    public void bundleChanged(BundleEvent event) {
        System.out.printf("Mock BundleChanged. => status: %s\n", BundleUtils.toStringStatus(event));
    }
}
