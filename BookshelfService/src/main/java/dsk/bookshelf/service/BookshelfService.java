package dsk.bookshelf.service;

import dsk.bookshelf.inventory.api.Book;
import dsk.bookshelf.inventory.api.exception.BookAlreadyExistsException;
import dsk.bookshelf.inventory.api.exception.BookNotFoundException;
import dsk.bookshelf.service.exception.InvalidBookException;

public interface BookshelfService extends Authentication {

    void addBook(String sessionId, String isbn) throws BookAlreadyExistsException, InvalidBookException;

    Book getBook(String sessionId, String isbn) throws BookNotFoundException;
}
