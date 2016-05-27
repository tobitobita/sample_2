package cv.sample.app.model;

import cv.sample.metaModel.Property;
import cv.sample.model.Model;
import static java.lang.reflect.Modifier.isStatic;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import cv.sample.metaModel.Class;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Book implements Model {

    public static final Class META_MODEL = () -> {
        final List<Property> props = Arrays.stream(Book.class.getDeclaredFields())
                .filter(f -> {
                    f.setAccessible(true);
                    return !isStatic(f.getModifiers());
                })
                .map(f -> {
                    return new PropertyImpl(f.getName(), f.getName().toUpperCase(), f.getType());
                }).collect(Collectors.toList());
        return props.toArray(new Property[props.size()]);
    };

    private static class PropertyImpl implements Property {

        private final String name;
        private final String displayName;
        private final java.lang.Class<?> type;

        public PropertyImpl(String name, String displayName, java.lang.Class<?> type) {
            this.name = name;
            this.displayName = displayName;
            this.type = type;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public java.lang.Class<?> getType() {
            return type;
        }

        @Override
        public String getDisplayName() {
            return displayName;
        }
    }

    private final StringProperty isbn = new SimpleStringProperty(this, "isbn");
    private final StringProperty title = new SimpleStringProperty(this, "title");
    private final StringProperty author = new SimpleStringProperty(this, "author");
    private final IntegerProperty price = new SimpleIntegerProperty(this, "price");

    public Book() {
    }

    public Book(String isbn, String title, String author, int price) {
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
