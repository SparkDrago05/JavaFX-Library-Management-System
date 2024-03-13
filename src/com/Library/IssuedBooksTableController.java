package com.Library;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class IssuedBooksTableController implements Initializable {
    @FXML
    private TableView<Book> issuedBooksTable;

    @FXML
    private TableColumn<Book, String> issuedBookName;

    @FXML
    private TableColumn<Book, String> issuedBookAuthor;

    @FXML
    private TableColumn<Book, String> issuedTo;

    @FXML
    public void initialize(URL url, ResourceBundle bundle) {

        // Set cell value factories for each column in Issued Books Table
        issuedBookName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title));
        issuedBookAuthor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().author));
        issuedTo.setCellValueFactory(cellData -> {
            String users = "";
            for (User user : cellData.getValue().getIssuedUsers()) {
                users += user.name + ", ";
            }
            return new SimpleStringProperty(users);
        });

        // Add all books in the LLibrary.issuedBooksist books to this table
        // Make FXCollections.observableList of all items in the book List
        issuedBooksTable.setItems(
                FXCollections.observableList(Library.books.stream().filter(book -> !book.available).toList()));
    }

}
