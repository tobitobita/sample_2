package dsk.bookshelf.inventory.api;

import dsk.bookshelf.inventory.api.exception.BookAlreadyExistsException;
import dsk.bookshelf.inventory.api.exception.BookNotFoundException;
import java.util.stream.Stream;

public interface BookInventory {

    MutableBook create(String isbn) throws BookAlreadyExistsException;

    Book load(String isbn) throws BookNotFoundException;

    MutableBook load4Edit(String isbn) throws BookNotFoundException;

    void remove(String isbn) throws BookNotFoundException;

    Stream<Book> searchBooks();
}
