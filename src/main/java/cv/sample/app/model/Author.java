package cv.sample.app.model;

import cv.sample.model.Model;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Author implements Model {

    public static final List<Field> FIELDS = new ArrayList<>();

    static {
        try {
            FIELDS.add(Author.class.getDeclaredField("firstName"));
            FIELDS.add(Author.class.getDeclaredField("lastName"));
            FIELDS.add(Author.class.getDeclaredField("age"));
        } catch (NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private final StringProperty firstName = new SimpleStringProperty(this, "firstName");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName");
    private final IntegerProperty age = new SimpleIntegerProperty(this, "age");

    public Author() {
    }

    public Author(String firstName, String lastName, int age) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.age.set(age);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public int getAge() {
        return age.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public StringProperty firstNameProperty() {
        return this.firstName;
    }

    public StringProperty lastNameProperty() {
        return this.lastName;
    }

    public IntegerProperty ageProperty() {
        return this.age;
    }
}
