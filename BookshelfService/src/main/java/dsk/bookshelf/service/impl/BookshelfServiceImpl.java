package dsk.bookshelf.service.impl;

import dsk.bookshelf.inventory.api.Book;
import dsk.bookshelf.inventory.api.BookInventory;
import dsk.bookshelf.inventory.api.exception.BookAlreadyExistsException;
import dsk.bookshelf.inventory.api.exception.BookNotFoundException;
import dsk.bookshelf.service.BookshelfService;
import dsk.bookshelf.service.exception.InvalidBookException;
import dsk.bookshelf.service.exception.InvalidCredentialsException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class BookshelfServiceImpl implements BookshelfService {

    private BundleContext context;

    public BookshelfServiceImpl(BundleContext context) {
        this.context = context;
    }

    private BookInventory lookupBookInventory() {
        ServiceReference ref = this.context.getServiceReference(BookInventory.class);
        return (BookInventory) this.context.getService(ref);
    }

    @Override
    public void addBook(String sessionId, String isbn) throws BookAlreadyExistsException, InvalidBookException {
        BookInventory inventory = this.lookupBookInventory();
        //MutableBook book = 
        inventory.create(isbn);
    }

    @Override
    public Book getBook(String sessionId, String isbn) throws BookNotFoundException {
        return this.lookupBookInventory().load(isbn);
    }

    @Override
    public String login(String username, String password) throws InvalidCredentialsException {
        return null;
    }

    @Override
    public void logout(String sessionId) {
    }
}
