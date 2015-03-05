package dsk.bookshelf.service;

import dsk.bookshelf.service.exception.InvalidCredentialsException;

public interface Authentication {
    String login(String username, String password) throws InvalidCredentialsException;
}
