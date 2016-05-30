package cv.sample.app.model;

import cv.sample.metaModel.Property;
import cv.sample.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import cv.sample.metaModel.Class;
import static java.lang.reflect.Modifier.isStatic;
import java.util.Arrays;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class Book implements Model {

    public static final BookClass META_MODEL = new BookClass();

    private final StringProperty isbn = new SimpleStringProperty(this, "isbn");
    private final StringProperty title = new SimpleStringProperty(this, "title");
    private final StringProperty author = new SimpleStringProperty(this, "author");
    private final IntegerProperty price = new SimpleIntegerProperty(this, "price");

    public Book() {
    }

    public Book(String isbn, String title, String author, int price) {
        if (!META_MODEL.isInit()) {
            META_MODEL.init();
        }
        this.isbn.set(isbn);
        this.title.set(title);
        this.author.set(author);
        this.price.set(price);
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
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

    public StringProperty authorProperty() {
        return author;
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    @Override
    public Class getType() {
        return META_MODEL;
    }
}

final class BookClass implements Class {

    private final ListProperty<Property> properties = new SimpleListProperty<>(this, "properties", FXCollections.observableArrayList());

    private static boolean init = false;

    boolean isInit() {
        return init;
    }

    void init() {
        Arrays.stream(Book.class.getDeclaredFields())
                .filter(f -> {
                    f.setAccessible(true);
                    return !isStatic(f.getModifiers());
                }).map(f -> new Property() {
            @Override
            public String getName() {
                return f.getName();
            }

            @Override
            public String getDisplayName() {
                return f.getName().toUpperCase();
            }

            @Override
            public java.lang.Class<?> getType() {
                return f.getType();
            }
        })
                .forEach(p -> {
                    properties.add(p);
                });
        init = true;
    }

    @Override
    public ListProperty<Property> propertiesProperty() {
        return properties;
    }
}
