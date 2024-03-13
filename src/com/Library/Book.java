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
