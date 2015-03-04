package dsk.bookshelf.inventory.api;

public interface Book {

    String getIsbn();

    String getTitle();

    String getAuthor();

    String getCategory();

    int getRating();
}
