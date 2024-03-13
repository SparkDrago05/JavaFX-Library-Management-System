package com.Library;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class IssueBookViewController implements Initializable {
    @FXML
    private JFXButton issueBookButton;
    
    @FXML
    private JFXButton returnBookButton;

    @FXML
    private JFXComboBox<Book> issueBooksList;

    @FXML
    private JFXComboBox<User> issueUsersList;

    @FXML
    private JFXComboBox<Book> returnBooksList;

    @FXML
    private JFXComboBox<User> returnUsersList;

    public TableView<Book> allBooksTable;
    TableView<Book> issuedBooksTable;

    @FXML
    public void initialize(URL url, ResourceBundle bundle) {
        issueBooksList.setItems(FXCollections.observableArrayList(Library.getAvailableBooks()));
        returnBooksList.setItems(FXCollections.observableArrayList(Library.getIssuedBooks()));

        issueUsersList.setDisable(true);
        returnUsersList.setDisable(true);
    }

    @FXML
    void getAvailableIssueUsers(ActionEvent event) {
        Book selectedBook = issueBooksList.getValue();
        if (selectedBook != null) {
            issueUsersList.setItems(
                    FXCollections.observableArrayList(Library.getAvailableIssueUsers(selectedBook)));

            issueUsersList.setDisable(false);
        }
    }

    @FXML
    void getAvailableReturnUsers(ActionEvent event) {
        Book selectedBook = returnBooksList.getValue();
        if (selectedBook != null) {
            returnUsersList.setItems(
                    FXCollections.observableArrayList(Library.getAvailableReturnUsers(selectedBook)));

            returnUsersList.setDisable(false);
        }
    }

    @FXML
    void issueBook(ActionEvent event) {
        Book selectedBook = issueBooksList.getValue();
        User selectedUser = issueUsersList.getValue();

        if (selectedBook != null && selectedUser != null) {
            Library.issueBook(selectedBook, selectedUser);
            issueBooksList.setItems(FXCollections.observableArrayList(Library.getAvailableBooks()));
            returnBooksList.setItems(FXCollections.observableArrayList(Library.getIssuedBooks()));

            // Empty users list selection
            issueUsersList.setItems(FXCollections.observableArrayList());
            issueUsersList.setDisable(true);

            // Refresh the allBooksTable in DashboardController
            allBooksTable.setItems(FXCollections.observableList(Library.books));
            issuedBooksTable.setItems(
                    FXCollections.observableList(Library.books.stream().filter(book -> !book.available).toList()));
        }
    }

    @FXML
    void returnBook(ActionEvent event) {
        Book selectedBook = returnBooksList.getValue();
        User selectedUser = returnUsersList.getValue();

        if (selectedBook != null && selectedUser != null) {
            Library.returnBook(selectedBook, selectedUser);
            issueBooksList.setItems(FXCollections.observableArrayList(Library.getAvailableBooks()));
            returnBooksList.setItems(FXCollections.observableArrayList(Library.getIssuedBooks()));

            returnUsersList.setDisable(true);

            // Refresh the allBooksTable in DashboardController
            allBooksTable.setItems(FXCollections.observableList(Library.books));
            issuedBooksTable.setItems(
                    FXCollections.observableList(Library.books.stream().filter(book -> !book.available).toList()));
        }
    }
}
