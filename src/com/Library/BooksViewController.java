package com.Library;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class BooksViewController implements Initializable {

    Parent issuedBooksTable;
    Parent allBooksTable;

    @FXML
    private StackPane tableStack;

    @FXML
    public void initialize(URL url, ResourceBundle bundle) {

        try {
            issuedBooksTable = FXMLLoader.load(getClass().getResource("issuedBooksTable.fxml"));
            allBooksTable = FXMLLoader.load(getClass().getResource("allBooksTable.fxml"));

            // Add the tables to the stack pane
            tableStack.getChildren().addAll(allBooksTable, issuedBooksTable);

            // Set the visibility of the tables
            allBooksTable.setVisible(true);
            issuedBooksTable.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void viewAllBooksClicked(MouseEvent event) {
        issuedBooksTable.setVisible(false);
        allBooksTable.setVisible(true);
    }

    @FXML
    void viewIssuedBooksClicked(MouseEvent event) {
        allBooksTable.setVisible(false);
        issuedBooksTable.setVisible(true);
    }

    @FXML
    void showUsersTable(ActionEvent event) {
        System.out.println(event);
        allBooksTable.setVisible(false);
        issuedBooksTable.setVisible(false);
    }
}