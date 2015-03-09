package dsk.bookshelf.inventory.api.utils;

import org.osgi.framework.BundleEvent;

public class BundleUtils {

    public static String toStringStatus(BundleEvent event) {
        String name;
        switch (event.getType()) {
            case BundleEvent.INSTALLED:
                name = "INSTALLED";
                break;
            case BundleEvent.LAZY_ACTIVATION:
                name = "LAZY_ACTIVATION";
                break;
            case BundleEvent.RESOLVED:
                name = "RESOLVED";
                break;
            case BundleEvent.STARTED:
                name = "STARTED";
                break;
            case BundleEvent.STARTING:
                name = "STARTING";
                break;
            case BundleEvent.STOPPED:
                name = "STOPPED";
                break;
            case BundleEvent.STOPPING:
                name = "STOPPING";
                break;
            case BundleEvent.UNINSTALLED:
                name = "UNINSTALLED";
                break;
            case BundleEvent.UNRESOLVED:
                name = "UNRESOLVED";
                break;
            case BundleEvent.UPDATED:
                name = "UPDATED";
                break;
            default:
                name = null;
                break;
        }
        return name;
    }
}
