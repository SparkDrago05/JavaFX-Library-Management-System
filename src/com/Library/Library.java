package com.Library;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Library {
    static List<Book> books = new ArrayList<>();
    static List<User> users = new ArrayList<>();

    public static void addBook(String title, String author, String genre) {

        String insertBookQuery = "INSERT INTO books (title, author, genre, available) VALUES ('" + title + "', '"
                + author + "', '" + genre + "', 1)";
        try {
            DB.statement.execute(insertBookQuery);
            int id = DB.statement.getGeneratedKeys().getInt(1);
            books.add(new Book(id, title, author, genre, true));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A Database Error Occurred!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static List<Book> getAvailableBooks() {
        return books.stream().filter(book -> book.available).toList();
    }

    public static List<Book> getIssuedBooks() {
        return books.stream().filter(book -> !book.available).toList();
    }

    public static List<User> getAvailableIssueUsers(Book book) {
        // Return all public users and those who has not already issued the book
        return users.stream().filter(user -> user.type.equals("public") && !user.borrowedBooks.contains(book)).toList();
    }

    public static List<User> getAvailableReturnUsers(Book book) {
        // Return all public users and those who have already issued the book
        return users.stream().filter(user -> user.type.equals("public") && user.borrowedBooks.contains(book))
                .toList();
    }

    public static void issueBook(Book book, User user) {
        String issueBookQuery = "UPDATE books SET available = 0 WHERE id = " + book.id;
        String issueBookRecordQuery = "INSERT INTO issued_books (user_id, book_id) VALUES (" + user.id + ", " + book.id
                + ")";
        try {
            DB.statement.execute(issueBookQuery);
            DB.statement.execute(issueBookRecordQuery);
            user.borrowedBooks.add(book);
            Library.books.stream().filter(b -> b.id == book.id).findFirst().get().available = false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A Database Error Occurred!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void returnBook(Book book, User user) {
        String returnBookQuery = "UPDATE books SET available = 1 WHERE id = " + book.id;
        String returnBookRecordQuery = "DELETE FROM issued_books WHERE user_id = " + user.id + " AND book_id = "
                + book.id;
        try {
            DB.statement.execute(returnBookQuery);
            DB.statement.execute(returnBookRecordQuery);
            user.borrowedBooks.remove(book);
            Library.books.stream().filter(b -> b.id == book.id).findFirst().get().available = true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "A Database Error Occurred!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void initializeTables() throws Exception {
        // Set Pragma foreign keys to on
        DB.statement.execute("PRAGMA foreign_keys = ON");

        DB.statement.execute(
                "CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, author CHAR(100), genre CHAR(100), available BOOLEAN)");
        DB.statement.execute(
                "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name CHAR(100), email CHAR(100), password CHAR(100), type CHAR(10))");
        // Insert the admin user record if not found
        ResultSet adminResultSet = DB.statement.executeQuery("SELECT * FROM users WHERE email = 'admin'");
        if (!adminResultSet.next()) {
            DB.statement.execute(
                    "INSERT INTO users (name, email, password, type) VALUES ('Admin', 'admin', 'admin', 'librarian')");
        }

        // Table of Many2many relation having user_id and book_id columns to track
        // issued books
        // DB.statement.execute("DROP TABLE IF EXISTS issued_books;");
        DB.statement.execute(
                "CREATE TABLE IF NOT EXISTS issued_books (id INTEGER PRIMARY KEY, user_id INTEGER REFERENCES users(id) ON DELETE CASCADE, book_id INTEGER REFERENCES books(id) ON DELETE CASCADE);");

    }

    public static void initializeBooksAndUsers() throws Exception {
        ResultSet bookRecords = DB.statement.executeQuery("SELECT * FROM books");

        while (bookRecords.next()) {
            books.add(
                    new Book(bookRecords.getInt("id"), bookRecords.getString("title"), bookRecords.getString("author"),
                            bookRecords.getString("genre"), bookRecords.getBoolean("available")));
        }

        ResultSet userRecords = DB.statement.executeQuery("SELECT * FROM users");

        while (userRecords.next()) {
            users.add(new User(userRecords.getInt("id"), userRecords.getString("name"), userRecords.getString("email"),
                    userRecords.getString("password"), userRecords.getString("type")));
        }

        // Initialize the borrowed books of each user
        ResultSet issuedBooksRecords = DB.statement.executeQuery("SELECT * FROM issued_books");

        while (issuedBooksRecords.next()) {
            int userId = issuedBooksRecords.getInt("user_id");
            int bookId = issuedBooksRecords.getInt("book_id");

            User user = users.stream().filter(u -> u.id == userId).findFirst().get();
            Book book = books.stream().filter(b -> b.id == bookId).findFirst().get();

            user.borrowedBooks.add(book);
            book.available = false;
        }
    }

    @FXML
    private TextField emailInput;
    @FXML
    private TextField passwordInput;

    @FXML
    private void signIn() {
        users.forEach(user -> {
            if (user.email.equals(emailInput.getText()) && user.password.equals(passwordInput.getText())) {
                System.out.println("Welcome " + user.name);

                // Switch to Dashboard Scene
                try {
                    Parent dashboardRoot = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Scene dashboardScene = new Scene(dashboardRoot, 800, 500);

                    dashboardScene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());
                    Stage stage = (Stage) emailInput.getScene().getWindow();
                    stage.setScene(dashboardScene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
