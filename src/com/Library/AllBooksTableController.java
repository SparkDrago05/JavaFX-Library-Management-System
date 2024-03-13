package com.Library;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AllBooksTableController implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Book> allBooksTable;

    @FXML
    private TableColumn<Book, Integer> bookIdCol;

    @FXML
    private TableColumn<Book, String> bookNameCol;

    @FXML
    private TableColumn<Book, String> bookAuthorCol;

    @FXML
    private TableColumn<Book, Boolean> bookAvailableCol;

    @FXML
    public void initialize(URL url, ResourceBundle bundle) {
        // Set cell value factories for each column in All Books Table
        bookIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().id).asObject());
        bookNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title));
        bookAuthorCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().author));
        bookAvailableCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().available));

        bookAvailableCol.setCellFactory(column -> {
            return new TableCell<Book, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item ? "Yes" : "No");
                        setStyle(item ? "-fx-background-color: #00FF00" : "-fx-background-color: #FF0000");
                    }
                }
            };
        });

        // Add all books in the List books to this table
        // Make FXCollections.observableList of all items in the book List
        // System.out.println(Library.books);
        allBooksTable.setItems(FXCollections.observableList(Library.books));
    }

}
