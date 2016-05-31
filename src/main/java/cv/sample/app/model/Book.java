package cv.sample.app.model;

import cv.sample.model.Model;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Book implements Model {

    public static final List<Field> FIELDS = new ArrayList<>();

    static {
        try {
            FIELDS.add(Book.class.getDeclaredField("isbn"));
            FIELDS.add(Book.class.getDeclaredField("title"));
            FIELDS.add(Book.class.getDeclaredField("author"));
            FIELDS.add(Book.class.getDeclaredField("price"));
        } catch (NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private final StringProperty isbn = new SimpleStringProperty(this, "isbn");
    private final StringProperty title = new SimpleStringProperty(this, "title");
    private final ObjectProperty<Author> author = new SimpleObjectProperty<>(this, "author");
    private final IntegerProperty price = new SimpleIntegerProperty(this, "price");

    public Book() {
    }

    public Book(String isbn, String title, Author author, int price) {
        this.isbn.set(isbn);
        this.title.set(title);
        this.price.set(price);
        this.author.set(author);
    }

    public Author getAuthor() {
        return author.get();
    }

    public void setAuthor(Author author) {
        this.author.set(author);
    }

    public String getIsbn() {
        return isbn.get();
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public StringProperty isbnProperty() {
        return isbn;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public ObjectProperty<Author> authorProperty() {
        return author;
    }
}
