package com.Library;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;

public class User {
    public int id;
    public String name;
    public String email;
    public String password;
    public String type; // (public, librarian)
    public List<Book> borrowedBooks = new ArrayList<>();
    public Button deleteUserButton;

    public User(int id, String name, String email, String password, String type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }

}
