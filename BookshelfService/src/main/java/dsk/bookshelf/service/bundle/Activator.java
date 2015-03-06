package dsk.bookshelf.service.bundle;

import dsk.bookshelf.service.BookshelfService;
import dsk.bookshelf.service.impl.BookshelfServiceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private ServiceRegistration registry;

    @Override
    public void start(BundleContext context) throws Exception {
        this.registry = context.registerService(BookshelfService.class, new BookshelfServiceImpl(context), null);
        this.testService(context);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (registry != null) {
            context.ungetService(registry.getReference());
            registry = null;
        }
    }

    private void testService(BundleContext context) {
        ServiceReference ref = context.getServiceReference(BookshelfService.class);
        BookshelfService service = (BookshelfService) context.getService(ref);
        if (service == null) {
            throw new RuntimeException("登録されていない");
        }
    }
}
