package dsk.bookshelf.inventory.mock;

import dsk.bookshelf.inventory.api.Book;
import dsk.bookshelf.inventory.api.BookInventory;
import dsk.bookshelf.inventory.api.MutableBook;
import dsk.bookshelf.inventory.api.entity.BookEntity;
import dsk.bookshelf.inventory.api.exception.BookAlreadyExistsException;
import dsk.bookshelf.inventory.api.exception.BookNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BookInventoryMock implements BookInventory {

    private final Map<String, Book> store = new HashMap<>();

    @Override
    public MutableBook create(String isbn) throws BookAlreadyExistsException {
        if (this.store.containsKey(isbn)) {
            throw new BookAlreadyExistsException();
        }
        MutableBook book = new BookEntity();
        book.setIsbn(isbn);
        return book;
    }

    @Override
    public Book load(String isbn) throws BookNotFoundException {
        return this.load4Edit(isbn);
    }

    @Override
    public MutableBook load4Edit(String isbn) throws BookNotFoundException {
        if (!this.store.containsKey(isbn)) {
            throw new BookNotFoundException();
        }
        return (MutableBook) store.get(isbn);
    }

    @Override
    public void remove(String isbn) throws BookNotFoundException {
        if (!this.store.containsKey(isbn)) {
            throw new BookNotFoundException();
        }
        this.store.remove(isbn);
    }

    @Override
    public Stream<Book> searchBooks() {
        return this.store.values().stream();
    }
}
