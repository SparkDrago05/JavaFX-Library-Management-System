package com.Library;

import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;


//JOptionPane
import javax.swing.JOptionPane;

public class AddBookDialogController {
    @FXML
    private JFXTextField bookAuthorField;

    @FXML
    private JFXTextField bookGenreField;

    @FXML
    private JFXTextField bookNameField;

    public TableView<Book> allBooksTable;

    @FXML
    void addNewBook(ActionEvent event) {
        try {
            String bookName = bookNameField.getText();
            String bookAuthor = bookAuthorField.getText();
            String bookGenre = bookGenreField.getText();

            Library.addBook(bookName, bookAuthor, bookGenre);

            // Refresh the allBooksTable in DashboardController
            allBooksTable.setItems(FXCollections.observableList(Library.books));

            // Close the Dialog
            bookNameField.getScene().getWindow().hide();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An Error Occurred!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    void cancelAddBook(ActionEvent event) {
        bookNameField.getScene().getWindow().hide();
    }
}
