package com.Library;

import java.util.List;

public class Book {
    public int id;
    public String title;
    public String author;
    public String genre;
    public Boolean available;

    public Book(int id, String title, String author, String genre, Boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = available;
    }

    public List<User> getIssuedUsers() {
        return Library.users.stream().filter(user -> user.borrowedBooks.contains(this)).toList();
    }

    @Override
    public String toString() {
        return title + " By " + author;
    }
}

// package com.Library;

// import javafx.beans.property.*;

// public class Book {
// final IntegerProperty id;
// private final StringProperty title;
// private final StringProperty author;
// private final StringProperty genre;
// private final BooleanProperty available;

// public Book(String title, String author, String genre) {
// this.id = new SimpleIntegerProperty();
// this.title = new SimpleStringProperty(title);
// this.author = new SimpleStringProperty(author);
// this.genre = new SimpleStringProperty(genre);
// this.available = new SimpleBooleanProperty(true);
// }

// public int getId() {
// return id.get();
// }

// public IntegerProperty idProperty() {
// return id;
// }

// public void setId(int id) {
// this.id.set(id);
// }

// public String getTitle() {
// return title.get();
// }

// public StringProperty titleProperty() {
// return title;
// }

// public void setTitle(String title) {
// this.title.set(title);
// }

// public String getAuthor() {
// return author.get();
// }

// public StringProperty authorProperty() {
// return author;
// }

// public void setAuthor(String author) {
// this.author.set(author);
// }

// public String getGenre() {
// return genre.get();
// }

// public StringProperty genreProperty() {
// return genre;
// }

// public void setGenre(String genre) {
// this.genre.set(genre);
// }

// public boolean isAvailable() {
// return available.get();
// }

// public BooleanProperty availableProperty() {
// return available;
// }

// public void setAvailable(boolean available) {
// this.available.set(available);
// }
// }
