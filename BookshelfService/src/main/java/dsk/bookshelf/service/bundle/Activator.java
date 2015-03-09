package dsk.bookshelf.service.bundle;

import dsk.bookshelf.inventory.api.utils.BundleUtils;
import dsk.bookshelf.service.BookshelfService;
import dsk.bookshelf.service.impl.BookshelfServiceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator, BundleListener {

    private ServiceRegistration registry;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("START Book shelf service.");
        this.registry = context.registerService(BookshelfService.class, new BookshelfServiceImpl(context), null);
        context.addBundleListener(this);
        this.testService(context);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("STOP Book shelf service.");
        if (registry == null) {
            return;
        }
        context.ungetService(registry.getReference());
        context.removeBundleListener(this);
        registry = null;
    }

    private void testService(BundleContext context) {
        System.out.println("testService.");
        ServiceReference ref = context.getServiceReference(BookshelfService.class);
        BookshelfService service = (BookshelfService) context.getService(ref);
        if (service == null) {
            throw new RuntimeException("登録されていないYO!");
        }
    }

    @Override
    public void bundleChanged(BundleEvent event) {
        System.out.printf("Service BundleChanged. => status: %s\n", BundleUtils.toStringStatus(event));
    }
}
